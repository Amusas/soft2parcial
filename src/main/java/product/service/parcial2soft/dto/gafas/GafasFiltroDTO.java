package product.service.parcial2soft.dto.gafas;

import product.service.parcial2soft.entity.gafas.MaterialMontura;
import product.service.parcial2soft.entity.gafas.TipoGafas;

import java.math.BigDecimal;

public record GafasFiltroDTO(
        TipoGafas tipo,
        String marca,
        MaterialMontura material,
        BigDecimal precioMin,
        BigDecimal precioMax,
        Boolean disponible,
        Boolean activo
) {}