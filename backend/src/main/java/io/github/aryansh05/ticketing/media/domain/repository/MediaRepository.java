package io.github.aryansh05.ticketing.media.domain.repository;

import io.github.aryansh05.ticketing.media.domain.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}
