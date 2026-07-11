package io.github.aryansh05.ticketing.event.service;

import io.github.aryansh05.ticketing.auth.security.AuthenticatedUser;
import io.github.aryansh05.ticketing.auth.security.UserPrincipal;
import io.github.aryansh05.ticketing.event.domain.entity.Event;
import io.github.aryansh05.ticketing.event.domain.repository.EventRepository;
import io.github.aryansh05.ticketing.event.dto.request.CreateEventRequest;
import io.github.aryansh05.ticketing.event.dto.request.UpdateEventRequest;
import io.github.aryansh05.ticketing.event.dto.response.EventResponse;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import io.github.aryansh05.ticketing.shared.exception.OwnershipException;
import io.github.aryansh05.ticketing.shared.exception.ResourceAlreadyExistsException;
import io.github.aryansh05.ticketing.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AuthenticatedUser authenticatedUser;

    public ApiSuccessResponse createEvent(CreateEventRequest request){
        UserPrincipal user = authenticatedUser.getAuthenticatedUser();
        if(eventRepository.existsByTitleAndUserIdAndStartTime(request.title(), user.id(), request.startTime())) throw new ResourceAlreadyExistsException("Title already exists at the requested Time");
        Event event = Event.builder()
                .title(request.title())
                .description(request.description())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .userId(user.id())
                .build();
        eventRepository.save(event);

        return new ApiSuccessResponse(
                true,
                "Event created successfully"
        );
    }

    public List<EventResponse> getAllMyEvents(){
        UserPrincipal user = authenticatedUser.getAuthenticatedUser();
        return eventRepository.findByUserId(user.id())
                .stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getUserId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getCategory(),
                        event.getStatus(),
                        event.getVisibility(),
                        event.getStartTime(),
                        event.getEndTime()
                ))
                .toList();
    }

    public ApiSuccessResponse updateEvent(UpdateEventRequest request, UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Event not found"
                        )
                );

        UserPrincipal user = authenticatedUser.getAuthenticatedUser();

        if (!event.getUserId().equals(user.id())) {
            throw new OwnershipException(
                    "You can only modify your own events"
            );
        }

        String title = event.getTitle();
        if (request.title() != null) {
            title = request.title();
        }

        Instant startTime = event.getStartTime();
        if (request.startTime() != null) {
            startTime = request.startTime();
        }

        Instant endTime = event.getEndTime();
        if (request.endTime() != null) {
            endTime = request.endTime();
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        boolean uniqueFieldsChanged = !title.equals(event.getTitle()) || !startTime.equals(event.getStartTime());

        if (uniqueFieldsChanged &&
                eventRepository
                        .existsByTitleAndUserIdAndStartTimeAndIdNot(
                                title,
                                user.id(),
                                startTime,
                                eventId
                        )) {

            throw new ResourceAlreadyExistsException(
                    "Event with the same title and start time already exists"
            );
        }

        if (request.title() != null) {
            event.setTitle(title);
        }

        if (request.description() != null) {
            event.setDescription(request.description());
        }

        if (request.category() != null) {
            event.setCategory(request.category());
        }

        if (request.status() != null) {
            event.setStatus(request.status());
        }

        if (request.visibility() != null) {
            event.setVisibility(request.visibility());
        }

        if (request.startTime() != null) {
            event.setStartTime(startTime);
        }

        if (request.endTime() != null) {
            event.setEndTime(endTime);
        }

        event.setUpdatedAt(Instant.now());
        eventRepository.save(event);

        return new ApiSuccessResponse(
                true,
                "Event updated successfully"
        );
    }

    public ApiSuccessResponse deleteEvent(UUID eventId){
       Event event = eventRepository.findById(eventId)
               .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
       UserPrincipal user = authenticatedUser.getAuthenticatedUser();
       if(!event.getUserId().equals(user.id())) throw new OwnershipException("You can only delete your own events");
       eventRepository.delete(event);
       return new ApiSuccessResponse(
               true,
               "Event deleted successfully"
       );
    }

}
