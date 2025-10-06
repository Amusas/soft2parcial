package product.service.parcial2soft.dto.pedidos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import product.service.parcial2soft.entity.pedido.MetodoPago;

/**
 * DTO para crear un pedido simple (un solo producto)
 */
public record PedidoCreateDTO(
        @NotNull(message = "El ID del cliente es obligatorio")
        @Positive(message = "El ID del cliente debe ser positivo")
        Long clienteId,

        @NotNull(message = "El ID de las gafas es obligatorio")
        @Positive(message = "El ID de las gafas debe ser positivo")
        Long gafasId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad mínima es 1")
        @Max(value = 100, message = "La cantidad máxima es 100")
        Integer cantidad,

        @NotNull(message = "El método de pago es obligatorio")
        MetodoPago metodoPago,

        @Valid
        @NotNull(message = "Los datos del pago son obligatorios")
        DatosPagoDTO datosPago,

        @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
        String observaciones
) {}