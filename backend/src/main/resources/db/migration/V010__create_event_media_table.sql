CREATE TABLE event_media
(
    event_media_id UUID PRIMARY KEY,

    event_id UUID NOT NULL,

    media_id UUID NOT NULL,

    media_role media_role NOT NULL,

    display_order INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT fk_event_media_event
        FOREIGN KEY (event_id)
            REFERENCES events(event_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_event_media_media
        FOREIGN KEY (media_id)
            REFERENCES media(media_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_event_media
        UNIQUE (event_id, media_id),

    CONSTRAINT chk_event_media_display_order
        CHECK (display_order >= 0)
);