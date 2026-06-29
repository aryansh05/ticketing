package io.github.aryansh05.ticketing.event.controller;

import io.github.aryansh05.ticketing.event.domain.repository.EventRepository;
import io.github.aryansh05.ticketing.event.dto.request.CreateEventRequest;
import io.github.aryansh05.ticketing.event.dto.request.UpdateEventRequest;
import io.github.aryansh05.ticketing.event.dto.response.EventResponse;
import io.github.aryansh05.ticketing.event.service.EventService;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/me")
    public ResponseEntity<List<EventResponse>> getAllMyEvents(){
        List<EventResponse> events = eventService.getAllMyEvents();
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse> updateEvent(@Valid @RequestBody UpdateEventRequest request, @PathVariable String id){
        UUID eventId = UUID.fromString(id);
        ApiSuccessResponse response = eventService.updateEvent(request, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse> deleteEvent(@PathVariable String id){
        UUID eventId = UUID.fromString(id);
        ApiSuccessResponse response = eventService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
