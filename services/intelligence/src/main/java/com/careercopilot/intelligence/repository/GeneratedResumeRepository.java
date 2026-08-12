package com.careercopilot.intelligence.repository;

import com.careercopilot.intelligence.entity.GeneratedResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GeneratedResumeRepository
        extends JpaRepository<GeneratedResume, UUID> {

    Optional<GeneratedResume> findTopByResumeIdOrderByVersionDesc(
            UUID resumeId
    );
}