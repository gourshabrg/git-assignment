package com.Capstone.InterviewTracking.constant;

public final class AppConstants {

    private AppConstants() {
    }

    public static final String AUTH_BASE_PATH = "/auth";
    public static final String HR_BASE_PATH = "/hr";
    public static final String PANEL_BASE_PATH = "/panel";
    public static final String CANDIDATE_BASE_PATH = "/candidate";
    public static final String JOB_BASE_PATH = "/jobs";

    public static final String REGISTER_PATH = "/register";
    public static final String LOGIN_PATH = "/login";
    public static final String TEST_PATH = "/test";

    public static final String AUTH_MATCHER = AUTH_BASE_PATH + "/**";
    public static final String HR_MATCHER = HR_BASE_PATH + "/**";
    public static final String PANEL_MATCHER = PANEL_BASE_PATH + "/**";
    public static final String CANDIDATE_MATCHER = CANDIDATE_BASE_PATH + "/**";
    public static final String JOB_MATCHER = JOB_BASE_PATH + "/**";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String ALL_PATHS = "/**";
}
