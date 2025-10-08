package product.service.parcial2soft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import product.service.parcial2soft.dto.pedidos.DatosPagoDTO;
import product.service.parcial2soft.dto.pedidos.PagoResponseDTO;
import product.service.parcial2soft.entity.pedido.MetodoPago;
import product.service.parcial2soft.entity.pedido.Pago;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    @Mapping(target = "numeroTarjeta", source = "numeroTarjeta")
    PagoResponseDTO toResponseDTO(Pago entity);

    // Método para crear entity desde DTO de entrada
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "estadoPago", constant = "PENDIENTE")
    @Mapping(target = "codigoAutorizacion", ignore = true)
    @Mapping(target = "mensajeRespuesta", ignore = true)
    @Mapping(target = "fechaProcesamiento", ignore = true)
    @Mapping(target = "montoPagado", source = "montoPagado")
    @Mapping(target = "metodoPago", source = "metodoPago")
    @Mapping(target = "numeroTarjeta", source = "datosPago.numeroTarjeta")
    @Mapping(target = "nombreTitular", source = "datosPago.nombreTitular")
    @Mapping(target = "cuotas", source = "datosPago.cuotas")
    @Mapping(target = "numeroReferencia", source = "datosPago.numeroReferencia")
    @Mapping(target = "bancoOrigen", source = "datosPago.bancoOrigen")
    @Mapping(target = "numeroCelular", source = "datosPago.numeroCelular")
    @Mapping(target = "nombreCuenta", source = "datosPago.nombreCuenta")
    Pago toEntity(DatosPagoDTO datosPago, MetodoPago metodoPago, java.math.BigDecimal montoPagado);
}