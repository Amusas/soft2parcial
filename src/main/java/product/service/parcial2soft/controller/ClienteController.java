package product.service.parcial2soft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.service.parcial2soft.dto.cliente.ClienteCreateDTO;
import product.service.parcial2soft.dto.cliente.ClienteResponseDTO;
import product.service.parcial2soft.dto.cliente.ClienteUpdateDTO;
import product.service.parcial2soft.service.interfaces.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Crear un nuevo cliente
     * POST /api/clientes
     */
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteCreateDTO dto) {
        ClienteResponseDTO response = clienteService.createCliente(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtener un cliente por ID
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.getClienteById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todos los clientes
     * GET /api/clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
        List<ClienteResponseDTO> response = clienteService.getAllClientes();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener solo clientes activos
     * GET /api/clientes/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<ClienteResponseDTO>> getClientesActivos() {
        List<ClienteResponseDTO> response = clienteService.getClientesActivos();
        return ResponseEntity.ok(response);
    }

    /**
     * Buscar cliente por documento
     * GET /api/clientes/documento/{documento}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> getClienteByEmail(@PathVariable String email) {
        ClienteResponseDTO response = clienteService.getClienteByEmail(email);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar un cliente
     * PUT /api/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteUpdateDTO dto) {
        ClienteResponseDTO response = clienteService.updateCliente(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Desactivar un cliente (borrado lógico)
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar permanentemente un cliente (borrado físico)
     * DELETE /api/clientes/{id}/permanente
     */
    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> deleteClientePermanente(@PathVariable Long id) {
        clienteService.deleteClientePermanente(id);
        return ResponseEntity.noContent().build();
    }
}