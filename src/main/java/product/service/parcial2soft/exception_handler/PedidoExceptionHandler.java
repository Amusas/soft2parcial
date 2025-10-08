package product.service.parcial2soft.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import lombok.extern.slf4j.Slf4j;
import product.service.parcial2soft.dto.ErrorResponse;
import product.service.parcial2soft.exceptions.pedido.*;

@Slf4j
@RestControllerAdvice
public class PedidoExceptionHandler {

    /**
     * Maneja la excepción cuando un pago es rechazado
     */
    @ExceptionHandler(PagoRechazadoException.class)
    public ResponseEntity<ErrorResponse> handlePagoRechazadoException(
            PagoRechazadoException ex, WebRequest request) {

        log.warn("Pago rechazado - Método: {}, Código Error: {}, Mensaje: {}",
                ex.getMetodoPago(), ex.getCodigoError(), ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.PAYMENT_REQUIRED.value(),
                "Pago Rechazado",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.PAYMENT_REQUIRED);
    }

    /**
     * Maneja la excepción cuando hay un error procesando el pago
     */
    @ExceptionHandler(PagoFallidoException.class)
    public ResponseEntity<ErrorResponse> handlePagoFallidoException(
            PagoFallidoException ex, WebRequest request) {

        log.error("Pago fallido - Método: {}, Mensaje: {}",
                ex.getMetodoPago(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error en Procesamiento de Pago",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja la excepción cuando el pedido no puede ser procesado
     */
    @ExceptionHandler(PedidoNoProcesableException.class)
    public ResponseEntity<ErrorResponse> handlePedidoNoProcesableException(
            PedidoNoProcesableException ex, WebRequest request) {

        log.warn("Pedido no procesable - Código: {}, Mensaje: {}",
                ex.getCodigoPedido(), ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Pedido No Procesable",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Maneja la excepción cuando se intenta cancelar un pedido que no puede ser cancelado
     */
    @ExceptionHandler(PedidoNoCancelableException.class)
    public ResponseEntity<ErrorResponse> handlePedidoNoCancelableException(
            PedidoNoCancelableException ex, WebRequest request) {

        log.warn("Pedido no cancelable - Número: {}, Estado: {}",
                ex.getNumeroPedido(), ex.getEstadoActual());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Pedido No Cancelable",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Maneja la excepción cuando los datos del pago son inválidos
     */
    @ExceptionHandler(DatosPagoInvalidosException.class)
    public ResponseEntity<ErrorResponse> handleDatosPagoInvalidosException(
            DatosPagoInvalidosException ex, WebRequest request) {

        log.warn("Datos de pago inválidos - Método: {}, Mensaje: {}",
                ex.getMetodoPago(), ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Datos de Pago Inválidos",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja la excepción cuando no se encuentra un pedido
     */
    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handlePedidoNoEncontradoException(
            PedidoNoEncontradoException ex, WebRequest request) {

        log.warn("Pedido no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Pedido No Encontrado",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}