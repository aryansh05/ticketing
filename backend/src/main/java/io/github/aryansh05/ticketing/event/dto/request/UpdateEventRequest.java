package io.github.aryansh05.ticketing.event.dto.request;

import io.github.aryansh05.ticketing.event.domain.entity.EventCategory;
import io.github.aryansh05.ticketing.event.domain.entity.EventStatus;
import io.github.aryansh05.ticketing.event.domain.entity.EventVisibility;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public record UpdateEventRequest(
        @Size(max=200)
        String title,
        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        EventCategory category,
        EventStatus status,
        EventVisibility visibility,

        @Future(message = "Start time must be in the future")
        Instant startTime,
        Instant endTime
) {
    public UpdateEventRequest{
        if (title != null) {
            title = title.trim();
        }

        if (description != null) {
            description = description.trim();
        }

        if (startTime != null) {
            startTime = startTime.truncatedTo(ChronoUnit.MINUTES);
        }

        if (endTime != null) {
            endTime = endTime.truncatedTo(ChronoUnit.MINUTES);
        }
        if(startTime != null && endTime != null && !endTime.isAfter(startTime)){
            throw new IllegalArgumentException("End time must be after start time");
        }

    }

}
