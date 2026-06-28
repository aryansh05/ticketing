package io.github.aryansh05.ticketing.event.dto.response;

import io.github.aryansh05.ticketing.event.domain.entity.EventCategory;
import io.github.aryansh05.ticketing.event.domain.entity.EventStatus;
import io.github.aryansh05.ticketing.event.domain.entity.EventVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public record EventResponse(
        UUID id,
        UUID userId,

        String title,
        String description,

        Instant startTime,
        Instant endTime,

        EventCategory category,
        EventStatus status,
        EventVisibility visibility
) {
}
