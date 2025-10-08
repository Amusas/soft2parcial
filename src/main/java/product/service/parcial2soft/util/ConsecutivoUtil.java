package product.service.parcial2soft.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import product.service.parcial2soft.repository.PedidoRepository;
import product.service.parcial2soft.repository.ReciboRepository;

import java.time.LocalDate;

/**
 * Servicio responsable de generar números consecutivos
 * para pedidos y recibos.
 *
 * Principio de Responsabilidad Única (SRP):
 * - Solo maneja la generación de consecutivos
 */
@Component
@RequiredArgsConstructor
public class ConsecutivoUtil {

    private final PedidoRepository pedidoRepository;
    private final ReciboRepository reciboRepository;

    /**
     * Genera el siguiente número de pedido
     * Formato: PED-YYYY-NNNN
     * Ejemplo: PED-2025-0001
     */
    @Transactional(readOnly = true)
    public String generarNumeroPedido() {
        int year = LocalDate.now().getYear();
        long count = pedidoRepository.contarPedidosDelAnio() + 1;
        return String.format("PED-%d-%04d", year, count);
    }

    /**
     * Genera el siguiente número de recibo
     * Formato: REC-YYYY-NNNN
     * Ejemplo: REC-2025-0001
     */
    @Transactional(readOnly = true)
    public String generarNumeroRecibo() {
        int year = LocalDate.now().getYear();
        long count = reciboRepository.contarRecibosDelAnio() + 1;
        return String.format("REC-%d-%04d", year, count);
    }

    /**
     * Valida si un número de pedido ya existe
     */
    @Transactional(readOnly = true)
    public boolean existeNumeroPedido(String numeroPedido) {
        return pedidoRepository.existsByNumeroPedido(numeroPedido);
    }

    /**
     * Valida si un número de recibo ya existe
     */
    @Transactional(readOnly = true)
    public boolean existeNumeroRecibo(String numeroRecibo) {
        return reciboRepository.existsByNumeroRecibo(numeroRecibo);
    }
}