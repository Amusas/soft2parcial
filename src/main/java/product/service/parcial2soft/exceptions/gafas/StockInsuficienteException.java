package product.service.parcial2soft.exceptions.gafas;

// Excepción cuando no hay stock suficiente
public class StockInsuficienteException extends RuntimeException {
    private final String codigoProducto;
    private final Integer stockDisponible;
    private final Integer stockSolicitado;

    public StockInsuficienteException(String codigoProducto, Integer stockDisponible, Integer stockSolicitado) {
        super(String.format("Stock insuficiente para el producto '%s'. Disponible: %d, Solicitado: %d",
                codigoProducto, stockDisponible, stockSolicitado));
        this.codigoProducto = codigoProducto;
        this.stockDisponible = stockDisponible;
        this.stockSolicitado = stockSolicitado;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }

    public Integer getStockSolicitado() {
        return stockSolicitado;
    }
}

