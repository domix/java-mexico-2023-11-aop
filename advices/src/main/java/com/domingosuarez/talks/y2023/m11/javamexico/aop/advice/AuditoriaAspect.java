package com.domingosuarez.talks.y2023.m11.javamexico.aop.advice;

import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class AuditoriaAspect {
    public AuditoriaAspect() {
        log.trace("Creando aspecto de auditoria.");
    }

    final static TimeUnit timeUnit = TimeUnit.NANOSECONDS;

    @Around("execution(* com.domingosuarez.talks.y2023.m11.javamexico.services.*.*(..))")
    public Object auditarOperacion(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();

        // Lógica de auditoría antes de la ejecución del método
        log.trace("Operación en algun servicio: " + joinPoint.getSignature().toShortString());

        // Ejecutar el método original y capturar el resultado
        Object resultado;
        var result = "exito";
        final var serviceClass = joinPoint.getTarget().getClass().getName();
        final var name = joinPoint.getStaticPart().getSignature().getName();
        final var user = Arrays.stream(joinPoint.getArgs())
            .filter(arg -> arg instanceof Principal)
            .map(arg -> (Principal) arg)
            .findFirst()
            .map(Principal::getName);

        try {
            watch.start();
            resultado = joinPoint.proceed();
        } catch (Throwable throwable) {
            result = "error";
            throw throwable;
        } finally {
            watch.stop();
            // Lógica de auditoría después de la ejecución del método
            log.trace("Operación completada en Servicio.");

            long time = watch.getTime(timeUnit);

            String[] tags = {
                "result", result,
                "serviceClass", serviceClass,
                "serviceMethod", name,
                "user", user.orElse("anon")
            };

            log.trace("Tiempo de ejecución: '{} millis'. Con resultado: '{}'", timeUnit.toMillis(time), result);
            log.trace("Tags usados: {}", Arrays.stream(tags).toList());

            Metrics.globalRegistry
                .timer("service.execution_timer", tags)
                .record(time, timeUnit);
        }

        // Devolver el resultado original
        return resultado;
    }
}
