package com.groupe.projet_android_AL.auth.services;

import com.groupe.projet_android_AL.auth.models.RefreshToken;
import com.groupe.projet_android_AL.auth.records.LoginRequest;
import com.groupe.projet_android_AL.auth.records.RefreshRequest;
import com.groupe.projet_android_AL.auth.records.TokenPair;
import com.groupe.projet_android_AL.auth.repositories.RefreshTokenRepository;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {
    private final UsersRepository usersRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Duration refreshTtl;

    AuthService(
            UsersRepository usersRepository,
            RefreshTokenRepository refreshTokenRepository,
            JwtService jwtService,
            @Value("${jwt.refresh-expiration}") Duration rTtl
    ) {
        this.usersRepository = usersRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.refreshTtl = rTtl;
    }

    public TokenPair login(LoginRequest loginRequest) {
        Users user = usersRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials"));

        if (!encoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }

        String access = jwtService.generateAccessToken(user.getId());
        String jti = UUID.randomUUID().toString();
        String refresh = jwtService.generateRefreshToken(user.getId(), jti);

        RefreshToken rt = new RefreshToken();
        rt.setJti(jti);
        rt.setUserId(user.getId());
        rt.setExpiresAt(Instant.now().plus(refreshTtl));
        refreshTokenRepository.save(rt);

        return new TokenPair(access, refresh);
    }

    public TokenPair refreshToken(RefreshRequest req) {
        String token = req.refreshToken();
        if (token == null || token.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }

        if (!"refresh".equals(jwtService.getType(token))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
        }

        Integer userId = jwtService.getUserId(token);
        String jti = jwtService.getJti(token);

        RefreshToken current = refreshTokenRepository.findByJtiAndRevokedFalse(jti)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token"));

        if(current.getExpiresAt().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Expired");
        }

        current.setRevoked(true);
        refreshTokenRepository.save(current);

        String newAccess = jwtService.generateAccessToken(userId);
        String newJti = UUID.randomUUID().toString();
        String newRefresh = jwtService.generateRefreshToken(userId, newJti);

        RefreshToken next = new RefreshToken();
        next.setJti(newJti);
        next.setExpiresAt(Instant.now().plus(refreshTtl));
        next.setUserId(userId);
        refreshTokenRepository.save(next);

        return new TokenPair(newAccess, newRefresh);
    }

    public void logoutByRefresh(String refreshToken) {
        String jti = jwtService.getJti(refreshToken);
        refreshTokenRepository.findByJti(jti)
                        .ifPresent(rt -> {rt.setRevoked(true); refreshTokenRepository.save(rt);});
    }
}
