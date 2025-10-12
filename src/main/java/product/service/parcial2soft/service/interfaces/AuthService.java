package product.service.parcial2soft.service.interfaces;

import product.service.parcial2soft.dto.cliente.LoginRequest;
import product.service.parcial2soft.dto.cliente.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}

