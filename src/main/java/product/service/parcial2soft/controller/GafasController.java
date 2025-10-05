package product.service.parcial2soft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.service.parcial2soft.dto.gafas.*;
import product.service.parcial2soft.service.interfaces.GafasService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gafas")
@RequiredArgsConstructor
public class GafasController {

    private final GafasService gafasService;

    /**
     * Crear nuevas gafas
     * POST /api/gafas
     */
    @PostMapping
    public ResponseEntity<GafasResponseDTO> createGafas(@Valid @RequestBody GafasCreateDTO dto) {
        GafasResponseDTO response = gafasService.createGafas(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtener gafas por ID
     * GET /api/gafas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<GafasResponseDTO> getGafasById(@PathVariable Long id) {
        GafasResponseDTO response = gafasService.getGafasById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todas las gafas
     * GET /api/gafas
     */
    @GetMapping
    public ResponseEntity<List<GafasResponseDTO>> getAllGafas() {
        List<GafasResponseDTO> response = gafasService.getAllGafas();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener solo gafas activas
     * GET /api/gafas/activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<GafasResponseDTO>> getGafasActivas() {
        List<GafasResponseDTO> response = gafasService.getGafasActivas();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener gafas disponibles (activas y con stock)
     * GET /api/gafas/disponibles
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<GafasResponseDTO>> getGafasDisponibles() {
        List<GafasResponseDTO> response = gafasService.getGafasDisponibles();
        return ResponseEntity.ok(response);
    }

    /**
     * Buscar gafas por código
     * GET /api/gafas/codigo/{codigo}
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<GafasResponseDTO> getGafasByCodigo(@PathVariable String codigo) {
        GafasResponseDTO response = gafasService.getGafasByCodigo(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar gafas
     * PUT /api/gafas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<GafasResponseDTO> updateGafas(
            @PathVariable Long id,
            @Valid @RequestBody GafasUpdateDTO dto) {
        GafasResponseDTO response = gafasService.updateGafas(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Desactivar gafas (borrado lógico)
     * DELETE /api/gafas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGafas(@PathVariable Long id) {
        gafasService.deleteGafas(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar permanentemente gafas (borrado físico)
     * DELETE /api/gafas/{id}/permanente
     */
    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> deleteGafasPermanente(@PathVariable Long id) {
        gafasService.deleteGafasPermanente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Incrementar stock
     * PATCH /api/gafas/{id}/incrementar-stock
     */
    @PatchMapping("/{id}/incrementar-stock")
    public ResponseEntity<GafasResponseDTO> incrementarStock(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDTO dto) {
        GafasResponseDTO response = gafasService.incrementarStock(id, dto.cantidad());
        return ResponseEntity.ok(response);
    }

    /**
     * Decrementar stock
     * PATCH /api/gafas/{id}/decrementar-stock
     */
    @PatchMapping("/{id}/decrementar-stock")
    public ResponseEntity<GafasResponseDTO> decrementarStock(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDTO dto) {
        GafasResponseDTO response = gafasService.decrementarStock(id, dto.cantidad());
        return ResponseEntity.ok(response);
    }

    /**
     * Buscar con filtros
     * POST /api/gafas/buscar
     */
    @PostMapping("/buscar")
    public ResponseEntity<List<GafasResponseDTO>> buscarConFiltros(
            @RequestBody GafasFiltroDTO filtro) {
        List<GafasResponseDTO> response = gafasService.buscarConFiltros(filtro);
        return ResponseEntity.ok(response);
    }


    /**
     * Obtener productos sin stock
     * GET /api/gafas/sin-stock
     */
    @GetMapping("/sin-stock")
    public ResponseEntity<List<GafasResponseDTO>> getProductosSinStock() {
        List<GafasResponseDTO> response = gafasService.getProductosSinStock();
        return ResponseEntity.ok(response);
    }

}