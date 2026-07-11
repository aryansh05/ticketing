package io.github.aryansh05.ticketing.event.domain.repository;

import io.github.aryansh05.ticketing.event.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    boolean existsByTitleAndUserIdAndStartTime(String title, UUID userId, Instant startTime);
    boolean existsByTitleAndUserIdAndStartTimeAndIdNot(String title, UUID userId, Instant startTime, UUID id);
    List<Event> findByUserId(UUID userId);

}
