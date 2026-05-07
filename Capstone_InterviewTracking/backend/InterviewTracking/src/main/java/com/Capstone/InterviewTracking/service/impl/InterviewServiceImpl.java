package com.Capstone.InterviewTracking.service.impl;

import com.Capstone.InterviewTracking.dto.InterviewResponse;
import com.Capstone.InterviewTracking.dto.InterviewScheduleRequest;
import com.Capstone.InterviewTracking.dto.PanelResponse;
import com.Capstone.InterviewTracking.entity.*;
import com.Capstone.InterviewTracking.enums.InterviewRound;
import com.Capstone.InterviewTracking.enums.InterviewStatus;
import com.Capstone.InterviewTracking.exception.BadRequestException;
import com.Capstone.InterviewTracking.repository.*;
import com.Capstone.InterviewTracking.service.EmailService;
import com.Capstone.InterviewTracking.service.InterviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final ApplicationRepository applicationRepository;
    private final PanelRepository panelRepository;
    private final InterviewRepository interviewRepository;
    private final EmailService emailService;

    public InterviewServiceImpl(ApplicationRepository applicationRepository,
                                PanelRepository panelRepository,
                                InterviewRepository interviewRepository,
                                EmailService emailService) {
        this.applicationRepository = applicationRepository;
        this.panelRepository = panelRepository;
        this.interviewRepository = interviewRepository;
        this.emailService = emailService;
    }

    @Override
    public InterviewResponse scheduleInterview(InterviewScheduleRequest request, String hrEmail) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new BadRequestException("Application not found"));

        Candidate candidate = application.getCandidate();

        if (interviewRepository.existsByCandidateAndRound(candidate, request.getRound())) {
            throw new BadRequestException("Interview for this round is already scheduled for this candidate");
        }

        boolean isHrRound = request.getRound() == InterviewRound.HR;
        List<Panel> panels = new ArrayList<>();

        if (!isHrRound) {
            List<Long> panelIds = request.getPanelIds();
            if (panelIds == null || panelIds.isEmpty()) {
                throw new BadRequestException("At least one panel member is required for this round");
            }
            if (panelIds.size() > 2) {
                throw new BadRequestException("Maximum 2 panel members allowed per interview");
            }
            panels = panelRepository.findAllById(panelIds);
            if (panels.isEmpty()) {
                throw new BadRequestException("No valid panel members found");
            }
        }

        Interview interview = new Interview();
        interview.setCandidate(candidate);
        interview.setRound(request.getRound());
        interview.setInterviewDateTime(request.getInterviewDateTime());
        interview.setFocusArea(request.getFocusArea());
        interview.setStatus(InterviewStatus.SCHEDULED);
        interview.setPanels(panels);

        interview = interviewRepository.save(interview);

        String interviewerLabel = isHrRound ? "HR Team"
                : panels.stream().map(Panel::getFullName).collect(Collectors.joining(", "));

        emailService.sendInterviewScheduledMail(
                candidate.getEmail(),
                candidate.getFullName(),
                request.getRound().name(),
                request.getInterviewDateTime().toString(),
                interviewerLabel
        );

        if (isHrRound && hrEmail != null) {
            emailService.sendHRInterviewScheduledMail(
                    hrEmail,
                    candidate.getFullName(),
                    candidate.getEmail(),
                    request.getInterviewDateTime().toString()
            );
        }

        return toResponse(interview, application);
    }

    @Override
    public List<InterviewResponse> getInterviewsByPanel(String panelEmail) {
        Panel panel = panelRepository.findByEmail(panelEmail)
                .orElseThrow(() -> new BadRequestException("Panel member not found"));

        return interviewRepository.findByPanelsContaining(panel)
                .stream()
                .map(i -> toResponse(i, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewResponse> getInterviewsByCandidate(Long candidateId) {
        return interviewRepository.findAll()
                .stream()
                .filter(i -> i.getCandidate().getId().equals(candidateId))
                .map(i -> toResponse(i, null))
                .collect(Collectors.toList());
    }

    public InterviewResponse toResponse(Interview interview) {
        return toResponse(interview, null);
    }

    public InterviewResponse toResponse(Interview interview, Application application) {
        InterviewResponse response = new InterviewResponse();
        response.setId(interview.getId());
        response.setRound(interview.getRound().name());
        response.setInterviewDateTime(interview.getInterviewDateTime());
        response.setFocusArea(interview.getFocusArea());
        response.setStatus(interview.getStatus().name());

        List<PanelResponse> panelResponses = interview.getPanels().stream()
                .map(p -> new PanelResponse(p.getId(), p.getFullName(), p.getEmail(),
                        p.getPhone(), p.getOrganization(), p.getDesignation()))
                .collect(Collectors.toList());
        response.setPanels(panelResponses);

        Candidate candidate = interview.getCandidate();
        if (candidate != null) {
            response.setCandidateName(candidate.getFullName());
            response.setCandidateEmail(candidate.getEmail());
        }

        if (application != null) {
            response.setApplicationId(application.getId());
        } else if (candidate != null) {
            applicationRepository.findByCandidateOrderByCreatedAtDesc(candidate)
                    .stream().findFirst()
                    .ifPresent(app -> response.setApplicationId(app.getId()));
        }

        return response;
    }
}
