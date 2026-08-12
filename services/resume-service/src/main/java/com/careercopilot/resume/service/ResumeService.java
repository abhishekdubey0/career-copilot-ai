package com.careercopilot.resume.service;

import com.careercopilot.resume.dto.response.ResumeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ResumeService {

    ResumeResponse uploadResume(MultipartFile file);

    ResumeResponse getResumeById(UUID resumeId);

    List<ResumeResponse> getAllResumes();

    void deleteResume(UUID resumeId);

    String getResumeText(UUID resumeId);

}
