CREATE TABLE generated_resumes (
    id UUID PRIMARY KEY,
    resume_id UUID NOT NULL,
    analysis_id UUID NOT NULL,
    job_description TEXT NOT NULL,
    version INTEGER NOT NULL,
    resume_content JSONB NOT NULL,
    latex_code TEXT,
    created_at TIMESTAMP NOT NULL
);