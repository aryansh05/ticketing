CREATE TYPE media_type AS ENUM (
    'IMAGE',
    'VIDEO'
);

CREATE TYPE media_role AS ENUM (
    'COVER',
    'GALLERY',
    'TRAILER'
);

CREATE TABLE media
(
    media_id         UUID PRIMARY KEY,

    media_url        VARCHAR(1000) NOT NULL,

    media_public_id  VARCHAR(500)  NOT NULL UNIQUE,

    media_format     VARCHAR(20) NOT NULL,

    media_type       media_type    NOT NULL,

    media_bytes      BIGINT        NOT NULL,

    media_created_at TIMESTAMPTZ   NOT NULL DEFAULT CURRENT_TIMESTAMP
);