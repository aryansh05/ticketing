ALTER TABLE events
DROP CONSTRAINT uq_event_creator_title;

ALTER TABLE events
    ADD CONSTRAINT uq_event_creator_title_start
        UNIQUE (
                event_created_by,
                event_title,
                event_start_time
            );