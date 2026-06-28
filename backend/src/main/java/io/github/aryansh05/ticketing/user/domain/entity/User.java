package io.github.aryansh05.ticketing.user.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Builder.Default
    @Column(name = "user_id")
    private UUID id = UuidCreator.getTimeOrderedEpoch();

    @Column(name = "user_full_name", nullable = false, length = 100)
    private String fullName;
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;
    @Column(name = "user_password_hash")
    private String passwordHash;

    @Builder.Default
    @Column(name = "user_active", nullable = false)
    private boolean active = true;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Builder.Default
    @Column(name = "user_auth_provider", nullable = false)
    private AuthProvider authProvider = AuthProvider.LOCAL;

    @Builder.Default
    @Column(name = "user_created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
    @Builder.Default
    @Column(name = "user_updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return getPasswordHash();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
