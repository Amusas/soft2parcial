package product.service.parcial2soft.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.service.parcial2soft.entity.pedido.MetodoPago;
import product.service.parcial2soft.entity.pedido.Pago;
import product.service.parcial2soft.exceptions.pedido.PagoFallidoException;
import product.service.parcial2soft.exceptions.pedido.PagoRechazadoException;
import product.service.parcial2soft.service.interfaces.PagoProcessorService;

import java.util.Random;
import java.util.UUID;

/**
 * Servicio responsable de procesar pagos digitales.
 *
 * Principio de Responsabilidad Única (SRP):
 * - Solo maneja la simulación del procesamiento de pagos
 *
 * NOTA: Esta es una SIMULACIÓN. En producción, aquí se
 * integraría con pasarelas de pago reales como:
 * - PayU, Mercado Pago, Stripe, etc.
 */
@Service
@Slf4j
public class PagoProcessorServiceImpl implements PagoProcessorService {

    private final Random random = new Random();

    /**
     * Procesa un pago de forma simulada
     *
     * @param pago Entidad de pago a procesar
     * @return Pago con estado actualizado
     * @throws PagoRechazadoException si el pago es rechazado
     * @throws PagoFallidoException si hay error en el procesamiento
     */
    @Override
    public Pago procesarPago(Pago pago) {
        log.info("Iniciando procesamiento de pago con método: {}", pago.getMetodoPago());

        try {
            // Marcar como procesando
            pago.marcarComoProcesando();

            // Simular validaciones específicas por método
            validarDatosPago(pago);

            // Simular tiempo de procesamiento
            simularProcesamiento();

            // Simular respuesta de la pasarela (90% éxito, 10% rechazo)
            boolean pagoExitoso = random.nextInt(100) < 90;

            if (pagoExitoso) {
                // Generar código de autorización simulado
                String codigoAutorizacion = generarCodigoAutorizacion();
                pago.marcarComoExitoso(codigoAutorizacion);
                log.info("Pago procesado exitosamente. Código de autorización: {}", codigoAutorizacion);
            } else {
                // Simular rechazo
                String mensajeRechazo = simularMensajeRechazo();
                pago.marcarComoRechazado(mensajeRechazo);
                log.warn("Pago rechazado: {}", mensajeRechazo);
                throw new PagoRechazadoException(mensajeRechazo, "ERR_RECHAZADO", pago.getMetodoPago());
            }

            return pago;

        } catch (PagoRechazadoException e) {
            throw e; // Re-lanzar excepciones de negocio
        } catch (Exception e) {
            log.error("Error inesperado procesando pago", e);
            pago.marcarComoRechazado("Error técnico en el procesamiento");
            throw new PagoFallidoException(
                    "Error procesando el pago con " + pago.getMetodoPago(),
                    pago.getMetodoPago(),
                    e
            );
        }
    }

    /**
     * Valida los datos del pago según el método
     */
    private void validarDatosPago(Pago pago) {
        MetodoPago metodo = pago.getMetodoPago();

        if (metodo.requiereTarjeta()) {
            validarDatosTarjeta(pago);
        }

        if (metodo.requiereReferencia()) {
            validarDatosTransferencia(pago);
        }

        if (metodo == MetodoPago.NEQUI || metodo == MetodoPago.DAVIPLATA) {
            validarDatosBilletera(pago);
        }
    }

    /**
     * Valida datos de tarjeta (simulado)
     */
    private void validarDatosTarjeta(Pago pago) {
        if (pago.getNumeroTarjeta() == null || pago.getNumeroTarjeta().length() < 4) {
            throw new IllegalArgumentException("Número de tarjeta inválido");
        }

        if (pago.getNombreTitular() == null || pago.getNombreTitular().isBlank()) {
            throw new IllegalArgumentException("Nombre del titular requerido");
        }

        log.debug("Datos de tarjeta validados: ****{}", pago.getNumeroTarjeta());
    }

    /**
     * Valida datos de transferencia (simulado)
     */
    private void validarDatosTransferencia(Pago pago) {
        if (pago.getNumeroReferencia() == null || pago.getNumeroReferencia().isBlank()) {
            throw new IllegalArgumentException("Número de referencia requerido");
        }

        log.debug("Referencia validada: {}", pago.getNumeroReferencia());
    }

    /**
     * Valida datos de billetera digital (simulado)
     */
    private void validarDatosBilletera(Pago pago) {
        if (pago.getNumeroCelular() == null || !pago.getNumeroCelular().matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Número de celular inválido");
        }

        log.debug("Número de celular validado: {}", pago.getNumeroCelular());
    }

    /**
     * Simula el tiempo de procesamiento de la pasarela
     */
    private void simularProcesamiento() {
        try {
            // Simular latencia de red (100-500ms)
            Thread.sleep(random.nextInt(400) + 100L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Genera un código de autorización simulado
     */
    private String generarCodigoAutorizacion() {
        return "AUTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Simula mensajes de rechazo según el método de pago
     */
    private String simularMensajeRechazo() {
        String[] mensajes = {
                "Fondos insuficientes",
                "Tarjeta declinada por el emisor",
                "Transacción no autorizada",
                "Error de comunicación con el banco",
                "Límite de transacciones excedido"
        };
        return mensajes[random.nextInt(mensajes.length)];
    }

}
