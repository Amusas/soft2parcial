package product.service.parcial2soft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.service.parcial2soft.dto.cliente.LoginRequest;
import product.service.parcial2soft.dto.cliente.LoginResponse;
import product.service.parcial2soft.service.interfaces.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("🔐 Login solicitado para: {}", loginRequest.email());
        LoginResponse token = authService.login(loginRequest);
        log.info("✅ Login exitoso para: {}", loginRequest.email());
        return ResponseEntity.ok(token);
    }

}
