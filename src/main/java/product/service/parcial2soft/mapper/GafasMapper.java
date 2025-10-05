package product.service.parcial2soft.mapper;

import org.mapstruct.*;
import product.service.parcial2soft.dto.gafas.GafasCreateDTO;
import product.service.parcial2soft.dto.gafas.GafasResponseDTO;
import product.service.parcial2soft.dto.gafas.GafasUpdateDTO;
import product.service.parcial2soft.entity.gafas.Gafas;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GafasMapper {

    // Mapear de CreateDTO a Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "stock", source = "stock", defaultValue = "0")
    @Mapping(target = "tieneProteccionUV", source = "tieneProteccionUV", defaultValue = "false")
    @Mapping(target = "esPolarizada", source = "esPolarizada", defaultValue = "false")
    @Mapping(target = "graduable", source = "graduable", defaultValue = "false")
    Gafas toEntity(GafasCreateDTO dto);

    // Mapear de Entity a ResponseDTO
    @Mapping(target = "disponible", expression = "java(entity.tieneStock())")
    GafasResponseDTO toResponseDTO(Gafas entity);

    // Mapear lista de entities a lista de ResponseDTOs
    List<GafasResponseDTO> toResponseDTOList(List<Gafas> entities);

    // Actualizar entity existente con UpdateDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(GafasUpdateDTO dto, @MappingTarget Gafas entity);
}
