package product.service.parcial2soft.dto.pedidos;

import java.math.BigDecimal;

/**
 * DTO para detalle del pedido
 */
public record DetallePedidoDTO(
        Long id,
        String codigoProducto,
        String nombreProducto,
        String marcaProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {}