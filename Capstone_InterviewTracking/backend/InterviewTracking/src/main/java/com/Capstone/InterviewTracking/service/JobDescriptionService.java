package com.Capstone.InterviewTracking.service;

import com.Capstone.InterviewTracking.dto.JobDescriptionRequest;
import com.Capstone.InterviewTracking.dto.JobDescriptionResponse;

import java.util.List;

public interface JobDescriptionService {

    JobDescriptionResponse create(JobDescriptionRequest request, String createdByEmail);

    List<JobDescriptionResponse> findActiveJobs();
}
