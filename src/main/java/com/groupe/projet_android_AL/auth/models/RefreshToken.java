package com.groupe.projet_android_AL.auth.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken {
    @Id
    private String jti;

    private Integer userId;

    private Instant expiresAt;

    private Boolean revoked = false;
}