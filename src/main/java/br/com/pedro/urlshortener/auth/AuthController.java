package br.com.pedro.urlshortener.auth;

import br.com.pedro.urlshortener.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthService authService;

    public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder encoder, AuthService authService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
       User userFound = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(encoder.matches(request.password(), userFound.getPassword())) {
            LoginResponseDTO response = authService.authenticate(userFound);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody RegisterRequestDTO request) {
        final var registeredUser = userService
                .registerUser(request);
        return ResponseEntity.ok(new UserResponseDTO(registeredUser.getUsername(), registeredUser.getEmail()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(@RequestParam UUID refreshToken) {
       LoginResponseDTO response = authService.refreshToken(refreshToken);
       return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestParam UUID refreshToken) {
        authService.revokeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
