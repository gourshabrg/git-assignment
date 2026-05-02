package com.Capstone.InterviewTracking.constant;

public final class AppConstants {

    private AppConstants() {}

    public static final String AUTH_BASE_PATH = "/auth";
    public static final String HR_BASE_PATH = "/hr";
    public static final String PANEL_BASE_PATH = "/panel";
    public static final String CANDIDATE_BASE_PATH = "/candidate";
    public static final String JOB_BASE_PATH = "/jobs";

    public static final String REGISTER_PATH = "/signup";
    public static final String LOGIN_PATH = "/login";
    public static final String SET_PASSWORD_PATH = "/set-password";
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

    public static final String EMAIL_LINK_STRING = "http://127.0.0.1:5500/Capstone_InterviewTracking/frontend/pages/auth/set-password.html?token=";
    public static final String EMAIL_SUBJECT = "Complete Your Account Setup – Set Your Password";
    public static final Integer SET_TOKEN_EXPIRY = 15; 
  
            public static String buildEmailMessage(String name, String token) {

    String link = EMAIL_LINK_STRING + token;

    return "<html>" +
            "<body style='font-family:Arial, sans-serif;'>" +

            "<p>Hi " + name + ",</p>" +

            "<p>Click the button below to complete your registration:</p>" +

            "<a href='" + link + "' " +
            "style='display:inline-block;padding:10px 15px;background-color:blue;color:white;text-decoration:none;border-radius:5px;'>"
            + "Verify Account</a>" +

            "<p>If the button doesn't work, copy this link:</p>" +
            "<p><a href='" + link + "'>" + link + "</a></p>" +

            "<p>Regards,<br>Interview Tracking Team</p>" +

            "</body></html>";
}
}
