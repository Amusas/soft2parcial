package product.service.parcial2soft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.service.parcial2soft.dto.cliente.LoginRequest;
import product.service.parcial2soft.dto.cliente.LoginResponse;
import product.service.parcial2soft.entity.Cliente;
import product.service.parcial2soft.exceptions.cliente.ClienteNotFoundException;
import product.service.parcial2soft.exceptions.cliente.IncorrectPasswordException;
import product.service.parcial2soft.repository.ClienteRepository;
import product.service.parcial2soft.service.interfaces.AuthService;
import product.service.parcial2soft.util.JwtUtils;
import product.service.parcial2soft.util.PasswordEncoderUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Intentando iniciar sesión para el usuario con email: {}", loginRequest.email());
        // Buscar usuario en el user-service
        Cliente user = clienteRepository.findByEmail(loginRequest.email()).orElseThrow(
                () -> new ClienteNotFoundException("cliente no encontrado")
        );

        // Validar contraseña en este microservicio
        if (!passwordEncoderUtil.matches(loginRequest.password(), user.getPassword())) {
            log.error("Contraseña incorrecta para el usuario {}", loginRequest.email());
            throw new IncorrectPasswordException("Contraseña incorrecta para el usuario " + loginRequest.email());
        }

        // Generar token JWT
        String token = jwtUtils.generateToken(user);
        log.info("Token JWT generado exitosamente para el usuario {}", loginRequest.email());
        return createResponse(user, token);

    }

    private LoginResponse createResponse(Cliente cliente, String token) {
        return new LoginResponse(cliente.getId(),
                cliente.getNombre() + " " + cliente.getApellido(), token);
    }
}