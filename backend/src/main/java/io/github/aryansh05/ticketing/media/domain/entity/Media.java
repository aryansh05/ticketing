package io.github.aryansh05.ticketing.media.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "media")
public class Media {
    @Id
    @Builder.Default
    @Column(name = "media_id")
    private UUID id = UuidCreator.getTimeOrderedEpoch();

    @Column(name = "media_url", nullable = false, length = 1000)
    private String url;

    @Column(name = "media_public_id", nullable = false, unique = true, length = 500)
    private String publicId;

    @Column(name = "media_format", nullable = false, length = 20)
    private String format;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "media_type", nullable = false)
    private MediaType type;

    @Column(name = "media_bytes", nullable = false)
    private Long bytes;

    @Column(name = "media_created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
