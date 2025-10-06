package product.service.parcial2soft.entity.pedido;

// Enum para método de pago (solo digitales)
public enum MetodoPago {
    TARJETA_CREDITO("Tarjeta de Crédito"),
    TARJETA_DEBITO("Tarjeta de Débito"),
    TRANSFERENCIA_BANCARIA("Transferencia Bancaria"),
    PSE("PSE - Débito Bancario"),
    NEQUI("Nequi"),
    DAVIPLATA("Daviplata"),
    PAYPAL("PayPal");

    private final String descripcion;

    MetodoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean requiereTarjeta() {
        return this == TARJETA_CREDITO || this == TARJETA_DEBITO;
    }

    public boolean requiereReferencia() {
        return this == TRANSFERENCIA_BANCARIA || this == PSE;
    }
}
