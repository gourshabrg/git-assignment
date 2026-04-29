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
}
