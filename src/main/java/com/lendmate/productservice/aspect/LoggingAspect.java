package com.lendmate.productservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(* com.lendmate.productservice.service.*.*(..))")
    public void serviceLayer() {}

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            log.debug("[START] {} | args: {}",
                    joinPoint.getSignature().toShortString(),
                    Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        if (log.isDebugEnabled()) {
            if (result instanceof Collection<?> collection) {
                log.debug("[END] {} | result size: {}",
                        joinPoint.getSignature().toShortString(),
                        collection.size());
            } else {
                log.debug("[END] {} | result: {}",
                        joinPoint.getSignature().toShortString(),
                        result);
            }
        }
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("[EXCEPTION] {} | cause: {} | message: {}",
                joinPoint.getSignature().toShortString(),
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                exception); // Dilersen stack trace'i de yazdırmak için exception nesnesini en sona ekleyebilirsin
    }
}