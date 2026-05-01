package com.Capstone.InterviewTracking.controller;

import com.Capstone.InterviewTracking.constant.AppConstants;
import com.Capstone.InterviewTracking.dto.ApiResponse;
import com.Capstone.InterviewTracking.dto.AuthRequest;
import com.Capstone.InterviewTracking.dto.AuthResponse;
import com.Capstone.InterviewTracking.service.AuthService;
import com.Capstone.InterviewTracking.dto.SignupRequest;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


@RestController
@RequestMapping(AppConstants.AUTH_BASE_PATH)
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(AppConstants.REGISTER_PATH)
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody SignupRequest request) {
        LOGGER.info("Register request received for email: {}", request.getEmail());
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful", null));
    }

    @PostMapping(AppConstants.SET_PASSWORD_PATH)
public ResponseEntity<ApiResponse<String>> setPassword(@RequestBody Map<String, String> body) {

    String token = body.get("token");
    String password = body.get("password");

    LOGGER.info("Set password request received for token: {}", token);

    authService.setPassword(token, password);

    return ResponseEntity.ok(
            ApiResponse.success("Password set successfully", null)
    );
}

    @PostMapping(AppConstants.LOGIN_PATH)
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        LOGGER.info("Login request received for email: {}", request.getEmail());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }
}
