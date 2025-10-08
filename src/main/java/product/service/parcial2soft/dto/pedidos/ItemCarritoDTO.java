package product.service.parcial2soft.dto.pedidos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para un item del carrito
 */
public record ItemCarritoDTO(
        @NotNull(message = "El ID de las gafas es obligatorio")
        @Positive(message = "El ID de las gafas debe ser positivo")
        Long gafasId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad mínima es 1")
        @Max(value = 100, message = "La cantidad máxima es 100")
        Integer cantidad
) {}