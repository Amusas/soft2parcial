package product.service.parcial2soft.dto.cliente;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la solicitud de inicio de sesión.
 * Contiene las credenciales necesarias (email y contraseña) para autenticar un usuario.
 */
public record LoginRequest(

        @NotBlank(message = "El correo electrónico es obligatorio")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password

) {
}
