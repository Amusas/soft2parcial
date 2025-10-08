package product.service.parcial2soft.dto.pedidos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import product.service.parcial2soft.entity.pedido.MetodoPago;

import java.util.List;

/**
 * DTO para crear un pedido con carrito (múltiples productos)
 */
public record PedidoCreateDTO(
        @NotNull(message = "El ID del cliente es obligatorio")
        @Positive(message = "El ID del cliente debe ser positivo")
        Long clienteId,

        @NotNull(message = "Los items del carrito son obligatorios")
        @Size(min = 1, message = "Debe agregar al menos un producto al carrito")
        @Valid
        List<ItemCarritoDTO> items,

        @NotNull(message = "El método de pago es obligatorio")
        MetodoPago metodoPago,

        @Valid
        @NotNull(message = "Los datos del pago son obligatorios")
        DatosPagoDTO datosPago,

        @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
        String observaciones
) {
        /**
         * Calcula la cantidad total de items en el carrito
         */
        public int cantidadTotalItems() {
                return items.stream()
                        .mapToInt(ItemCarritoDTO::cantidad)
                        .sum();
        }

        /**
         * Valida que no haya productos duplicados en el carrito
         */
        public void validarItemsUnicos() {
                long idsUnicos = items.stream()
                        .map(ItemCarritoDTO::gafasId)
                        .distinct()
                        .count();

                if (idsUnicos != items.size()) {
                        throw new IllegalArgumentException("El carrito contiene productos duplicados. Agrupe las cantidades.");
                }
        }
}
