package product.service.parcial2soft.exceptions.gafas;

public class CodigoDuplicadoException extends RuntimeException {
    private final String codigo;

    public CodigoDuplicadoException(String codigo) {
        super(String.format("Ya existe un producto con el código '%s'", codigo));
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
