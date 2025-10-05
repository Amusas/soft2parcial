package product.service.parcial2soft.service.interfaces;

import product.service.parcial2soft.dto.gafas.GafasCreateDTO;
import product.service.parcial2soft.dto.gafas.GafasFiltroDTO;
import product.service.parcial2soft.dto.gafas.GafasResponseDTO;
import product.service.parcial2soft.dto.gafas.GafasUpdateDTO;

import java.util.List;

public interface GafasService {

    /**
     * Crear nuevas gafas
     * @param dto Datos de las gafas a crear
     * @return Gafas creadas
     */
    GafasResponseDTO createGafas(GafasCreateDTO dto);

    /**
     * Obtener gafas por su ID
     * @param id ID de las gafas
     * @return Gafas encontradas
     */
    GafasResponseDTO getGafasById(Long id);

    /**
     * Obtener todas las gafas
     * @return Lista de todas las gafas
     */
    List<GafasResponseDTO> getAllGafas();

    /**
     * Obtener solo gafas activas
     * @return Lista de gafas activas
     */
    List<GafasResponseDTO> getGafasActivas();

    /**
     * Obtener gafas disponibles (activas y con stock)
     * @return Lista de gafas disponibles
     */
    List<GafasResponseDTO> getGafasDisponibles();

    /**
     * Buscar gafas por código
     * @param codigo Código de las gafas
     * @return Gafas encontradas
     */
    GafasResponseDTO getGafasByCodigo(String codigo);

    /**
     * Actualizar gafas existentes
     * @param id ID de las gafas a actualizar
     * @param dto Datos actualizados
     * @return Gafas actualizadas
     */
    GafasResponseDTO updateGafas(Long id, GafasUpdateDTO dto);

    /**
     * Eliminar gafas (borrado lógico)
     * @param id ID de las gafas a eliminar
     */
    void deleteGafas(Long id);

    /**
     * Eliminar permanentemente gafas (borrado físico)
     * @param id ID de las gafas a eliminar
     */
    void deleteGafasPermanente(Long id);

    /**
     * Incrementar stock de gafas
     * @param id ID de las gafas
     * @param cantidad Cantidad a incrementar
     * @return Gafas actualizadas
     */
    GafasResponseDTO incrementarStock(Long id, Integer cantidad);

    /**
     * Decrementar stock de gafas
     * @param id ID de las gafas
     * @param cantidad Cantidad a decrementar
     * @return Gafas actualizadas
     */
    GafasResponseDTO decrementarStock(Long id, Integer cantidad);

    /**
     * Buscar gafas con filtros
     * @param filtro Filtros de búsqueda
     * @return Lista de gafas que cumplen los filtros
     */
    List<GafasResponseDTO> buscarConFiltros(GafasFiltroDTO filtro);


    /**
     * Obtener productos sin stock
     * @return Lista de gafas sin stock
     */
    List<GafasResponseDTO> getProductosSinStock();

}