package product.service.parcial2soft.dto.gafas;

import product.service.parcial2soft.entity.gafas.MaterialMontura;
import product.service.parcial2soft.entity.gafas.TipoGafas;
import product.service.parcial2soft.entity.gafas.TipoLente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GafasResponseDTO(
        Long id,
        String codigo,
        String nombre,
        String descripcion,
        String marca,
        String modelo,
        TipoGafas tipo,
        MaterialMontura material,
        String color,
        String genero,
        TipoLente tipoLente,
        String talla,
        BigDecimal precio,
        Integer stock,
        String urlImagen,
        Boolean tieneProteccionUV,
        Boolean esPolarizada,
        Boolean graduable,
        Boolean disponible,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {}
