package br.com.pedro.urlshortener.user;

public record LoginRequestDTO(
        String email,
        String password
) {
}
