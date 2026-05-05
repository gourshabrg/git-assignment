package com.Capstone.InterviewTracking.service.impl;

import com.Capstone.InterviewTracking.dto.ApplicationResponse;
import com.Capstone.InterviewTracking.dto.SignupRequest;
import com.Capstone.InterviewTracking.entity.*;
import com.Capstone.InterviewTracking.enums.ApplicationStatus;
import com.Capstone.InterviewTracking.enums.InterviewStage;
import com.Capstone.InterviewTracking.repository.*;
import com.Capstone.InterviewTracking.service.AuthService;
import com.Capstone.InterviewTracking.service.CandidateService;
import com.Capstone.InterviewTracking.service.DriveService;
import com.Capstone.InterviewTracking.util.FileValidationUtil;
import com.Capstone.InterviewTracking.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final JobDescriptionRepository jobRepository;
    private final DriveService driveService;
    private final AuthService authService;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public CandidateServiceImpl(
            CandidateRepository candidateRepository,
            JobDescriptionRepository jobRepository,
            DriveService driveService,
            AuthService authService,
            ApplicationRepository applicationRepository,
            UserRepository userRepository) {

        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.driveService = driveService;
        this.authService = authService;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    // jd apply
  @Override
public ApplicationResponse applyCandidate(
        String fullName, String email, String mobile, String dob,
        MultipartFile resume,
        String currentCompany, Double totalExp, Double relExp,
        Double currentCtc, Double expectedCtc,
        Integer noticePeriod, String location, String source,
        Long jobId) {

    FileValidationUtil.validatePdf(resume);

    Candidate candidate = candidateRepository.findByEmail(email)
            .orElseGet(() -> {

                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                Candidate c = new Candidate();
                c.setEmail(email);
                c.setUser(user);
                c.setFullName(fullName);
                c.setMobile(mobile);

                return candidateRepository.save(c);
            });

    if (applicationRepository.existsByCandidateAndStatus(candidate, ApplicationStatus.APPLIED)) {
        throw new BadRequestException("You already have an active application");
    }

    JobDescription job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

    if (applicationRepository.existsByCandidateAndJob(candidate, job)) {
        throw new BadRequestException("You already applied for this job");
    }

    String resumeUrl = driveService.uploadFile(resume);

    
    candidate.setFullName(fullName);
    candidate.setMobile(mobile);
    candidate.setCurrentCompany(currentCompany);
    candidate.setTotalExperience(totalExp);
    candidate.setRelevantExperience(relExp);
    candidate.setCurrentCtc(currentCtc);
    candidate.setExpectedCtc(expectedCtc);
    candidate.setNoticePeriod(noticePeriod);
    candidate.setPreferredLocation(location);
    candidate.setSource(source);
    candidate.setResumeUrl(resumeUrl);

    candidateRepository.save(candidate);

    Application app = new Application();
    app.setCandidate(candidate);
    app.setJob(job);
    app.setStage(InterviewStage.PROFILING);
    app.setStatus(ApplicationStatus.APPLIED);

    app = applicationRepository.save(app);

   
    return new ApplicationResponse(
            app.getId(),
            job.getId(),
            job.getTitle(),
            app.getStatus().name(),
            app.getStage().name(),
            candidate.getResumeUrl(),
            app.getCreatedAt()
    );
}

    // hr 
  @Override
public ApplicationResponse createByHR(
        String fullName, String email, String mobile, String dob,
        MultipartFile resume,
        String currentCompany, Double totalExp, Double relExp,
        Double currentCtc, Double expectedCtc,
        Integer noticePeriod, String location, String source,
        Long jobId,
        String hrEmail) {

    FileValidationUtil.validatePdf(resume);

    Candidate candidate = candidateRepository.findByEmail(email)
            .orElseGet(() -> {
                Candidate c = new Candidate();
                c.setEmail(email);
                c.setFullName(fullName);
                c.setMobile(mobile);
                return candidateRepository.save(c);
            });

    if (applicationRepository.existsByCandidateAndStatus(candidate, ApplicationStatus.APPLIED)) {
        throw new BadRequestException("Candidate already has an active application");
    }

    JobDescription job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

    String resumeUrl = driveService.uploadFile(resume);

    candidate.setFullName(fullName);
    candidate.setMobile(mobile);
    candidate.setCurrentCompany(currentCompany);
    candidate.setTotalExperience(totalExp);
    candidate.setRelevantExperience(relExp);
    candidate.setCurrentCtc(currentCtc);
    candidate.setExpectedCtc(expectedCtc);
    candidate.setNoticePeriod(noticePeriod);
    candidate.setPreferredLocation(location);
    candidate.setSource(source);
    candidate.setResumeUrl(resumeUrl);

    candidateRepository.save(candidate);

    Application app = new Application();
    app.setCandidate(candidate);
    app.setJob(job);
    app.setStage(InterviewStage.PROFILING);
    app.setStatus(ApplicationStatus.APPLIED);

    app = applicationRepository.save(app);

    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setEmail(email);
    signupRequest.setFullName(fullName);

    authService.register(signupRequest);

    return new ApplicationResponse(
            app.getId(),
            job.getId(),
            job.getTitle(),
            app.getStatus().name(),
            app.getStage().name(),
            candidate.getResumeUrl(),
            app.getCreatedAt()
    );
}
}