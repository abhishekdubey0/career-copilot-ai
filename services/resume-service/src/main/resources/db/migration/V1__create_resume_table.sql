CREATE TABLE resumes
(
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,

    original_file_name VARCHAR(255) NOT NULL,

    stored_file_name VARCHAR(255) NOT NULL UNIQUE,

    file_path VARCHAR(500) NOT NULL,

    content_type VARCHAR(100) NOT NULL,

    file_size BIGINT NOT NULL,

    extracted_text TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_resume_user
ON resumes(user_id);