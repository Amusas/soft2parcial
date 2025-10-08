package product.service.parcial2soft.service.interfaces;

import product.service.parcial2soft.dto.pedidos.CancelarPedidoDTO;
import product.service.parcial2soft.dto.pedidos.PedidoCreateDTO;
import product.service.parcial2soft.dto.pedidos.PedidoListDTO;
import product.service.parcial2soft.dto.pedidos.PedidoResponseDTO;
import product.service.parcial2soft.entity.pedido.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface del servicio de Pedidos
 *
 * Principio de Segregación de Interfaces (ISP):
 * - Define solo los métodos necesarios para la gestión de pedidos
 */
public interface PedidoService {

    /**
     * Crea un nuevo pedido y procesa el pago
     *
     * Este método es transaccional y realiza:
     * 1. Validar cliente y producto
     * 2. Verificar stock disponible
     * 3. Crear pedido (estado PENDIENTE)
     * 4. Crear detalle del pedido
     * 5. Procesar pago
     * 6. Actualizar stock
     * 7. Marcar pedido como PAGADO
     * 8. Generar recibo
     *
     * @param dto Datos del pedido a crear
     * @return Pedido creado con todos los detalles
     */
    PedidoResponseDTO crearPedido(PedidoCreateDTO dto);

    /**
     * Obtiene un pedido por su ID con todos los detalles
     *
     * @param id ID del pedido
     * @return Pedido encontrado
     */
    PedidoResponseDTO obtenerPedidoPorId(Long id);

    /**
     * Obtiene un pedido por su número
     *
     * @param numeroPedido Número del pedido
     * @return Pedido encontrado
     */
    PedidoResponseDTO obtenerPedidoPorNumero(String numeroPedido);

    /**
     * Obtiene todos los pedidos
     *
     * @return Lista de todos los pedidos
     */
    List<PedidoListDTO> obtenerTodosPedidos();

    /**
     * Obtiene pedidos por cliente
     *
     * @param clienteId ID del cliente
     * @return Lista de pedidos del cliente
     */
    List<PedidoListDTO> obtenerPedidosPorCliente(Long clienteId);

    /**
     * Obtiene pedidos por estado
     *
     * @param estado Estado del pedido
     * @return Lista de pedidos con ese estado
     */
    List<PedidoListDTO> obtenerPedidosPorEstado(EstadoPedido estado);

    /**
     * Obtiene pedidos en un rango de fechas
     *
     * @param fechaInicio Fecha inicial
     * @param fechaFin Fecha final
     * @return Lista de pedidos en el rango
     */
    List<PedidoListDTO> obtenerPedidosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Obtiene los pedidos del día actual
     *
     * @return Lista de pedidos del día
     */
    List<PedidoListDTO> obtenerPedidosDelDia();

    /**
     * Cancela un pedido
     * Solo se pueden cancelar pedidos en estado PENDIENTE
     *
     * @param id del pedido
     * @param dto Datos de cancelación
     */
    void cancelarPedido(Long id, CancelarPedidoDTO dto);

}