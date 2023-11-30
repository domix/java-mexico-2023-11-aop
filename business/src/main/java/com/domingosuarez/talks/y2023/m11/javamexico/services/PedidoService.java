package com.domingosuarez.talks.y2023.m11.javamexico.services;

import java.time.Duration;

public interface PedidoService {
    Duration MAX_TIME = Duration.ofMinutes(30);

    /**
     * Reserva el producto especificado por el tiempo especificado.
     * <p>
     * Se lanzara una exception del tipo {@link BusinessException} si alguna
     * de las siguientes reglas se rompe:
     * 1. El identificador del producto no existe.
     * 2. No hay cantidad disponible del producto solicitado.
     * 3. El periodo definido es invalido. Negativo o mayor a 30 minutos.
     *
     * @param productId El identificador del producto.
     * @param cantidad  La cantidad de producto a reservar.
     * @param periodo   El periodo de tiempo a mantener reservado el producto.
     * @return Un identificador de reserva de producto.
     * Este identificador debe usarse despues al realizar un pedido.
     * @throws BusinessException En caso de que alguna regla de negocio se rompa.
     */
    Long reservar(String productId, Integer cantidad, Duration periodo) throws BusinessException;

    /**
     * Realiza el pedido de los productos que correspondan a una reservacion previa.
     * Se lanzara una exception del tipo {@link BusinessException} si alguna
     * de las siguientes reglas se rompe:
     * 1. No existe alguno de los identicadores proporcionados.
     *
     * @param identificadoresDeReserva El conjunto de identificadores de reserva de producto.
     * @return Un identificador de pedido realizado.
     * @throws BusinessException En caso de que alguna regla de negocio se rompa.
     */
    Long realizarPedido(Long... identificadoresDeReserva) throws BusinessException;

    boolean existeReserva(Long identificadorReserva);

    void borrarTodasLasReservas();
}
