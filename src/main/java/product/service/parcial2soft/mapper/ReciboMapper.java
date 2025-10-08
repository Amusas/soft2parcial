package product.service.parcial2soft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import product.service.parcial2soft.dto.pedidos.ReciboResponseDTO;
import product.service.parcial2soft.entity.pedido.Recibo;

@Mapper(componentModel = "spring")
public interface ReciboMapper {

    ReciboResponseDTO toResponseDTO(Recibo entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "numeroRecibo", ignore = true)
    @Mapping(target = "fechaEmision", ignore = true)
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "motivoAnulacion", ignore = true)
    @Mapping(target = "fechaAnulacion", ignore = true)
    Recibo toEntity(java.math.BigDecimal total);
}
