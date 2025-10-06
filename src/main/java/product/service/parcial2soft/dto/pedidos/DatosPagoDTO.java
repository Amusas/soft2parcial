package product.service.parcial2soft.dto.pedidos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import product.service.parcial2soft.entity.pedido.MetodoPago;

/**
 * DTO para datos del pago según el método
 */
public record DatosPagoDTO(
        // Para tarjetas de crédito/débito
        @Size(min = 13, max = 19, message = "El número de tarjeta debe tener entre 13 y 19 dígitos")
        @Pattern(regexp = "^[0-9]*$", message = "El número de tarjeta solo debe contener dígitos")
        String numeroTarjeta,

        @Size(max = 200, message = "El nombre del titular no puede exceder 200 caracteres")
        String nombreTitular,

        @Min(value = 1, message = "El número de cuotas mínimo es 1")
        @Max(value = 36, message = "El número de cuotas máximo es 36")
        Integer cuotas,

        // Para transferencias y PSE
        @Size(max = 100, message = "El número de referencia no puede exceder 100 caracteres")
        String numeroReferencia,

        @Size(max = 100, message = "El banco de origen no puede exceder 100 caracteres")
        String bancoOrigen,

        // Para billeteras digitales (Nequi, Daviplata)
        @Pattern(regexp = "^[0-9]{10}$", message = "El número de celular debe tener 10 dígitos")
        String numeroCelular,

        @Size(max = 200, message = "El nombre de cuenta no puede exceder 200 caracteres")
        String nombreCuenta
) {
    // Validaciones adicionales según método de pago
    public void validarParaMetodo(MetodoPago metodo) {
        if (metodo.requiereTarjeta()) {
            if (numeroTarjeta == null || numeroTarjeta.isBlank()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio para " + metodo);
            }
            if (nombreTitular == null || nombreTitular.isBlank()) {
                throw new IllegalArgumentException("El nombre del titular es obligatorio para " + metodo);
            }
        }

        if (metodo.requiereReferencia()) {
            if (numeroReferencia == null || numeroReferencia.isBlank()) {
                throw new IllegalArgumentException("El número de referencia es obligatorio para " + metodo);
            }
        }

        if (metodo == MetodoPago.NEQUI || metodo == MetodoPago.DAVIPLATA) {
            if (numeroCelular == null || numeroCelular.isBlank()) {
                throw new IllegalArgumentException("El número de celular es obligatorio para " + metodo);
            }
        }
    }
}
