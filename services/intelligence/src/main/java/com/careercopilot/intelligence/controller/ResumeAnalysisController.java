package com.careercopilot.intelligence.controller;

import com.careercopilot.intelligence.common.ApiResponse;
import com.careercopilot.intelligence.common.ResponseBuilder;
import com.careercopilot.intelligence.dto.ai.ImprovedResume;
import com.careercopilot.intelligence.dto.request.ResumeAnalysisRequest;
import com.careercopilot.intelligence.dto.response.GeneratedResumeResponse;
import com.careercopilot.intelligence.dto.response.ResumeAnalysisResponse;
import com.careercopilot.intelligence.service.GeneratedResumeService;
import com.careercopilot.intelligence.service.ResumeAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analyses")
@RequiredArgsConstructor
public class ResumeAnalysisController {

    private final ResumeAnalysisService resumeAnalysisService;
    private final GeneratedResumeService generatedResumeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResumeAnalysisResponse>> analyzeResume(
            @Valid @RequestBody ResumeAnalysisRequest request) {

        ResumeAnalysisResponse response =
                resumeAnalysisService.analyze(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED,
                                "Resume Analysis completed",
                                response
                        )
                );
    }

    @GetMapping("/{analysisId}")
    public ResponseEntity<ApiResponse<ResumeAnalysisResponse>> getAnalysisById(
            @PathVariable UUID analysisId) {

        ResumeAnalysisResponse response = resumeAnalysisService.getAnalysisById(analysisId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Analysis fetched successfully",
                        response
                )
        );
    }

    @PostMapping("/{analysisId}/improve")
    public ResponseEntity<ApiResponse<GeneratedResumeResponse>> improveResume(
            @PathVariable UUID analysisId) {

        GeneratedResumeResponse response = generatedResumeService.improve(analysisId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Resume has been improved",
                        response
                )

        );
    }

}