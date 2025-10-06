package product.service.parcial2soft.exceptions.pedido;

import product.service.parcial2soft.entity.pedido.MetodoPago;

/**
 * Excepción cuando hay un error procesando el pago
 */
public class PagoFallidoException extends RuntimeException {
    private final MetodoPago metodoPago;

    public PagoFallidoException(String mensaje, MetodoPago metodoPago) {
        super(mensaje);
        this.metodoPago = metodoPago;
    }

    public PagoFallidoException(String mensaje, MetodoPago metodoPago, Throwable causa) {
        super(mensaje, causa);
        this.metodoPago = metodoPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }
}
