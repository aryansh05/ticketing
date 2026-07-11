package io.github.aryansh05.ticketing.event.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import io.github.aryansh05.ticketing.media.domain.entity.MediaRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "event_media")
public class EventMedia {

    @Id
    @Builder.Default
    @Column(name = "event_media_id")
    private UUID id = UuidCreator.getTimeOrderedEpoch();

    @Column(name = "event_id", nullable = false, updatable = false)
    private UUID eventId;

    @Column(name = "media_id", nullable = false, updatable = false)
    private UUID mediaId;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "media_role", nullable = false)
    private MediaRole role;

    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;
}