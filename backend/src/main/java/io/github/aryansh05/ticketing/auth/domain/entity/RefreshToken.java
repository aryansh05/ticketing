package io.github.aryansh05.ticketing.auth.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @Builder.Default
    @Column(name = "jti")
    private UUID id = UuidCreator.getTimeOrderedEpoch();
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;
}
