package product.service.parcial2soft.exceptions.pedido;

import product.service.parcial2soft.entity.pedido.MetodoPago;

/**
 * Excepción cuando el pago es rechazado
 */
public class PagoRechazadoException extends RuntimeException {
    private final String codigoError;
    private final MetodoPago metodoPago;

    public PagoRechazadoException(String mensaje, String codigoError, MetodoPago metodoPago) {
        super(mensaje);
        this.codigoError = codigoError;
        this.metodoPago = metodoPago;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }
}

