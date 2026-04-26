package com.Capstone.InterviewTracking.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @GetMapping("/test")
    public String test() {
        return "Candidate access success";
    }
}