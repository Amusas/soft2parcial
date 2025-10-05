package product.service.parcial2soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import product.service.parcial2soft.entity.gafas.Gafas;
import product.service.parcial2soft.entity.gafas.MaterialMontura;
import product.service.parcial2soft.entity.gafas.TipoGafas;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GafasRepository extends JpaRepository<Gafas, Long> {

    // Verificar si existe gafas con el código dado
    boolean existsByCodigo(String codigo);

    // Verificar si existe gafas con el código dado, excluyendo un ID específico
    boolean existsByCodigoAndIdNot(String codigo, Long id);

    // Buscar gafas por código
    Optional<Gafas> findByCodigo(String codigo);

    // Buscar gafas activas
    List<Gafas> findByActivoTrue();

    // Buscar gafas disponibles (activas y con stock > 0)
    @Query("SELECT g FROM Gafas g WHERE g.activo = true AND g.stock > 0")
    List<Gafas> findDisponibles();

    // Buscar por tipo de gafas
    List<Gafas> findByTipoAndActivoTrue(TipoGafas tipo);

    // Buscar por marca
    List<Gafas> findByMarcaContainingIgnoreCaseAndActivoTrue(String marca);

    // Buscar por material
    List<Gafas> findByMaterialAndActivoTrue(MaterialMontura material);

    // Buscar por rango de precio
    @Query("SELECT g FROM Gafas g WHERE g.precio BETWEEN :precioMin AND :precioMax AND g.activo = true")
    List<Gafas> findByRangoPrecio(@Param("precioMin") BigDecimal precioMin,
                                  @Param("precioMax") BigDecimal precioMax);

    // Buscar productos sin stock
    @Query("SELECT g FROM Gafas g WHERE g.stock = 0 AND g.activo = true")
    List<Gafas> findProductosSinStock();

    // Buscar por múltiples filtros
    @Query("SELECT g FROM Gafas g WHERE " +
            "(:tipo IS NULL OR g.tipo = :tipo) AND " +
            "(:marca IS NULL OR LOWER(g.marca) LIKE LOWER(CONCAT('%', :marca, '%'))) AND " +
            "(:material IS NULL OR g.material = :material) AND " +
            "(:precioMin IS NULL OR g.precio >= :precioMin) AND " +
            "(:precioMax IS NULL OR g.precio <= :precioMax) AND " +
            "(:disponible IS NULL OR (:disponible = true AND g.stock > 0) OR (:disponible = false)) AND " +
            "g.activo = true")
    List<Gafas> findByFiltros(@Param("tipo") TipoGafas tipo,
                              @Param("marca") String marca,
                              @Param("material") MaterialMontura material,
                              @Param("precioMin") BigDecimal precioMin,
                              @Param("precioMax") BigDecimal precioMax,
                              @Param("disponible") Boolean disponible);

    // Buscar gafas graduables
    List<Gafas> findByGraduableTrueAndActivoTrue();

    // Buscar gafas con protección UV
    List<Gafas> findByTieneProteccionUVTrueAndActivoTrue();

    // Buscar gafas polarizadas
    List<Gafas> findByEsPolarizadaTrueAndActivoTrue();
}