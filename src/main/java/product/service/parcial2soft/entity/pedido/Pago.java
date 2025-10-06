package product.service.parcial2soft.entity.pedido;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "monto_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false, length = 30)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false, length = 20)
    @Builder.Default
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    // Para tarjetas
    @Column(name = "numero_tarjeta", length = 20)
    private String numeroTarjeta; // Últimos 4 dígitos

    @Column(name = "nombre_titular", length = 200)
    private String nombreTitular;

    @Column
    private Integer cuotas;

    @Column(name = "valor_cuota", precision = 10, scale = 2)
    private BigDecimal valorCuota;

    // Para transferencias y PSE
    @Column(name = "numero_referencia", length = 100)
    private String numeroReferencia;

    @Column(name = "banco_origen", length = 100)
    private String bancoOrigen;

    // Para billeteras digitales
    @Column(name = "numero_celular", length = 20)
    private String numeroCelular;

    @Column(name = "nombre_cuenta", length = 200)
    private String nombreCuenta;

    // Información de la transacción
    @Column(name = "codigo_autorizacion", length = 50)
    private String codigoAutorizacion;

    @Column(name = "mensaje_respuesta", length = 500)
    private String mensajeRespuesta;

    @CreationTimestamp
    @Column(name = "fecha_procesamiento", nullable = false, updatable = false)
    private LocalDateTime fechaProcesamiento;

    // Métodos de ayuda
    public boolean esPagoExitoso() {
        return EstadoPago.EXITOSO.equals(this.estadoPago);
    }

    public void marcarComoExitoso(String codigoAutorizacion) {
        this.estadoPago = EstadoPago.EXITOSO;
        this.codigoAutorizacion = codigoAutorizacion;
        this.mensajeRespuesta = "Pago procesado exitosamente";
    }

    public void marcarComoRechazado(String mensajeError) {
        this.estadoPago = EstadoPago.RECHAZADO;
        this.mensajeRespuesta = mensajeError;
    }

    public void marcarComoProcesando() {
        this.estadoPago = EstadoPago.PROCESANDO;
    }

    // Método para enmascarar número de tarjeta
    public static String enmascararTarjeta(String numeroCompleto) {
        if (numeroCompleto == null || numeroCompleto.length() < 4) {
            return "****";
        }
        return "****" + numeroCompleto.substring(numeroCompleto.length() - 4);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pago)) return false;
        Pago pago = (Pago) o;
        return id != null && id.equals(pago.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}