package product.service.parcial2soft.exceptions.pedido;

/**
 * Excepción cuando el pedido no puede ser procesado
 */
public class PedidoNoProcesableException extends RuntimeException {
    private final String codigoPedido;

    public PedidoNoProcesableException(String mensaje) {
        super(mensaje);
        this.codigoPedido = null;
    }

    public PedidoNoProcesableException(String mensaje, String codigoPedido) {
        super(mensaje);
        this.codigoPedido = codigoPedido;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }
}

