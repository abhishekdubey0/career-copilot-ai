package com.careercopilot.intelligence.repository;

import com.careercopilot.intelligence.entity.ResumeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, UUID> {

    Optional<ResumeAnalysis> findTopByResumeIdOrderByCreatedAtDesc(
            UUID resumeId
    );
}