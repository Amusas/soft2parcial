package product.service.parcial2soft.exceptions.gafas;

// Excepción cuando un producto no está disponible
public class ProductoNoDisponibleException extends RuntimeException {
    private final String codigoProducto;

    public ProductoNoDisponibleException(String codigoProducto) {
        super(String.format("El producto '%s' no está disponible actualmente", codigoProducto));
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }
}
