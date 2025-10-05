package product.service.parcial2soft.entity.gafas;

// Enum para tipo de lente
public enum TipoLente {
    ORGANICO("Orgánico"),
    CRISTAL("Cristal"),
    POLICARBONATO("Policarbonato"),
    TRIVEX("Trivex"),
    HIGH_INDEX("High Index");

    private final String descripcion;

    TipoLente(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
