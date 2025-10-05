package product.service.parcial2soft.exception_handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import product.service.parcial2soft.dto.ErrorResponse;
import product.service.parcial2soft.exceptions.gafas.CodigoDuplicadoException;
import product.service.parcial2soft.exceptions.gafas.PrecioInvalidoException;
import product.service.parcial2soft.exceptions.gafas.ProductoNoDisponibleException;
import product.service.parcial2soft.exceptions.gafas.StockInsuficienteException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GafasExceptionHandler {

    /**
     * Maneja la excepción cuando se intenta crear un producto con código duplicado
     */
    @ExceptionHandler(CodigoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleCodigoDuplicadoException(
            CodigoDuplicadoException ex, WebRequest request) {

        log.warn("Intento de crear producto con código duplicado: {}", ex.getCodigo());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Código Duplicado",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Maneja la excepción cuando el precio de un producto es inválido
     */
    @ExceptionHandler(PrecioInvalidoException.class)
    public ResponseEntity<ErrorResponse> handlePrecioInvalidoException(
            PrecioInvalidoException ex, WebRequest request) {

        log.warn("Precio inválido: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Precio Inválido",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja la excepción cuando un producto no está disponible
     */
    @ExceptionHandler(ProductoNoDisponibleException.class)
    public ResponseEntity<ErrorResponse> handleProductoNoDisponibleException(
            ProductoNoDisponibleException ex, WebRequest request) {

        log.warn("Producto no disponible: {}", ex.getCodigoProducto());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Producto No Disponible",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja la excepción cuando no hay stock suficiente
     */
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<ErrorResponse> handleStockInsuficienteException(
            StockInsuficienteException ex, WebRequest request) {

        log.warn("Stock insuficiente - Producto: {}, Disponible: {}, Solicitado: {}",
                ex.getCodigoProducto(), ex.getStockDisponible(), ex.getStockSolicitado());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Stock Insuficiente",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}