ALTER TABLE events
DROP CONSTRAINT uq_event_title;

ALTER TABLE events
    ADD CONSTRAINT uq_event_creator_title
        UNIQUE (event_created_by, event_title);