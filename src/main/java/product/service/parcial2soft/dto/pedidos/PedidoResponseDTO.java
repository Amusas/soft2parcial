package product.service.parcial2soft.dto.pedidos;

import product.service.parcial2soft.dto.cliente.ClienteResponseDTO;
import product.service.parcial2soft.entity.pedido.EstadoPedido;
import product.service.parcial2soft.entity.pedido.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * DTO de respuesta completa del pedido
 */
public record PedidoResponseDTO(
        Long id,
        String numeroPedido,
        ClienteResponseDTO cliente,
        List<DetallePedidoDTO> detalles,
        BigDecimal subtotal,
        BigDecimal total,
        EstadoPedido estado,
        MetodoPago metodoPago,
        String observaciones,
        PagoResponseDTO pago,
        ReciboResponseDTO recibo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaPago
) {}