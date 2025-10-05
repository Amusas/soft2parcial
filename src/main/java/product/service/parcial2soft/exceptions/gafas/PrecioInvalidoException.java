package product.service.parcial2soft.exceptions.gafas;

// Excepción para precio inválido
public class PrecioInvalidoException extends RuntimeException {
    public PrecioInvalidoException(String message) {
        super(message);
    }
}
