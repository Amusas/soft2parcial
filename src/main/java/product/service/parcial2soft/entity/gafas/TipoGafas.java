package product.service.parcial2soft.entity.gafas;

// Enum para tipo de gafas
public enum TipoGafas {
    SOL("Gafas de Sol"),
    LECTURA("Gafas de Lectura"),
    DEPORTIVAS("Gafas Deportivas"),
    SEGURIDAD("Gafas de Seguridad"),
    MODA("Gafas de Moda");

    private final String descripcion;

    TipoGafas(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
