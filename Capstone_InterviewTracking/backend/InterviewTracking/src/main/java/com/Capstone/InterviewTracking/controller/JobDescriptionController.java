package com.Capstone.InterviewTracking.controller;

import com.Capstone.InterviewTracking.constant.AppConstants;
import com.Capstone.InterviewTracking.dto.ApiResponse;
import com.Capstone.InterviewTracking.dto.JobDescriptionRequest;
import com.Capstone.InterviewTracking.dto.JobDescriptionResponse;
import com.Capstone.InterviewTracking.service.JobDescriptionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AppConstants.JOB_BASE_PATH)
public class JobDescriptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDescriptionController.class);

    private final JobDescriptionService jobDescriptionService;

    public JobDescriptionController(JobDescriptionService jobDescriptionService) {
        this.jobDescriptionService = jobDescriptionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobDescriptionResponse>> create(
            @Valid @RequestBody JobDescriptionRequest request,
            Authentication authentication) {
        LOGGER.info("Create job description request received for title: {}", request.getTitle());

        JobDescriptionResponse response = jobDescriptionService.create(request, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Job description created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobDescriptionResponse>>> findActiveJobs() {
        LOGGER.info("Fetch active job descriptions request received");

        List<JobDescriptionResponse> response = jobDescriptionService.findActiveJobs();
        return ResponseEntity.ok(ApiResponse.success("Job descriptions fetched successfully", response));
    }
}
