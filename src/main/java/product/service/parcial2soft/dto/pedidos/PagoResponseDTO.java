package product.service.parcial2soft.dto.pedidos;

import product.service.parcial2soft.entity.pedido.EstadoPago;
import product.service.parcial2soft.entity.pedido.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta del pago
 */
public record PagoResponseDTO(
        Long id,
        BigDecimal montoPagado,
        MetodoPago metodoPago,
        EstadoPago estadoPago,
        String numeroTarjeta, // Enmascarado
        String nombreTitular,
        Integer cuotas,
        BigDecimal valorCuota,
        String numeroReferencia,
        String bancoOrigen,
        String numeroCelular,
        String nombreCuenta,
        String codigoAutorizacion,
        String mensajeRespuesta,
        LocalDateTime fechaProcesamiento
) {}