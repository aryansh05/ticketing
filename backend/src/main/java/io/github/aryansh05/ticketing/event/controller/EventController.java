package io.github.aryansh05.ticketing.event.controller;

import io.github.aryansh05.ticketing.event.domain.repository.EventRepository;
import io.github.aryansh05.ticketing.event.dto.request.CreateEventRequest;
import io.github.aryansh05.ticketing.event.service.EventService;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {
    private final EventRepository eventRepository;
    private final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResponse> createEvent(@Valid @RequestBody CreateEventRequest request){
        ApiSuccessResponse response = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
