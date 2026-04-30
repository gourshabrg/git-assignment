package com.Capstone.InterviewTracking.service;

import com.Capstone.InterviewTracking.dto.AuthRequest;
import com.Capstone.InterviewTracking.dto.AuthResponse;

public interface AuthService {

    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);
}