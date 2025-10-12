package product.service.parcial2soft.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import product.service.parcial2soft.dto.ErrorResponse;
import product.service.parcial2soft.dto.ValidationErrorResponse;
import product.service.parcial2soft.exceptions.cliente.ClienteExitsException;
import product.service.parcial2soft.exceptions.cliente.ClienteNotFoundException;
import product.service.parcial2soft.exceptions.cliente.IncorrectPasswordException;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class ClientExceptionHandler {

    /**
     * Maneja la excepción cuando no se encuentra un cliente
     */
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClienteNotFoundException(
            ClienteNotFoundException ex, WebRequest request) {

        log.warn("Cliente no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción cuando ya existe un cliente
     */
    @ExceptionHandler(ClienteExitsException.class)
    public ResponseEntity<ErrorResponse> handleClienteExistsException(
            ClienteExitsException ex, WebRequest request) {

        log.warn("Cliente ya existe: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Maneja la excepción cuando ya existe un cliente
     */
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException(
            IncorrectPasswordException ex, WebRequest request) {

        log.warn("Contraseña incorrecta: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    /**
     * Maneja errores de validación de Jakarta Validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        log.warn("Error de validación: {}", ex.getMessage());

        // Convertir FieldErrors de Spring a nuestro record FieldError
        List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String fieldName = error instanceof FieldError ?
                            ((FieldError) error).getField() : error.getObjectName();
                    String errorMessage = error.getDefaultMessage();
                    return new ValidationErrorResponse.FieldError(fieldName, errorMessage);
                }).toList();

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Error de validación en los datos de entrada",
                request.getDescription(false).replace("uri=", ""),
                fieldErrors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}