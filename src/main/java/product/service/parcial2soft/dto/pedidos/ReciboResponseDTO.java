package product.service.parcial2soft.dto.pedidos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta del recibo
 */
public record ReciboResponseDTO(
        Long id,
        String numeroRecibo,
        BigDecimal total,
        LocalDateTime fechaEmision,
        Boolean activo
) {}
