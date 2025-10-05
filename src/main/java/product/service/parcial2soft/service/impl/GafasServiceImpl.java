package product.service.parcial2soft.service.impl;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.service.parcial2soft.dto.gafas.GafasCreateDTO;
import product.service.parcial2soft.dto.gafas.GafasFiltroDTO;
import product.service.parcial2soft.dto.gafas.GafasResponseDTO;
import product.service.parcial2soft.dto.gafas.GafasUpdateDTO;
import product.service.parcial2soft.entity.gafas.Gafas;
import product.service.parcial2soft.exceptions.gafas.CodigoDuplicadoException;
import product.service.parcial2soft.exceptions.gafas.ProductoNoDisponibleException;
import product.service.parcial2soft.exceptions.gafas.StockInsuficienteException;
import product.service.parcial2soft.mapper.GafasMapper;
import product.service.parcial2soft.repository.GafasRepository;
import product.service.parcial2soft.service.interfaces.GafasService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GafasServiceImpl implements GafasService {

    private final GafasRepository gafasRepository;
    private final GafasMapper gafasMapper;

    @Override
    public GafasResponseDTO createGafas(GafasCreateDTO dto) {
        log.info("Creando nuevas gafas con código: {}", dto.codigo());

        // Validar que no existan gafas con el mismo código
        if (gafasRepository.existsByCodigo(dto.codigo())) {
            throw new CodigoDuplicadoException(dto.codigo());
        }

        Gafas gafas = gafasMapper.toEntity(dto);
        Gafas savedGafas = gafasRepository.save(gafas);

        log.info("Gafas creadas exitosamente con ID: {}", savedGafas.getId());
        return gafasMapper.toResponseDTO(savedGafas);
    }


    @Override
    @Transactional(readOnly = true)
    public GafasResponseDTO getGafasById(Long id) {
        log.info("Buscando gafas con ID: {}", id);

        Gafas gafas = gafasRepository.findById(id)
                .orElseThrow(() -> new ProductoNoDisponibleException("Gafas no encontradas con ID: " + id));

        return gafasMapper.toResponseDTO(gafas);
    }


    @Override
    @Transactional(readOnly = true)
    public List<GafasResponseDTO> getAllGafas() {
        log.info("Obteniendo todas las gafas");

        List<Gafas> gafasList = gafasRepository.findAll();
        return gafasMapper.toResponseDTOList(gafasList);
    }


    @Override
    @Transactional(readOnly = true)
    public List<GafasResponseDTO> getGafasActivas() {
        log.info("Obteniendo gafas activas");

        List<Gafas> gafasList = gafasRepository.findByActivoTrue();
        return gafasMapper.toResponseDTOList(gafasList);
    }


    @Override
    @Transactional(readOnly = true)
    public List<GafasResponseDTO> getGafasDisponibles() {
        log.info("Obteniendo gafas disponibles");

        List<Gafas> gafasList = gafasRepository.findDisponibles();
        return gafasMapper.toResponseDTOList(gafasList);
    }


    @Override
    @Transactional(readOnly = true)
    public GafasResponseDTO getGafasByCodigo(String codigo) {
        log.info("Buscando gafas con código: {}", codigo);

        Gafas gafas = gafasRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNoDisponibleException("Gafas no encontradas con código: " + codigo));

        return gafasMapper.toResponseDTO(gafas);
    }


    @Override
    public GafasResponseDTO updateGafas(Long id, GafasUpdateDTO dto) {
        log.info("Actualizando gafas con ID: {}", id);

        Gafas gafas = gafasRepository.findById(id)
                .orElseThrow(() -> new ProductoNoDisponibleException("Gafas no encontradas con ID: " + id));

        // Actualizar los campos usando MapStruct
        gafasMapper.updateEntityFromDTO(dto, gafas);

        Gafas updatedGafas = gafasRepository.save(gafas);

        log.info("Gafas actualizadas exitosamente con ID: {}", updatedGafas.getId());
        return gafasMapper.toResponseDTO(updatedGafas);
    }


    @Override
    public void deleteGafas(Long id) {
        log.info("Desactivando gafas con ID: {}", id);

        Gafas gafas = gafasRepository.findById(id)
                .orElseThrow(() -> new ProductoNoDisponibleException("Gafas no encontradas con ID: " + id));

        gafas.setActivo(false);
        gafasRepository.save(gafas);

        log.info("Gafas desactivadas exitosamente con ID: {}", id);
    }


    @Override
    public void deleteGafasPermanente(Long id) {
        log.info("Eliminando permanentemente gafas con ID: {}", id);

        if (!gafasRepository.existsById(id)) {
            throw new ProductoNoDisponibleException("Gafas no encontradas con ID: " + id);
        }

        gafasRepository.deleteById(id);

        log.info("Gafas eliminadas permanentemente con ID: {}", id);
    }


    @Override
    public GafasResponseDTO incrementarStock(Long id, Integer cantidad) {
        log.info("Incrementando stock de gafas con ID: {} en {} unidades", id, cantidad);

        if (cantidad <= 0) {
            throw new ValidationException("La cantidad debe ser mayor a 0");
        }

        Gafas gafas = gafasRepository.findById(id)
                .orElseThrow(() -> new ProductoNoDisponibleException("Gafas no encontradas con ID: " + id));

        gafas.setStock(gafas.getStock() + cantidad);
        Gafas updatedGafas = gafasRepository.save(gafas);

        log.info("Stock incrementado. Nuevo stock: {}", updatedGafas.getStock());
        return gafasMapper.toResponseDTO(updatedGafas);
    }


    @Override
    public GafasResponseDTO decrementarStock(Long id, Integer cantidad) {
        log.info("Decrementando stock de gafas con ID: {} en {} unidades", id, cantidad);

        if (cantidad <= 0) {
            throw new ValidationException("La cantidad debe ser mayor a 0");
        }

        Gafas gafas = gafasRepository.findById(id)
                .orElseThrow(() -> new ProductoNoDisponibleException("Gafas no encontradas con ID: " + id));

        // Validar que el producto esté activo
        if (!gafas.getActivo()) {
            throw new ProductoNoDisponibleException(gafas.getCodigo());
        }

        // Validar que haya stock suficiente
        if (gafas.getStock() < cantidad) {
            throw new StockInsuficienteException(gafas.getCodigo(), gafas.getStock(), cantidad);
        }

        gafas.setStock(gafas.getStock() - cantidad);
        Gafas updatedGafas = gafasRepository.save(gafas);

        log.info("Stock decrementado. Nuevo stock: {}", updatedGafas.getStock());
        return gafasMapper.toResponseDTO(updatedGafas);
    }


    @Override
    @Transactional(readOnly = true)
    public List<GafasResponseDTO> buscarConFiltros(GafasFiltroDTO filtro) {
        log.info("Buscando gafas con filtros: {}", filtro);

        List<Gafas> gafasList = gafasRepository.findByFiltros(
                filtro.tipo(),
                filtro.marca(),
                filtro.material(),
                filtro.precioMin(),
                filtro.precioMax(),
                filtro.disponible()
        );

        return gafasMapper.toResponseDTOList(gafasList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GafasResponseDTO> getProductosSinStock() {
        log.info("Obteniendo productos sin stock");

        List<Gafas> gafasList = gafasRepository.findProductosSinStock();
        return gafasMapper.toResponseDTOList(gafasList);
    }

}