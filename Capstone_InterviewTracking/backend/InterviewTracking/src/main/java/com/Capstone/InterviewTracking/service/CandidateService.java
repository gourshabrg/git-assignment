package com.Capstone.InterviewTracking.service;

import org.springframework.web.multipart.MultipartFile;

import com.Capstone.InterviewTracking.dto.ApplicationResponse;


public interface CandidateService {

    ApplicationResponse applyCandidate(
            String fullName, String email, String mobile, String dob,
            MultipartFile resume,
            String currentCompany, Double totalExp, Double relExp,
            Double currentCtc, Double expectedCtc,
            Integer noticePeriod, String location, String source,
            Long jobId
    );

    ApplicationResponse createByHR(
            String fullName, String email, String mobile, String dob,
            MultipartFile resume,
            String currentCompany, Double totalExp, Double relExp,
            Double currentCtc, Double expectedCtc,
            Integer noticePeriod, String location, String source,
            Long jobId,
            String hrEmail
    );
}