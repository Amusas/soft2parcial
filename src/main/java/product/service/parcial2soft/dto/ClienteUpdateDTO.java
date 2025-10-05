package product.service.parcial2soft.dto;

import jakarta.validation.constraints.*;

// DTO de entrada para actualizar cliente
public record ClienteUpdateDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
        String apellido,

        @Email(message = "El email debe ser válido")
        @Size(max = 200, message = "El email no puede exceder 200 caracteres")
        String email,

        @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "El teléfono solo puede contener números y caracteres: +, -, (), espacios")
        @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
        String telefono,

        @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
        String direccion,

        @NotNull(message = "El estado activo es obligatorio")
        Boolean activo
) {}