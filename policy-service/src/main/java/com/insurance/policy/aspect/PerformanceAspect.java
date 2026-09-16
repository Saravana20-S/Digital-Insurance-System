package com.insurance.policy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("execution(* com.insurance.policy.service..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint)
            throws Throwable {

        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {

            long executionTime =
                    System.currentTimeMillis() - start;

            log.info(
                    "Method={} executed in {} ms",
                    joinPoint.getSignature().toShortString(),
                    executionTime
            );
        }
    }
}