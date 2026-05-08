package com.Capstone.InterviewTracking.service;

import com.Capstone.InterviewTracking.dto.JobDescriptionRequest;
import com.Capstone.InterviewTracking.dto.JobDescriptionResponse;

import java.util.List;

public interface JobDescriptionService {

    JobDescriptionResponse create(JobDescriptionRequest request, String createdByEmail);
    JobDescriptionResponse update(Long id, JobDescriptionRequest request, String email);

     void delete(Long id, String email);

     JobDescriptionResponse getById(Long id);

     List<JobDescriptionResponse> findActiveJobs();
    List<JobDescriptionResponse> findAllForHR();
    void toggleActive(Long id, String email);
}
