package product.service.parcial2soft.entity.pedido;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recibos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "numero_recibo", nullable = false, unique = true, length = 50)
    private String numeroRecibo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @CreationTimestamp
    @Column(name = "fecha_emision", nullable = false, updatable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @Column(name = "motivo_anulacion", length = 500)
    private String motivoAnulacion;

    @Column(name = "fecha_anulacion")
    private LocalDateTime fechaAnulacion;

    // Métodos de ayuda
    public void anular(String motivo) {
        if (Boolean.FALSE.equals(this.activo)) {
            throw new IllegalStateException("El recibo ya está anulado");
        }
        this.activo = false;
        this.motivoAnulacion = motivo;
        this.fechaAnulacion = LocalDateTime.now();
    }

    public boolean estaActivo() {
        return this.activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recibo)) return false;
        Recibo recibo = (Recibo) o;
        return id != null && id.equals(recibo.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
