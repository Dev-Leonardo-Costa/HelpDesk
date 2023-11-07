package br.com.leonardo.authserviceapi.services;

import br.com.leonardo.authserviceapi.models.RefreshToken;
import br.com.leonardo.authserviceapi.repository.RefreshTokenRepository;
import br.com.leonardo.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.leonardo.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpiredException;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long expiration;

    private final RefreshTokenRepository repository;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;

    public RefreshToken save(final String userName) {
        return repository.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID().toString())
                        .cretedAt(now())
                        .expiresAt(now().plusSeconds(expiration))
                        .username(userName)
                        .build()
        );
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId){
        final var refreshToken = repository.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found. Id: " + refreshTokenId));

        if(refreshToken.getExpiresAt().isBefore(now())){
            throw new RefreshTokenExpiredException("Refresh token expired. Id: " + refreshTokenId);
        }

        System.out.println(refreshToken.getUsername());

        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()))
        );
    }
}
