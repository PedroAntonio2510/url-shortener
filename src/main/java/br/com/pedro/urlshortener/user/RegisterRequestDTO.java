package br.com.pedro.urlshortener.user;

public record RegisterRequestDTO(
        String username,
        String email,
        String password
) {
}
