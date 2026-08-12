package com.careercopilot.resume.controller;

import com.careercopilot.resume.common.ApiResponse;
import com.careercopilot.resume.common.ResponseBuilder;
import com.careercopilot.resume.dto.response.ResumeResponse;
import com.careercopilot.resume.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "Resume APIs", description = "Resume Management APIs")
@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(summary = "Upload a resume")
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<ResumeResponse>> uploadResume(
            @RequestPart("file") MultipartFile file){

        ResumeResponse response = resumeService.uploadResume(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED,
                                "Resume uploaded successfully",
                                response
                        )
                );
    }

    @Operation(summary = "Get resume by id")
    @GetMapping("/{resumeId}")
    public ResponseEntity<ApiResponse<ResumeResponse>> getResumeById(
            @PathVariable UUID resumeId) {

        ResumeResponse response = resumeService.getResumeById(resumeId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Resume fetched successfully",
                        response
                )
        );
    }

    @Operation(summary = "Get all resumes")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ResumeResponse>>> getAllResumes() {

        List<ResumeResponse> responses = resumeService.getAllResumes();

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Resumes fetched successfully",
                        responses
                )
        );
    }

    @Operation(summary = "Delete a resume")
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<ApiResponse<Void>> deleteResume(
            @PathVariable UUID resumeId) {

        resumeService.deleteResume(resumeId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Resume deleted successfully",
                        null
                )
        );
    }
    @Operation(summary = "Get resume text")
    @GetMapping("/{resumeId}/text")
    public ResponseEntity<ApiResponse<String>> getResumeText(
            @PathVariable UUID resumeId) {

        String resumeText = resumeService.getResumeText(resumeId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Resume text fetched successfully",
                        resumeText
                )
        );
    }
}
