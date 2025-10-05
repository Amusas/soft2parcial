package product.service.parcial2soft.mapper;

import org.mapstruct.*;
import product.service.parcial2soft.dto.ClienteCreateDTO;
import product.service.parcial2soft.dto.ClienteResponseDTO;
import product.service.parcial2soft.dto.ClienteUpdateDTO;
import product.service.parcial2soft.entity.Cliente;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    // Mapear de CreateDTO a Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "activo", constant = "true")
    Cliente toEntity(ClienteCreateDTO dto);

    // Mapear de Entity a ResponseDTO
    ClienteResponseDTO toResponseDTO(Cliente entity);

    // Mapear lista de entities a lista de ResponseDTOs
    List<ClienteResponseDTO> toResponseDTOList(List<Cliente> entities);

    // Actualizar entity existente con UpdateDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ClienteUpdateDTO dto, @MappingTarget Cliente entity);
}
