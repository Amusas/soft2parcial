package product.service.parcial2soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import product.service.parcial2soft.entity.pedido.Recibo;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long> {

    // Buscar por número de recibo
    Optional<Recibo> findByNumeroRecibo(String numeroRecibo);

    // Verificar si existe por número
    boolean existsByNumeroRecibo(String numeroRecibo);

    // Buscar recibo por pedido
    Optional<Recibo> findByPedidoId(Long pedidoId);

    // Buscar recibos activos
    List<Recibo> findByActivoTrueOrderByFechaEmisionDesc();

    // Buscar recibos anulados
    List<Recibo> findByActivoFalseOrderByFechaAnulacionDesc();

    // Generar siguiente número de recibo
    @Query("SELECT COUNT(r) FROM Recibo r WHERE YEAR(r.fechaEmision) = YEAR(CURRENT_DATE)")
    long contarRecibosDelAnio();
}