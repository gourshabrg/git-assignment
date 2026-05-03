package com.Capstone.InterviewTracking.service.impl;

import com.Capstone.InterviewTracking.dto.JobDescriptionRequest;
import com.Capstone.InterviewTracking.dto.JobDescriptionResponse;
import com.Capstone.InterviewTracking.entity.JobDescription;
import com.Capstone.InterviewTracking.entity.User;
import com.Capstone.InterviewTracking.exception.BadRequestException;
import com.Capstone.InterviewTracking.exception.UserNotFoundException;
import com.Capstone.InterviewTracking.mapper.JobDescriptionMapper;
import com.Capstone.InterviewTracking.repository.JobDescriptionRepository;
import com.Capstone.InterviewTracking.repository.UserRepository;
import com.Capstone.InterviewTracking.service.JobDescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDescriptionServiceImpl implements JobDescriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDescriptionServiceImpl.class);

    private final JobDescriptionRepository jobDescriptionRepository;
    private final UserRepository userRepository;
    private final JobDescriptionMapper jobDescriptionMapper;

    public JobDescriptionServiceImpl(JobDescriptionRepository jobDescriptionRepository,
                                     UserRepository userRepository,
                                     JobDescriptionMapper jobDescriptionMapper) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.userRepository = userRepository;
        this.jobDescriptionMapper = jobDescriptionMapper;
    }

    @Override
    public JobDescriptionResponse create(JobDescriptionRequest request, String createdByEmail) {
        validateRange(request.getMinExperience(), request.getMaxExperience(), "Experience");
        validateRange(request.getMinSalary(), request.getMaxSalary(), "Salary");

        User createdBy = userRepository.findByEmail(createdByEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        JobDescription savedJob = jobDescriptionRepository.save(
                jobDescriptionMapper.toEntity(request, createdBy)
        );

        LOGGER.info("Created job description with id: {}", savedJob.getId());
        return jobDescriptionMapper.toResponse(savedJob);
    }

    @Override
    public List<JobDescriptionResponse> findActiveJobs() {
        return jobDescriptionRepository.findByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(jobDescriptionMapper::toResponse)
                .toList();
    }

    private void validateRange(Integer min, Integer max, String fieldName) {
        if (min != null && max != null && min > max) {
            throw new BadRequestException(fieldName + " minimum cannot be greater than maximum");
        }
    }

    private void validateRange(Double min, Double max, String fieldName) {
        if (min != null && max != null && min > max) {
            throw new BadRequestException(fieldName + " minimum cannot be greater than maximum");
        }
    }

    @Override
    public JobDescriptionResponse update(Long id, JobDescriptionRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getRole().name().equals("HR")) {
            throw new BadRequestException("Only HR can update job descriptions");
        }

        JobDescription jd = jobDescriptionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Job not found"));

        validateRange(request.getMinExperience(), request.getMaxExperience(), "Experience");
        validateRange(request.getMinSalary(), request.getMaxSalary(), "Salary");

        jd.setTitle(request.getTitle());
        jd.setDescription(request.getDescription());
        jd.setSkills(request.getSkills());
        jd.setLocation(request.getLocation());
        jd.setMinSalary(request.getMinSalary());
        jd.setMaxSalary(request.getMaxSalary());
        jd.setMinExperience(request.getMinExperience());
        jd.setMaxExperience(request.getMaxExperience());
        jd.setJobType(request.getJobType());

        LOGGER.info("JD updated with id: {}", id);

        return jobDescriptionMapper.toResponse(jobDescriptionRepository.save(jd));
    }
@Override
public void delete(Long id, String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    if (!user.getRole().name().equals("HR")) {
        throw new BadRequestException("Only HR can delete job");
    }

    JobDescription jd = jobDescriptionRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Job not found"));

    jobDescriptionRepository.delete(jd); 

    LOGGER.info("Job permanently deleted with id: {}", id);
}
 @Override
 public JobDescriptionResponse getById(Long id) {

     JobDescription jd = jobDescriptionRepository.findById(id)
             .orElseThrow(() -> new BadRequestException("Job not found"));

            LOGGER.info("HR fetching  job by id");
     return jobDescriptionMapper.toResponse(jd);
 }

@Override
public List<JobDescriptionResponse> findAllForHR() {

    LOGGER.info("HR fetching all jobs");

    return jobDescriptionRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(jobDescriptionMapper::toResponse)
            .toList();
}

@Override
public void toggleActive(Long id, String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    if (!user.getRole().name().equals("HR")) {
        throw new BadRequestException("Only HR can change job status");
    }

    JobDescription jd = jobDescriptionRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Job not found"));

    jd.setActive(!jd.isActive()); 

    jobDescriptionRepository.save(jd);
    LOGGER.info("User {} toggling job id {}", email, id);
}

}
