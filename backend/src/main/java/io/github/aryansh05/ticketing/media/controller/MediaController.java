package io.github.aryansh05.ticketing.media.controller;

import io.github.aryansh05.ticketing.media.domain.entity.MediaRole;
import io.github.aryansh05.ticketing.media.dto.response.MediaResponse;
import io.github.aryansh05.ticketing.event.service.EventMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class MediaController {

    private final EventMediaService eventMediaService;

    @PostMapping(value = "/{eventId}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaResponse> uploadEventMedia(
            @PathVariable UUID eventId,
            @RequestParam("role") MediaRole role,
            @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
            @RequestPart("file") MultipartFile file
    ) {
        MediaResponse response =eventMediaService.uploadEventMedia(file, eventId, role, displayOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{eventId}/media")
    public ResponseEntity<List<MediaResponse>> getEventMedia(
            @PathVariable UUID eventId
    ) {

        return ResponseEntity.ok(
                eventMediaService.getEventMedia(eventId)
        );
    }
}