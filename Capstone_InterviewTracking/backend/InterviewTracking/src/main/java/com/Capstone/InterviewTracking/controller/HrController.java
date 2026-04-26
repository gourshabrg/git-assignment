package com.Capstone.InterviewTracking.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
public class HrController {

    @GetMapping("/test")
    public String test() {
        return "HR access success";
    }
}