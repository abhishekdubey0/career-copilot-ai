package com.careercopilot.intelligence.client;

import com.careercopilot.intelligence.common.ApiResponse;
import com.careercopilot.intelligence.exception.ResumeServiceException;
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

        try {

            ApiResponse<String> response =
                    restClient.get()
                            .uri(
                                    "/api/v1/resumes/{resumeId}/text",
                                    resumeId
                            )
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<ApiResponse<String>>() {}
                            );

            if (response == null || response.getData() == null) {
                throw new ResumeServiceException(
                        "Resume text could not be retrieved",
                        null
                );
            }

            return response.getData();

        } catch (ResumeServiceException e) {

            throw e;

        } catch (Exception e) {

            throw new ResumeServiceException(
                    "Unable to communicate with Resume Service",
                    e
            );
        }
    }
}