package io.github.aryansh05.ticketing.event.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CreateEventRequest(
    @NotBlank(message = "Title is required")
    @Size(max=200)
    String title,
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    String description,
    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    Instant startTime,
    @NotNull(message = "End time is required")
    Instant endTime
) {
    public CreateEventRequest{
        if(startTime != null && endTime != null && !endTime.isAfter(startTime)){
            throw new IllegalArgumentException("End time must be after start time");
        }

        if (title != null) {
            title = title.trim();
        }

        if (description != null) {
            description = description.trim();
        }
    }
}
