-- Make event title unique
ALTER TABLE events
ADD CONSTRAINT uq_event_title UNIQUE (event_title);

-- Change description from TEXT to VARCHAR(2000)
ALTER TABLE events
ALTER COLUMN event_description TYPE VARCHAR(1000);

-- Drop chk_event_time constraint
ALTER TABLE events
DROP CONSTRAINT chk_event_time;