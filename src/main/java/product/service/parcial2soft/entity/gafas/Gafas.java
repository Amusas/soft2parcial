package product.service.parcial2soft.entity.gafas;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gafas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gafas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(length = 100)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoGafas tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MaterialMontura material;

    @Column(length = 50)
    private String color;

    @Column(length = 20)
    private String genero; // Unisex, Hombre, Mujer, Niño

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoLente tipoLente;

    @Column(length = 20)
    private String talla; // S, M, L o medidas específicas

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(name = "url_imagen", length = 500)
    private String urlImagen;

    @Column(name = "tiene_proteccion_uv")
    private Boolean tieneProteccionUV = false;

    @Column(name = "es_polarizada")
    private Boolean esPolarizada = false;

    @Column(name = "graduable")
    private Boolean graduable = false;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private Boolean activo = true;

    // Método auxiliar para verificar si hay stock disponible
    public boolean tieneStock() {
        return this.stock != null && this.stock > 0;
    }

}
