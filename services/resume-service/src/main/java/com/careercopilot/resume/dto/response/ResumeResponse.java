package com.careercopilot.resume.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResumeResponse(

        UUID id,

        String originalFileName,

        Long fileSize,

        LocalDateTime uploadedAt

) {
}