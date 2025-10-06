package product.service.parcial2soft.dto.pedidos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para cancelar pedido
 */
public record CancelarPedidoDTO(
        @NotBlank(message = "El motivo de cancelación es obligatorio")
        @Size(min = 10, max = 500, message = "El motivo debe tener entre 10 y 500 caracteres")
        String motivo
) {}

