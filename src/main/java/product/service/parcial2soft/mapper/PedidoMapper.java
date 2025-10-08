package product.service.parcial2soft.mapper;

import org.mapstruct.*;
import product.service.parcial2soft.dto.pedidos.PedidoListDTO;
import product.service.parcial2soft.dto.pedidos.PedidoResponseDTO;
import product.service.parcial2soft.entity.pedido.Pedido;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, DetallePedidoMapper.class})
public interface PedidoMapper {

    // Mapear de Entity a ResponseDTO
    @Mapping(target = "cliente", source = "cliente")
    @Mapping(target = "detalles", source = "detalles")
    @Mapping(target = "pago", source = "pago")
    @Mapping(target = "recibo", source = "recibo")
    PedidoResponseDTO toResponseDTO(Pedido entity);

    // Mapear lista de pedidos
    List<PedidoResponseDTO> toResponseDTOList(List<Pedido> entities);

    // Mapear a DTO simplificado para listados
    @Mapping(target = "nombreCliente", expression = "java(entity.getCliente().getNombre() + ' ' + entity.getCliente().getApellido())")
    PedidoListDTO toListDTO(Pedido entity);

    // Mapear lista simplificada
    List<PedidoListDTO> toListDTOList(List<Pedido> entities);
}
