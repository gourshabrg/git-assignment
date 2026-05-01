package com.Capstone.InterviewTracking.service;

public interface EmailService {

    void sendVerificationMail(String toEmail, String name, String token);

}