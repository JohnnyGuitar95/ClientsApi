package org.JG95.advices.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ThrowingAspect {
    @AfterThrowing("@annotation(org.JG95.advices.annotations.LoggingExecAfterThrow)")
    public void logThrow(JoinPoint joinPoint) {
        log.warn("Method {} has thrown an exception in {}", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName());
    }
}
