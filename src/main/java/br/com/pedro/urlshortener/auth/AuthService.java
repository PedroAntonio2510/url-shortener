package br.com.pedro.urlshortener.auth;

import br.com.pedro.urlshortener.jwt.JwtService;
import br.com.pedro.urlshortener.jwt.RefreshToken;
import br.com.pedro.urlshortener.jwt.RefreshTokenRepository;
import br.com.pedro.urlshortener.user.LoginResponseDTO;
import br.com.pedro.urlshortener.user.User;
import br.com.pedro.urlshortener.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(JwtService jwtService, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponseDTO authenticate(User user) {
        String acessToken = jwtService.generateToken(user);

        User userFound = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userFound);
        refreshToken.setExpiration(Instant.now().plusSeconds(3600L));
        refreshToken.setCreated(Instant.now());

        refreshTokenRepository.save(refreshToken);
        return new LoginResponseDTO(user.getUsername(), acessToken, refreshToken.getId());
    }

    public LoginResponseDTO refreshToken(UUID refreshToken) {
        final var refreshTokenEntity = refreshTokenRepository
                .findByIdAndExpirationAfter(refreshToken, Instant.now())
                .orElseThrow(() -> new ResponseStatusException(
                        BAD_REQUEST,
                        "Invalid or expired refresh token"));

        final var newAccessToken = jwtService
                .generateToken(refreshTokenEntity.getUser());
        return new LoginResponseDTO(null, newAccessToken, refreshToken);
    }

    public void revokeRefreshToken(UUID refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

}
