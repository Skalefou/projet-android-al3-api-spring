package com.groupe.projet_android_AL.auth.repositories;

import com.groupe.projet_android_AL.auth.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByJtiAndRevokedFalse(String jti);
    Optional<RefreshToken> findByJti(String jti);
}