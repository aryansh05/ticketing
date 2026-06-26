package io.github.aryansh05.ticketing.auth.domain.repository;

import io.github.aryansh05.ticketing.auth.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    void deleteByUserId(UUID userId);
}
