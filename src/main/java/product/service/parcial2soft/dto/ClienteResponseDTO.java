package product.service.parcial2soft.dto;

import java.time.LocalDateTime;

// DTO de salida
public record ClienteResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String direccion,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {}
