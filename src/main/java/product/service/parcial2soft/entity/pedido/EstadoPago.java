package product.service.parcial2soft.entity.pedido;

// Enum para estado del pago
public enum EstadoPago {
    PENDIENTE("Pendiente"),
    PROCESANDO("Procesando"),
    EXITOSO("Exitoso"),
    RECHAZADO("Rechazado"),
    REEMBOLSADO("Reembolsado");

    private final String descripcion;

    EstadoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

