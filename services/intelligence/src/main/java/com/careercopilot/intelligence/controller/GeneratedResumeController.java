package com.careercopilot.intelligence.controller;

import com.careercopilot.intelligence.common.ApiResponse;
import com.careercopilot.intelligence.common.ResponseBuilder;
import com.careercopilot.intelligence.dto.response.GeneratedResumeResponse;
import com.careercopilot.intelligence.service.GeneratedResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/generated-resumes")
@RequiredArgsConstructor
public class GeneratedResumeController {

    private final GeneratedResumeService generatedResumeService;

    @PostMapping("/{generatedResumeId}/latex")
    public ResponseEntity<ApiResponse<GeneratedResumeResponse>> generateLatex(
            @PathVariable UUID generatedResumeId) {

        GeneratedResumeResponse response = generatedResumeService.generateLatex(generatedResumeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.OK,
                                "Latex generated successfully",
                                response
                        )
                );
    }

    @GetMapping("/{generatedResumeId}")
    public ResponseEntity<ApiResponse<GeneratedResumeResponse>> getGeneratedResume(
            @PathVariable UUID generatedResumeId) {

        GeneratedResumeResponse response = generatedResumeService.getById(generatedResumeId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Resume fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/resume/{resumeId}")
    public ResponseEntity<ApiResponse<List<GeneratedResumeResponse>>> getByResumeId(
            @PathVariable UUID resumeId) {

        List<GeneratedResumeResponse> response = generatedResumeService.getByResumeId(resumeId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "List of resumes fetched successfully",
                        response
                )
        );
    }
}