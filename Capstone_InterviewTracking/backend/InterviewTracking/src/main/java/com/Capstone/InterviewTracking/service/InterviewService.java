package com.Capstone.InterviewTracking.service;

import com.Capstone.InterviewTracking.dto.InterviewResponse;
import com.Capstone.InterviewTracking.dto.InterviewScheduleRequest;
import com.Capstone.InterviewTracking.entity.Application;
import com.Capstone.InterviewTracking.entity.Interview;

import java.util.List;

public interface InterviewService {

    InterviewResponse scheduleInterview(InterviewScheduleRequest request, String hrEmail);

    List<InterviewResponse> getInterviewsByPanel(String panelEmail);

    List<InterviewResponse> getInterviewsByCandidate(Long candidateId);

    InterviewResponse toResponse(Interview interview, Application application);
}
