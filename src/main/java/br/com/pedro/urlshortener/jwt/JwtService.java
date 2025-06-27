package br.com.pedro.urlshortener.jwt;

import br.com.pedro.urlshortener.user.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        long expiry = 3600L;
//
//        String scopes = user.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));

        final var claims = JwtClaimsSet.builder()
                .issuer("url-shortener")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
//                .claim("scope", scopes)
                .subject(user.getUsername())
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
