package product.service.parcial2soft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.service.parcial2soft.dto.pedidos.*;
import product.service.parcial2soft.entity.Cliente;
import product.service.parcial2soft.entity.gafas.Gafas;
import product.service.parcial2soft.entity.pedido.*;
import product.service.parcial2soft.exceptions.cliente.ClienteNotFoundException;
import product.service.parcial2soft.exceptions.gafas.ProductoNoDisponibleException;
import product.service.parcial2soft.exceptions.gafas.StockInsuficienteException;
import product.service.parcial2soft.exceptions.pedido.*;
import product.service.parcial2soft.mapper.PagoMapper;
import product.service.parcial2soft.mapper.PedidoMapper;
import product.service.parcial2soft.mapper.ReciboMapper;
import product.service.parcial2soft.repository.ClienteRepository;
import product.service.parcial2soft.repository.GafasRepository;
import product.service.parcial2soft.repository.PedidoRepository;
import product.service.parcial2soft.repository.ReciboRepository;
import product.service.parcial2soft.service.interfaces.GafasService;
import product.service.parcial2soft.service.interfaces.PagoProcessorService;
import product.service.parcial2soft.service.interfaces.PedidoService;
import product.service.parcial2soft.util.ConsecutivoUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de Pedidos
 *
 * Principios SOLID aplicados:
 * - SRP: Delega responsabilidades en servicios especializados
 * - OCP: Extensible mediante inyección de dependencias
 * - LSP: Implementa la interface PedidoService correctamente
 * - ISP: Usa interfaces segregadas
 * - DIP: Depende de abstracciones (interfaces)
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoServiceImpl implements PedidoService {

    // Repositories
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final GafasRepository gafasRepository;

    // Servicios especializados (SRP + DIP)
    private final GafasService gafasService;
    private final ConsecutivoUtil consecutivoService;
    private final PagoProcessorService pagoProcessorService;

    // Mappers
    private final PedidoMapper pedidoMapper;
    private final PagoMapper pagoMapper;
    private final ReciboRepository reciboRepository;

    @Override
    public PedidoResponseDTO crearPedido(PedidoCreateDTO dto) {
        log.info("Iniciando creación de pedido para cliente ID: {} con {} items",
                dto.clienteId(), dto.items().size());

        try {
            // 1. Validar items únicos en el carrito
            dto.validarItemsUnicos();

            // 2. Validar y obtener cliente
            Cliente cliente = validarYObtenerCliente(dto.clienteId());

            // 3. Validar datos del pago
            dto.datosPago().validarParaMetodo(dto.metodoPago());

            // 4. Validar disponibilidad de todos los productos
            validarDisponibilidadCarrito(dto.items());

            // 5. Crear el pedido (estado PENDIENTE)
            Pedido pedido = crearPedidoEntity(dto, cliente);

            // 6. Crear detalles del pedido para cada item del carrito
            for (ItemCarritoDTO item : dto.items()) {
                Gafas gafas = validarYObtenerGafas(item.gafasId());
                DetallePedido detalle = crearDetallePedido(pedido, gafas, item.cantidad());
                pedido.addDetalle(detalle);
            }

            // 7. Calcular totales del pedido completo
            pedido.calcularTotales();

            // 8. Crear el pago
            Pago pago = crearPagoEntity(dto, pedido);
            pedido.setPago(pago);

            // 9. Guardar pedido (antes de procesar pago)
            pedido = pedidoRepository.save(pedido);
            log.info("Pedido guardado con número: {}", pedido.getNumeroPedido());

            // 10. Procesar el pago (puede lanzar excepciones)
            pago = pagoProcessorService.procesarPago(pago);

            // 11. Si el pago fue exitoso, actualizar stock y estado
            if (pago.esPagoExitoso()) {
                // Decrementar stock de todos los productos
                for (DetallePedido detalle : pedido.getDetalles()) {
                    gafasService.decrementarStock(detalle.getGafas().getId(), detalle.getCantidad());
                    log.debug("Stock decrementado para producto: {} | Cantidad: {}",
                            detalle.getGafas().getCodigo(), detalle.getCantidad());
                }

                pedido.marcarComoPagado();

                // 12. Generar recibo único para todo el pedido
                Recibo recibo = generarRecibo(pedido);
                reciboRepository.save(recibo);
                pedido.setRecibo(recibo);

                pedido = pedidoRepository.save(pedido);
                log.info("Pedido completado exitosamente: {} | Items: {} | Total: {}",
                        pedido.getNumeroPedido(),
                        pedido.getDetalles().size(),
                        pedido.getTotal());
            }

            // 13. Recargar el pedido con todas las relaciones para el mapeo
            pedido = pedidoRepository.findByIdWithDetails(pedido.getId())
                    .orElse(pedido);

            return pedidoMapper.toResponseDTO(pedido);

        } catch (PagoRechazadoException | PagoFallidoException e) {
            log.error("Error procesando pago para cliente {}: {}", dto.clienteId(), e.getMessage());
            throw e; // Re-lanzar excepciones de pago
        } catch (Exception e) {
            log.error("Error inesperado creando pedido", e);
            throw new PedidoNoProcesableException("Error procesando el pedido: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPedidoPorId(Long id) {
        log.info("Buscando pedido con ID: {}", id);

        Pedido pedido = pedidoRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new PedidoNoEncontradoException("Pedido no encontrado con ID: " + id));

        return pedidoMapper.toResponseDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPedidoPorNumero(String numeroPedido) {
        log.info("Buscando pedido con número: {}", numeroPedido);

        Pedido pedido = pedidoRepository.findByNumeroPedido(numeroPedido)
                .orElseThrow(() -> new PedidoNoEncontradoException("Pedido no encontrado: " + numeroPedido));

        return pedidoMapper.toResponseDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoListDTO> obtenerTodosPedidos() {
        log.info("Obteniendo todos los pedidos");
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toListDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoListDTO> obtenerPedidosPorCliente(Long clienteId) {
        log.info("Obteniendo pedidos del cliente ID: {}", clienteId);

        if (!clienteRepository.existsById(clienteId)) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId);
        }

        List<Pedido> pedidos = pedidoRepository.findByClienteIdOrderByFechaCreacionDesc(clienteId);
        return pedidoMapper.toListDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoListDTO> obtenerPedidosPorEstado(EstadoPedido estado) {
        log.info("Obteniendo pedidos con estado: {}", estado);
        List<Pedido> pedidos = pedidoRepository.findByEstadoOrderByFechaCreacionDesc(estado);
        return pedidoMapper.toListDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoListDTO> obtenerPedidosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.info("Obteniendo pedidos entre {} y {}", fechaInicio, fechaFin);
        List<Pedido> pedidos = pedidoRepository.findByRangoFechas(fechaInicio, fechaFin);
        return pedidoMapper.toListDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoListDTO> obtenerPedidosDelDia() {
        log.info("Obteniendo pedidos del día");
        List<Pedido> pedidos = pedidoRepository.findPedidosDelDia();
        return pedidoMapper.toListDTOList(pedidos);
    }

    @Override
    public void cancelarPedido(Long id, CancelarPedidoDTO dto) {
        log.info("Cancelando pedido ID: {}", id);

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException("Pedido no encontrado con ID: " + id));

        if (!pedido.puedeCancelarse()) {
            throw new PedidoNoCancelableException(pedido.getNumeroPedido(), pedido.getEstado().name());
        }

        // Si el pedido tenía stock reservado, devolverlo
        for (DetallePedido detalle : pedido.getDetalles()) {
            gafasService.incrementarStock(detalle.getGafas().getId(), detalle.getCantidad());
        }

        pedido.cancelar();
        pedidoRepository.save(pedido);

        log.info("Pedido cancelado: {}. Motivo: {}", pedido.getNumeroPedido(), dto.motivo());
    }


    // ========== MÉTODOS PRIVADOS DE AYUDA ==========

    /**
     * Valida la disponibilidad de todos los productos en el carrito
     */
    private void validarDisponibilidadCarrito(List<ItemCarritoDTO> items) {
        log.debug("Validando disponibilidad de {} items en el carrito", items.size());

        for (ItemCarritoDTO item : items) {
            Gafas gafas = validarYObtenerGafas(item.gafasId());
            validarDisponibilidadProducto(gafas, item.cantidad());
        }

        log.debug("Todos los items del carrito están disponibles");
    }

    private Cliente validarYObtenerCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));

        if (Boolean.FALSE.equals(cliente.getActivo())) {
            throw new ClienteNotFoundException("El cliente está inactivo");
        }

        return cliente;
    }

    private Gafas validarYObtenerGafas(Long gafasId) {
        Gafas gafas = gafasRepository.findById(gafasId)
                .orElseThrow(() -> new ProductoNoDisponibleException("Gafas no encontradas con ID: " + gafasId));

        if (Boolean.FALSE.equals(gafas.getActivo())) {
            throw new ProductoNoDisponibleException(gafas.getCodigo());
        }

        return gafas;
    }

    private void validarDisponibilidadProducto(Gafas gafas, Integer cantidadSolicitada) {
        if (!gafas.tieneStock()) {
            throw new StockInsuficienteException(gafas.getCodigo(), 0, cantidadSolicitada);
        }

        if (gafas.getStock() < cantidadSolicitada) {
            throw new StockInsuficienteException(gafas.getCodigo(), gafas.getStock(), cantidadSolicitada);
        }
    }

    private Pedido crearPedidoEntity(PedidoCreateDTO dto, Cliente cliente) {
        String numeroPedido = consecutivoService.generarNumeroPedido();

        return Pedido.builder()
                .numeroPedido(numeroPedido)
                .cliente(cliente)
                .estado(EstadoPedido.PENDIENTE)
                .metodoPago(dto.metodoPago())
                .observaciones(dto.observaciones())
                .subtotal(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .build();
    }

    private DetallePedido crearDetallePedido(Pedido pedido, Gafas gafas, Integer cantidad) {
        DetallePedido detalle = DetallePedido.builder()
                .pedido(pedido)
                .gafas(gafas)
                .cantidad(cantidad)
                .precioUnitario(gafas.getPrecio())
                .build();

        detalle.calcularValores();
        return detalle;
    }

    private Pago crearPagoEntity(PedidoCreateDTO dto, Pedido pedido) {
        Pago pago = pagoMapper.toEntity(dto.datosPago(), dto.metodoPago(), pedido.getTotal());
        pago.setPedido(pedido);

        // Enmascarar número de tarjeta si existe
        if (pago.getNumeroTarjeta() != null) {
            pago.setNumeroTarjeta(Pago.enmascararTarjeta(pago.getNumeroTarjeta()));
        }

        // Calcular valor de cuota si hay cuotas
        if (pago.getCuotas() != null && pago.getCuotas() > 1) {
            BigDecimal valorCuota = pedido.getTotal()
                    .divide(BigDecimal.valueOf(pago.getCuotas()), 2, RoundingMode.HALF_UP);
            pago.setValorCuota(valorCuota);
        }

        return pago;
    }

    private Recibo generarRecibo(Pedido pedido) {
        String numeroRecibo = consecutivoService.generarNumeroRecibo();

        return Recibo.builder()
                .numeroRecibo(numeroRecibo)
                .pedido(pedido)
                .total(pedido.getTotal())
                .activo(true)
                .build();
    }
}