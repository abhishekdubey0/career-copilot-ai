package com.careercopilot.resume.mapper;

import com.careercopilot.resume.dto.response.ResumeResponse;
import com.careercopilot.resume.entity.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumeMapper {

    public ResumeResponse toResponse(Resume resume) {

        return new ResumeResponse(
                resume.getId(),
                resume.getOriginalFileName(),
                resume.getFileSize(),
                resume.getCreatedAt()
        );
    }
}