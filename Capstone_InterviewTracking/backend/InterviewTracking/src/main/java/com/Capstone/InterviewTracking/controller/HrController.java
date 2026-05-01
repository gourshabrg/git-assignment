package com.Capstone.InterviewTracking.controller;
import com.Capstone.InterviewTracking.constant.AppConstants;
import com.Capstone.InterviewTracking.dto.ApiResponse;
import com.Capstone.InterviewTracking.dto.PanelRequest;
import com.Capstone.InterviewTracking.service.PanelService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.HR_BASE_PATH)
public class HrController {

    private final PanelService panelService;

    public HrController(PanelService panelService) {
        this.panelService = panelService;
    }

    @PostMapping("/create-panel")
    public ResponseEntity<ApiResponse<String>> createPanel(@RequestBody PanelRequest request) {

        panelService.createPanel(request);

        return ResponseEntity.ok(
                ApiResponse.success("Panel created successfully. Email sent.", null)
        );
    }
}