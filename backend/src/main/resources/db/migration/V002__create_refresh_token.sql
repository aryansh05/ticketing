CREATE TABLE refresh_token(
    jti           UUID PRIMARY KEY DEFAULT Uuidv7(),
    user_id       UUID NOT NULL UNIQUE,
    token         VARCHAR(255) NOT NULL UNIQUE,
    expires_at    TIMESTAMPTZ NOT NULL
)