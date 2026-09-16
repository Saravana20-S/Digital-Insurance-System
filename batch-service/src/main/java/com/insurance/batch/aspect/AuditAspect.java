package com.insurance.batch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    @Around("execution(* com.insurance.batch.service..*(..))")
    public Object auditServiceMethod(
            ProceedingJoinPoint joinPoint) throws Throwable {

        String method =
                joinPoint.getSignature().getName();

        try {

            Object result = joinPoint.proceed();

            log.info(
                    "AUDIT SUCCESS: service={}, method={}",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    method
            );

            return result;

        } catch (Exception exception) {

            log.error(
                    "AUDIT FAILURE: method={}, error={}",
                    method,
                    exception.getMessage()
            );

            throw exception;
        }
    }
}