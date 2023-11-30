package com.domingosuarez.talks.y2023.m11.javamexico.services.implementation

import com.domingosuarez.talks.y2023.m11.javamexico.services.BusinessException
import com.domingosuarez.talks.y2023.m11.javamexico.services.PedidoService
import spock.lang.Specification
import spock.lang.Subject

import java.time.Duration

class DefaultPedidoServiceSpecs extends Specification {
    @Subject
    PedidoService underTest = new DefaultPedidoService()

    def 'should throw exception due business rules violation'() {

        when:
            underTest.reservar(productId, cantidad, periodo)
        then:
            def businessException = thrown(BusinessException)
            businessException.message == exceptionMessage
        where:
            productId | cantidad | periodo                 || exceptionMessage
            null      | null     | null                    || 'El identificador de producto es invalido.'
            ''        | null     | null                    || 'El identificador de producto es invalido.'
            ' '       | null     | null                    || 'El identificador de producto es invalido.'
            'foo'     | null     | null                    || 'La cantidad de producto debe ser un numero positivo.'
            'foo'     | 0        | null                    || 'La cantidad de producto debe ser un numero positivo.'
            'foo'     | -1       | null                    || 'La cantidad de producto debe ser un numero positivo.'
            'foo'     | 1        | null                    || 'El periodo no puede ser NULL.'
            'foo'     | 1        | Duration.ofMillis(-100) || 'El periodo de reserva no puede ser negativo.'
            'foo'     | 1        | Duration.ofMillis(0)    || 'El periodo de reserva no puede ser cero.'
            'foo'     | 1        | Duration.ofMinutes(31)  || 'El periodo de reserva no puede ser mayor a 30 minutos.'
    }

    def foo() {
        given:
            def reserva = underTest.reservar('foo', 4, Duration.ofMinutes(5))
        when:
            def pedido = underTest.realizarPedido(reserva)
        then:
            pedido
        and:
            underTest.borrarTodasLasReservas()
        when:
            underTest.realizarPedido(1l)
        then:
            def businessException = thrown(BusinessException)
            businessException.message == "El identificador de reserva '1' no existe."
    }
}
