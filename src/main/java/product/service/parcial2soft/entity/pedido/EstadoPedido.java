package product.service.parcial2soft.entity.pedido;

// Enum para estado del pedido
public enum EstadoPedido {
    PENDIENTE("Pendiente de Pago"),
    PAGADO("Pagado"),
    CANCELADO("Cancelado"),
    ANULADO("Anulado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
