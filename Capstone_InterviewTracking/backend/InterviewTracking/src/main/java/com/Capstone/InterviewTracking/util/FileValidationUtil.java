package com.Capstone.InterviewTracking.util;

import org.springframework.web.multipart.MultipartFile;

public class FileValidationUtil {

    public static void validatePdf(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Resume file is required");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new RuntimeException("Only PDF files are allowed");
        }
    }
}