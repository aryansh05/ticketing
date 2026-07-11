package io.github.aryansh05.ticketing.media.dto.response;

import io.github.aryansh05.ticketing.media.domain.entity.MediaType;

public record StoredMedia(
        String url,
        String publicId,
        String format,
        MediaType type,
        Long bytes
) {
}