package product.service.parcial2soft.entity.pedido;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import product.service.parcial2soft.entity.Cliente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true, length = 50)
    private String numeroPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pago pago;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Recibo recibo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false, length = 30)
    private MetodoPago metodoPago;

    @Column(length = 500)
    private String observaciones;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    // Métodos de ayuda para mantener la bidireccionalidad
    public void addDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    public void removeDetalle(DetallePedido detalle) {
        detalles.remove(detalle);
        detalle.setPedido(null);
    }

    public void setPago(Pago pago) {
        this.pago = pago;
        if (pago != null) {
            pago.setPedido(this);
        }
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
        if (recibo != null) {
            recibo.setPedido(this);
        }
    }

    // Método para calcular totales
    public void calcularTotales() {
        this.subtotal = detalles.stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = subtotal;
    }

    // Método para verificar si el pedido está pagado
    public boolean estaPagado() {
        return EstadoPedido.PAGADO.equals(this.estado);
    }

    // Método para verificar si el pedido puede ser cancelado
    public boolean puedeCancelarse() {
        return EstadoPedido.PENDIENTE.equals(this.estado);
    }

    // Método para marcar como pagado
    public void marcarComoPagado() {
        this.estado = EstadoPedido.PAGADO;
        this.fechaPago = LocalDateTime.now();
    }

    // Método para cancelar
    public void cancelar() {
        if (!puedeCancelarse()) {
            throw new IllegalStateException("No se puede cancelar un pedido en estado: " + this.estado);
        }
        this.estado = EstadoPedido.CANCELADO;
    }
}
