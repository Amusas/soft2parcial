package product.service.parcial2soft.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO de entrada para crear cliente
public record ClienteCreateDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
        String apellido,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 5, max = 20, message = "La contraseña debe tener entre 5 y 20 caracteres")
        String password,

        @Email(message = "El email debe ser válido")
        @Size(max = 200, message = "El email no puede exceder 200 caracteres")
        String email,

        @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "El teléfono solo puede contener números y caracteres: +, -, (), espacios")
        @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
        String telefono,

        @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
        String direccion
) {}