package io.github.aryansh05.ticketing.event.service;

import io.github.aryansh05.ticketing.auth.security.AuthenticatedUser;
import io.github.aryansh05.ticketing.auth.security.UserPrincipal;
import io.github.aryansh05.ticketing.event.domain.entity.Event;
import io.github.aryansh05.ticketing.event.domain.repository.EventRepository;
import io.github.aryansh05.ticketing.event.dto.request.CreateEventRequest;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AuthenticatedUser authenticatedUser;

    public ApiSuccessResponse createEvent(CreateEventRequest request){
        UserPrincipal user = authenticatedUser.getAuthenticatedUser();
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



}
