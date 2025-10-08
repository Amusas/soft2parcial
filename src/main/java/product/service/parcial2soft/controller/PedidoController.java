package product.service.parcial2soft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.service.parcial2soft.dto.pedidos.CancelarPedidoDTO;
import product.service.parcial2soft.dto.pedidos.PedidoCreateDTO;
import product.service.parcial2soft.dto.pedidos.PedidoListDTO;
import product.service.parcial2soft.dto.pedidos.PedidoResponseDTO;
import product.service.parcial2soft.entity.pedido.EstadoPedido;
import product.service.parcial2soft.service.interfaces.PedidoService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para el módulo de Pedidos
 *
 * Expone endpoints para:
 * - Crear pedidos
 * - Consultar pedidos
 * - Cancelar pedidos
 * - Obtener estadísticas
 */
@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    /**
     * Crear un nuevo pedido
     * POST /api/pedidos
     *
     * Este endpoint:
     * 1. Valida el cliente y producto
     * 2. Verifica stock disponible
     * 3. Procesa el pago
     * 4. Genera el pedido y recibo
     *
     * @param dto Datos del pedido
     * @return Pedido creado con código 201
     */
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoCreateDTO dto) {
        PedidoResponseDTO response = pedidoService.crearPedido(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtener un pedido por ID
     * GET /api/pedidos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener un pedido por número
     * GET /api/pedidos/numero/{numeroPedido}
     */
    @GetMapping("/numero/{numeroPedido}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedidoPorNumero(@PathVariable String numeroPedido) {
        PedidoResponseDTO response = pedidoService.obtenerPedidoPorNumero(numeroPedido);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todos los pedidos
     * GET /api/pedidos
     */
    @GetMapping
    public ResponseEntity<List<PedidoListDTO>> obtenerTodosPedidos() {
        List<PedidoListDTO> response = pedidoService.obtenerTodosPedidos();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pedidos por cliente
     * GET /api/pedidos/cliente/{clienteId}
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoListDTO>> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        List<PedidoListDTO> response = pedidoService.obtenerPedidosPorCliente(clienteId);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pedidos por estado
     * GET /api/pedidos/estado/{estado}
     *
     * Estados disponibles: PENDIENTE, PAGADO, CANCELADO, ANULADO
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoListDTO>> obtenerPedidosPorEstado(@PathVariable EstadoPedido estado) {
        List<PedidoListDTO> response = pedidoService.obtenerPedidosPorEstado(estado);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pedidos por rango de fechas
     * GET /api/pedidos/fechas?inicio=2025-01-01T00:00:00&fin=2025-12-31T23:59:59
     */
    @GetMapping("/fechas")
    public ResponseEntity<List<PedidoListDTO>> obtenerPedidosPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<PedidoListDTO> response = pedidoService.obtenerPedidosPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pedidos del día actual
     * GET /api/pedidos/hoy
     */
    @GetMapping("/hoy")
    public ResponseEntity<List<PedidoListDTO>> obtenerPedidosDelDia() {
        List<PedidoListDTO> response = pedidoService.obtenerPedidosDelDia();
        return ResponseEntity.ok(response);
    }

    /**
     * Cancelar un pedido
     * DELETE /api/pedidos/{id}/cancelar
     *
     * Solo se pueden cancelar pedidos en estado PENDIENTE
     */
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(
            @PathVariable Long id,
            @Valid @RequestBody CancelarPedidoDTO dto) {
        pedidoService.cancelarPedido(id, dto);
        return ResponseEntity.noContent().build();
    }

}