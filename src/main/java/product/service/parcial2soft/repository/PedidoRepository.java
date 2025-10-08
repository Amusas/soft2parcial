package product.service.parcial2soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import product.service.parcial2soft.entity.pedido.EstadoPedido;
import product.service.parcial2soft.entity.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar por número de pedido
    Optional<Pedido> findByNumeroPedido(String numeroPedido);

    // Verificar si existe por número de pedido
    boolean existsByNumeroPedido(String numeroPedido);

    // Buscar pedidos por cliente
    List<Pedido> findByClienteIdOrderByFechaCreacionDesc(Long clienteId);

    // Buscar pedidos por estado
    List<Pedido> findByEstadoOrderByFechaCreacionDesc(EstadoPedido estado);

    // Buscar pedidos por cliente y estado
    List<Pedido> findByClienteIdAndEstadoOrderByFechaCreacionDesc(Long clienteId, EstadoPedido estado);

    // Buscar pedidos por rango de fechas
    @Query("SELECT p FROM Pedido p WHERE p.fechaCreacion BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fechaCreacion DESC")
    List<Pedido> findByRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio,
                                   @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar pedidos pendientes
    List<Pedido> findByEstadoOrderByFechaCreacionAsc(EstadoPedido estado);

    // Contar pedidos por cliente
    long countByClienteId(Long clienteId);

    // Contar pedidos por estado
    long countByEstado(EstadoPedido estado);

    // Obtener últimos N pedidos
    List<Pedido> findTop10ByOrderByFechaCreacionDesc();

    // Buscar pedidos con eager loading de detalles
    @Query("SELECT DISTINCT p FROM Pedido p " +
            "LEFT JOIN FETCH p.detalles d " +
            "LEFT JOIN FETCH d.gafas " +
            "LEFT JOIN FETCH p.cliente " +
            "LEFT JOIN FETCH p.pago " +
            "LEFT JOIN FETCH p.recibo " +
            "WHERE p.id = :id")
    Optional<Pedido> findByIdWithDetails(@Param("id") Long id);

    // Buscar pedidos del día
    @Query("SELECT p FROM Pedido p WHERE DATE(p.fechaCreacion) = CURRENT_DATE ORDER BY p.fechaCreacion DESC")
    List<Pedido> findPedidosDelDia();

    // Generar siguiente número de pedido
    @Query("SELECT COUNT(p) FROM Pedido p WHERE YEAR(p.fechaCreacion) = YEAR(CURRENT_DATE)")
    long contarPedidosDelAnio();
}