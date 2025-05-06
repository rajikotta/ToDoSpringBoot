package com.raji.todo.models.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "refresh_tokens")
public class RefreshTokenEntity {
    private Long userId;
    private Instant expiresAt;
    @Id
    private String hashedToken;
    private Instant createdAt;
}
