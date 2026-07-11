package io.github.aryansh05.ticketing.media.dto.response;

import io.github.aryansh05.ticketing.media.domain.entity.MediaRole;
import io.github.aryansh05.ticketing.media.domain.entity.MediaType;

import java.util.UUID;

public record MediaResponse(
        UUID id,
        String url,
        String format,
        MediaType type,
        MediaRole role,
        Long bytes,
        Integer displayOrder
) {
}