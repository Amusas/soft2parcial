package product.service.parcial2soft.dto;

import java.time.LocalDateTime;
import java.util.List;

// Record para respuestas de error con validaciones
public record ValidationErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldError> fieldErrors
) {
    public ValidationErrorResponse(int status, String error, String message, String path, List<FieldError> fieldErrors) {
        this(LocalDateTime.now(), status, error, message, path, fieldErrors);
    }

    // Record para errores de campo específicos
    public record FieldError(
            String field,
            String message
    ) {}
}

