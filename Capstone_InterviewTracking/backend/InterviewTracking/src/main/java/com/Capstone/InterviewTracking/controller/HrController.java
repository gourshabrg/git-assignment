package com.Capstone.InterviewTracking.controller;

import com.Capstone.InterviewTracking.constant.AppConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.HR_BASE_PATH)
public class HrController {

    @GetMapping(AppConstants.TEST_PATH)
    public String test() {
        return "HR access success";
    }
}
