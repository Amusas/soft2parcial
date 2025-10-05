package product.service.parcial2soft.exceptions;

// Excepción cuando no se encuentra un recurso
public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message) {
        super(message);
    }
}

