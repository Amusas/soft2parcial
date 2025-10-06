package product.service.parcial2soft.exceptions.pedido;

import product.service.parcial2soft.entity.pedido.MetodoPago;

/**
 * Excepción cuando los datos del pago son inválidos
 */
public class DatosPagoInvalidosException extends RuntimeException {
    private final MetodoPago metodoPago;

    public DatosPagoInvalidosException(String mensaje, MetodoPago metodoPago) {
        super(mensaje);
        this.metodoPago = metodoPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }
}
