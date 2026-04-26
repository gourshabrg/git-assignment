package com.Capstone.InterviewTracking.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panel")
public class PanelController {

    @GetMapping("/test")
    public String test() {
        return "Panel access success";
    }
}