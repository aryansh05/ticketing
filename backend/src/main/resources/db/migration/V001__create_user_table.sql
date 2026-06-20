-- auth_provider enum
CREATE TYPE auth_provider AS ENUM(
    'LOCAL',
    'GOOGLE',
    'GITHUB'
);

-- users table
CREATE TABLE users(
    user_id            UUID PRIMARY KEY DEFAULT uuidv7(),
    user_full_name     VARCHAR(100) NOT NULL,
    user_email         VARCHAR(255) NOT NULL UNIQUE,
    user_password_hash VARCHAR(255),
    user_active        BOOLEAN NOT NULL DEFAULT TRUE,
    user_auth_provider auth_provider NOT NULL DEFAULT 'LOCAL',
    user_created_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_updated_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);