package com.Capstone.InterviewTracking.service;

import com.Capstone.InterviewTracking.dto.AuthRequest;
import com.Capstone.InterviewTracking.dto.AuthResponse;
import com.Capstone.InterviewTracking.dto.SignupRequest;

public interface AuthService {

    void register(SignupRequest request);

    AuthResponse login(AuthRequest request);
    void setPassword(String token, String password);
}