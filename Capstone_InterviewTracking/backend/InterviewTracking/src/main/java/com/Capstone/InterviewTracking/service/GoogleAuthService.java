package com.Capstone.InterviewTracking.service;

import com.google.api.client.auth.oauth2.Credential;

public interface GoogleAuthService {
    Credential getCredentials() throws Exception;
}