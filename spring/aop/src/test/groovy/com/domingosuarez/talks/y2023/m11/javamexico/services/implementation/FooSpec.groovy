package com.domingosuarez.talks.y2023.m11.javamexico.services.implementation

import com.domingosuarez.talks.y2023.m11.javamexico.services.BusinessException
import com.domingosuarez.talks.y2023.m11.javamexico.services.FooService
import com.domingosuarez.talks.y2023.m11.javamexico.services.PedidoService
import groovy.util.logging.Slf4j
import org.springframework.context.support.ClassPathXmlApplicationContext
import spock.lang.Specification

import java.time.Duration

@Slf4j
class FooSpec extends Specification {

    def foo() {
        given:
            def applicationContext = new ClassPathXmlApplicationContext('/application-context.xml')
            def pedidoService = applicationContext.getBean(PedidoService)
        when:
            log.info('_' * 120)
            pedidoService.realizarPedido(1l)
        then:
            def e = thrown(BusinessException)
            e.message == "El identificador de reserva '1' no existe."
            e.printStackTrace()
        and:
            log.info('_' * 120)
        when:
            def reservar = pedidoService.reservar('foo', 1, Duration.ofSeconds(30))
        then:
            reservar
        and:
            log.info('_' * 120)
            def fooService = applicationContext.getBean(FooService)
        when:
            def nothing = fooService.doNothing()
        then:
            nothing
    }

}
