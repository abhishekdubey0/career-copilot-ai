package com.careercopilot.auth.dto.response;

import java.util.List;

public record UserProfileResponse(

        String email,
        List<String> roles

) {
}