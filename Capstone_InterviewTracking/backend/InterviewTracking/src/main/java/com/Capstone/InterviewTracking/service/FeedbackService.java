package com.Capstone.InterviewTracking.service;

import com.Capstone.InterviewTracking.dto.FeedbackRequest;
import com.Capstone.InterviewTracking.dto.FeedbackResponse;

import java.util.List;

public interface FeedbackService {

    FeedbackResponse submitFeedback(Long interviewId, String panelEmail, FeedbackRequest request);

    List<FeedbackResponse> getFeedbackByInterview(Long interviewId);

    FeedbackResponse submitHRFeedback(Long interviewId, String hrEmail, FeedbackRequest request);
}
