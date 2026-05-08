package com.Capstone.InterviewTracking.service;

public interface EmailService {

    void sendVerificationMail(String toEmail, String name, String token);

    void sendInterviewScheduledMail(String toEmail, String candidateName,
                                    String round, String dateTime, String panelNames);

    void sendHRInterviewScheduledMail(String hrEmail, String candidateName,
                                      String candidateEmail, String dateTime);
}
