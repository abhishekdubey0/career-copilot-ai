package com.careercopilot.intelligence.controller;

import com.careercopilot.intelligence.dto.response.GeneratedResumeResponse;
import com.careercopilot.intelligence.service.GeneratedResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/generated-resumes")
@RequiredArgsConstructor
public class GeneratedResumeController {

    private final GeneratedResumeService generatedResumeService;

    @PostMapping("/{generatedResumeId}/latex")
    public ResponseEntity<GeneratedResumeResponse> generateLatex(
            @PathVariable UUID generatedResumeId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        generatedResumeService.generateLatex(
                                generatedResumeId
                        )
                );
    }

    @GetMapping("/{generatedResumeId}")
    public ResponseEntity<GeneratedResumeResponse> getGeneratedResume(
            @PathVariable UUID generatedResumeId) {

        return ResponseEntity.ok(
                generatedResumeService.getById(
                        generatedResumeId
                )
        );
    }
}