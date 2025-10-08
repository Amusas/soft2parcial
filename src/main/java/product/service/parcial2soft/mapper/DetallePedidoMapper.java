package product.service.parcial2soft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import product.service.parcial2soft.dto.pedidos.DetallePedidoDTO;
import product.service.parcial2soft.entity.pedido.DetallePedido;
import java.util.List;
import org.mapstruct.*;
import org.hibernate.Hibernate;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {

    @Mapping(target = "codigoProducto", source = "gafas.codigo")
    @Mapping(target = "nombreProducto", source = "gafas.nombre")
    @Mapping(target = "marcaProducto", source = "gafas.marca")
    DetallePedidoDTO toDTO(DetallePedido entity);

    List<DetallePedidoDTO> toDTOList(List<DetallePedido> entities);

    @AfterMapping
    default void asegurarGafasInicializadas(DetallePedido detalle, @MappingTarget DetallePedidoDTO dto) {
        // Forzar la inicialización de la relación LAZY
        if (detalle.getGafas() != null) {
            Hibernate.initialize(detalle.getGafas());
        }
    }
}

