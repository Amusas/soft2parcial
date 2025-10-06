package product.service.parcial2soft.exceptions.pedido;

/**
 * Excepción cuando se intenta cancelar un pedido que no puede ser cancelado
 */
public class PedidoNoCancelableException extends RuntimeException {
    private final String numeroPedido;
    private final String estadoActual;

    public PedidoNoCancelableException(String numeroPedido, String estadoActual) {
        super(String.format("El pedido '%s' no puede ser cancelado. Estado actual: %s",
                numeroPedido, estadoActual));
        this.numeroPedido = numeroPedido;
        this.estadoActual = estadoActual;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public String getEstadoActual() {
        return estadoActual;
    }
}