DROP TABLE IF EXISTS refresh_token;

CREATE TABLE refresh_tokens(
    jti           UUID PRIMARY KEY DEFAULT Uuidv7(),
    user_id       UUID NOT NULL UNIQUE,
    expires_at    TIMESTAMPTZ NOT NULL
)