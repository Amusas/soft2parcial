package product.service.parcial2soft.dto.gafas;

import jakarta.validation.constraints.*;
import product.service.parcial2soft.entity.gafas.MaterialMontura;
import product.service.parcial2soft.entity.gafas.TipoGafas;
import product.service.parcial2soft.entity.gafas.TipoLente;

import java.math.BigDecimal;

// DTO de entrada para crear gafas
public record GafasCreateDTO(

        @NotBlank(message = "El código es obligatorio")
        @Size(min = 3, max = 50, message = "El código debe tener entre 3 y 50 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
        String nombre,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion,

        @NotBlank(message = "La marca es obligatoria")
        @Size(min = 2, max = 100, message = "La marca debe tener entre 2 y 100 caracteres")
        String marca,

        @Size(max = 100, message = "El modelo no puede exceder 100 caracteres")
        String modelo,

        @NotNull(message = "El tipo de gafas es obligatorio")
        TipoGafas tipo,

        @NotNull(message = "El material de la montura es obligatorio")
        MaterialMontura material,

        @Size(max = 50, message = "El color no puede exceder 50 caracteres")
        String color,

        @Size(max = 20, message = "El género no puede exceder 20 caracteres")
        String genero,

        TipoLente tipoLente,

        @Size(max = 20, message = "La talla no puede exceder 20 caracteres")
        String talla,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
        BigDecimal precio,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$",
                message = "La URL de la imagen debe ser válida",
                flags = Pattern.Flag.CASE_INSENSITIVE)
        @Size(max = 500, message = "La URL de la imagen no puede exceder 500 caracteres")
        String urlImagen,

        Boolean tieneProteccionUV,

        Boolean esPolarizada,

        Boolean graduable
) {}
