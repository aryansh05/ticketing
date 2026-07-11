package io.github.aryansh05.ticketing.event.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "events",
uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_event_creator_title_start",
                columnNames = {
                        "event_created_by",
                        "event_title",
                        "event_start_time"
                }
        )
}
)
public class Event {
    @Id
    @Builder.Default
    @Column(name = "event_id")
    private UUID id = UuidCreator.getTimeOrderedEpoch();
    @Column(name = "event_created_by", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "event_title", nullable = false, length = 200)
    private String title;
    @Column(name = "event_description", length = 1000)
    private String description;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Builder.Default
    @Column(name = "event_category", nullable = false)
    private EventCategory category = EventCategory.OTHER;
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Builder.Default
    @Column(name = "event_status", nullable = false)
    private EventStatus status = EventStatus.DRAFT;
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Builder.Default
    @Column(name = "event_visibility", nullable = false)
    private EventVisibility visibility = EventVisibility.PUBLIC;

    @Column(name = "event_start_time", nullable = false)
    private Instant startTime;
    @Column(name = "event_end_time", nullable = false)
    private Instant endTime;
    @Builder.Default
    @Column(name = "event_created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
    @Builder.Default
    @Column(name = "event_updated_at", nullable = false)
    private Instant updatedAt = Instant.now();
}
