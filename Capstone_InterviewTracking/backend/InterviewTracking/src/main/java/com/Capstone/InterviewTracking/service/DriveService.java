package com.Capstone.InterviewTracking.service;

import org.springframework.web.multipart.MultipartFile;

public interface DriveService {
    String uploadFile(MultipartFile file);
}