CREATE TABLE roles
(
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

INSERT INTO roles (id, name, created_at, updated_at)
VALUES
(gen_random_uuid(), 'ROLE_ADMIN', NOW(), NOW()),
(gen_random_uuid(), 'ROLE_USER', NOW(), NOW());