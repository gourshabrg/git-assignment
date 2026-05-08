package com.Capstone.InterviewTracking.service;

import org.springframework.web.multipart.MultipartFile;

import com.Capstone.InterviewTracking.dto.ApplicationResponse;
import com.Capstone.InterviewTracking.dto.CandidateDetailResponse;

public interface CandidateService {

    CandidateDetailResponse getMyApplication(String email);

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
