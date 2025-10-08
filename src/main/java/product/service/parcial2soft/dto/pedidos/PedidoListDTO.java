package product.service.parcial2soft.dto.pedidos;

import product.service.parcial2soft.entity.pedido.EstadoPedido;
import product.service.parcial2soft.entity.pedido.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO simplificado para listados
 */
public record PedidoListDTO(
        Long id,
        String numeroPedido,
        String nombreCliente,
        BigDecimal total,
        EstadoPedido estado,
        MetodoPago metodoPago,
        LocalDateTime fechaCreacion
) {}
