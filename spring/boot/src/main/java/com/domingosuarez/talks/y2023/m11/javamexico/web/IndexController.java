package com.domingosuarez.talks.y2023.m11.javamexico.web;

import com.domingosuarez.talks.y2023.m11.javamexico.aop.advice.AuditoriaAspect;
import com.domingosuarez.talks.y2023.m11.javamexico.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController("/")
@RequiredArgsConstructor
public class IndexController {
    private final PedidoService pedidoService;
    private final AuditoriaAspect auditoriaAspect;

    @GetMapping
    public String reset() {
        pedidoService.borrarTodasLasReservas();

        return "respuesta";
    }

    @PutMapping
    public ReservaResponse reservar(@RequestBody ReservaCommand reserva) {

        final var reservaId = pedidoService.reservar(
            reserva.getProductId(),
            reserva.getCantidad(),
            Duration.ofMinutes(reserva.getMinutes())
        );

        return ReservaResponse.builder()
            .reservaId(reservaId)
            .reservaInput(reserva)
            .build();
    }

    @PostMapping
    public Long pedido(@RequestBody PedidoCommand pedidoCommand) {
        return pedidoService.realizarPedido(pedidoCommand.getReservaId());
    }


}
