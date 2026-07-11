package io.github.aryansh05.ticketing.event.domain.repository;

import io.github.aryansh05.ticketing.event.domain.entity.EventMedia;
import io.github.aryansh05.ticketing.media.domain.entity.MediaRole;
import io.github.aryansh05.ticketing.media.dto.response.MediaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventMediaRepository extends JpaRepository<EventMedia, UUID> {
    List<EventMedia> findByEventId(UUID eventId);

    void deleteByEventIdAndMediaId(
            UUID eventId,
            UUID mediaId
    );
    boolean existsByEventIdAndRole(
            UUID eventId,
            MediaRole role
    );
    @Query("""
            SELECT new io.github.aryansh05.ticketing.media.dto.response.MediaResponse(
                m.id,
                m.url,
                m.format,
                m.type,
                em.role,
                m.bytes,
                em.displayOrder
            )
            FROM EventMedia em
            JOIN Media m
                ON m.id = em.mediaId
            WHERE em.eventId = :eventId
            ORDER BY em.displayOrder ASC
            """)
    List<MediaResponse> findMediaByEventId(
            @Param("eventId") UUID eventId
    );
}
