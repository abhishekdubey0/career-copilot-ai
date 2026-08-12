package com.careercopilot.intelligence.client;

import com.careercopilot.intelligence.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ResumeClient {

    private final RestClient restClient;

    public String getResumeText(UUID resumeId) {

        ApiResponse<String> response = restClient.get()
                .uri("/api/v1/resumes/{resumeId}/text", resumeId)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<String>>() {});

        if (response == null || response.getData() == null) {
            throw new IllegalStateException(
                    "Resume text could not be retrieved"
            );
        }

        return response.getData();
    }
}