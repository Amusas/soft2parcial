package product.service.parcial2soft.dto;

import java.time.LocalDateTime;

// Record para respuestas de error simples
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path);
    }
}
