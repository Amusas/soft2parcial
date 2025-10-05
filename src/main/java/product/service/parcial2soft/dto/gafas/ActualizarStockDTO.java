package product.service.parcial2soft.dto.gafas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// DTO para actualizar solo el stock
public record ActualizarStockDTO(
        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer cantidad
) {}
