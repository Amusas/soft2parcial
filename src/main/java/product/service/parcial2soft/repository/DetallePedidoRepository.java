package product.service.parcial2soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import product.service.parcial2soft.entity.pedido.DetallePedido;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    // Buscar detalles por pedido
    List<DetallePedido> findByPedidoId(Long pedidoId);

    // Buscar detalles por producto
    List<DetallePedido> findByGafasId(Long gafasId);

    // Obtener productos más vendidos
    @Query("SELECT d.gafas.id, d.gafas.nombre, SUM(d.cantidad) as total " +
            "FROM DetallePedido d " +
            "GROUP BY d.gafas.id, d.gafas.nombre " +
            "ORDER BY total DESC")
    List<Object[]> findProductosMasVendidos();

}

