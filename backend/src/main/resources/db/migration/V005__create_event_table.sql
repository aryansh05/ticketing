CREATE TYPE event_status AS ENUM (
    'DRAFT',
    'PUBLISHED',
    'CANCELLED',
    'COMPLETED'
);

CREATE TYPE event_visibility AS ENUM (
    'PUBLIC',
    'PRIVATE'
);

CREATE TYPE event_category AS ENUM (
    'MUSIC',
    'SPORTS',
    'COMEDY',
    'THEATRE',
    'CONFERENCE',
    'WORKSHOP',
    'EXHIBITION',
    'FESTIVAL',
    'OTHER'
);

CREATE TABLE events (
    event_id            UUID PRIMARY KEY DEFAULT Uuidv7(),
    event_created_by    UUID NOT NULL,
    event_title         VARCHAR(200) NOT NULL,
    event_description   TEXT,
    event_category      event_category NOT NULL DEFAULT 'OTHER',
    event_status        event_status NOT NULL DEFAULT 'DRAFT',
    event_visibility    event_visibility NOT NULL DEFAULT 'PUBLIC',
    event_start_time    TIMESTAMPTZ NOT NULL,
    event_end_time      TIMESTAMPTZ NOT NULL,
    event_created_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    event_updated_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_event_creator
    FOREIGN KEY (event_created_by)
    REFERENCES users(user_id),

    CONSTRAINT chk_event_time
    CHECK (event_end_time > event_start_time)
);