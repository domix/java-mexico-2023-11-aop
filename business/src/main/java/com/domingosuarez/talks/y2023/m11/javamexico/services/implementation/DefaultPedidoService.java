package com.domingosuarez.talks.y2023.m11.javamexico.services.implementation;

import com.domingosuarez.talks.y2023.m11.javamexico.services.BusinessException;
import com.domingosuarez.talks.y2023.m11.javamexico.services.PedidoService;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.random.RandomGenerator;

@Slf4j
public class DefaultPedidoService implements PedidoService {
    private final Set<Long> reservationStorage = new HashSet<>(100);

    @Override
    public Long reservar(String productId, Integer cantidad, Duration periodo) throws BusinessException {
        Optional.ofNullable(productId)
                .filter(s -> !s.isBlank())
                .orElseThrow(() -> new BusinessException("El identificador de producto es invalido."));

        if (Objects.isNull(cantidad) || cantidad <= 0) {
            throw new BusinessException("La cantidad de producto debe ser un numero positivo.");
        }

        if (Objects.isNull(periodo)) {
            throw new BusinessException("El periodo no puede ser NULL.");
        }

        if (periodo.isNegative()) {
            throw new BusinessException("El periodo de reserva no puede ser negativo.");
        }

        if (periodo.isZero()) {
            throw new BusinessException("El periodo de reserva no puede ser cero.");
        }

        if (periodo.compareTo(PedidoService.MAX_TIME) >= 1) {
            throw new BusinessException("El periodo de reserva no puede ser mayor a %d minutos."
                    .formatted(PedidoService.MAX_TIME.toMinutes()));
        }

        //asumamos el resto de la implementacion

        long reservationId = RandomGenerator.getDefault().nextLong();
        reservationStorage.add(reservationId);
        return reservationId;
    }

    @Override
    public Long realizarPedido(Long... identificadoresDeReserva) throws BusinessException {
        for (Long identificador : identificadoresDeReserva) {
            if (!existeReserva(identificador)) {
                throw new BusinessException("El identificador de reserva '%d' no existe.".formatted(identificador));
            }
        }

        for (Long identificador : identificadoresDeReserva) {
            reservationStorage.remove(identificador);
        }

        //asumamos el resto de la implementacion
        return RandomGenerator.getDefault().nextLong();
    }

    @Override
    public boolean existeReserva(Long identificadorReserva) {
        return reservationStorage.contains(identificadorReserva);
    }

    @Override
    public void borrarTodasLasReservas() {
        log.warn("borrando todas las reservas");
        reservationStorage.clear();
    }
}
