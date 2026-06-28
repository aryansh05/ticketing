package io.github.aryansh05.ticketing.event.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record EventResponse(
        @NotBlank
        @Size(max=200)
        String title,
        @NotBlank
        String description,
        @NotNull
        Instant startTime,
        @NotNull
        Instant endTime


) {
}
