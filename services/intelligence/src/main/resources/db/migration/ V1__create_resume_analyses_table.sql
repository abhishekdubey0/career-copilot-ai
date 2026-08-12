CREATE TABLE resume_analyses (
    id UUID PRIMARY KEY,
    resume_id UUID NOT NULL,
    job_description TEXT NOT NULL,
    ats_score INTEGER NOT NULL,
    matched_requirements TEXT,
    missing_requirements TEXT,
    created_at TIMESTAMP NOT NULL
);