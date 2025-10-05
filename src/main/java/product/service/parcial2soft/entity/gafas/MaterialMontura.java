package product.service.parcial2soft.entity.gafas;

public enum MaterialMontura {
    ACETATO("Acetato"),
    METAL("Metal"),
    TITANIO("Titanio"),
    PLASTICO("Plástico"),
    ALUMINIO("Aluminio"),
    MADERA("Madera"),
    CARBONO("Fibra de Carbono");

    private final String descripcion;

    MaterialMontura(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
