package com.domingosuarez.talks.y2023.m11.javamexico;

import com.domingosuarez.talks.y2023.m11.javamexico.services.PedidoService;
import com.domingosuarez.talks.y2023.m11.javamexico.services.implementation.DefaultPedidoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ContextConfig {
    @Bean
    public PedidoService pedidoService() {
        System.out.println("Creando pedidoServoce");
        return new DefaultPedidoService();
    }
}
