package org.JG95.advices.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("@annotation(org.JG95.advices.annotations.LoggingExecAround)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint)  {
        log.info("Aspect has started before method {}", proceedingJoinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        log.info("Aspect has finished after method {}", proceedingJoinPoint.getSignature().getName());
        log.info("Time elapsed : {} ms", endTime - startTime);
        return result;
    }
}
