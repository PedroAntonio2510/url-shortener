package br.com.pedro.urlshortener.user;

import java.util.UUID;

public record LoginResponseDTO(
        String username,
        String token,
        UUID refreshToken
) {
}
