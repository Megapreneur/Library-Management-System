package com.uncledemy.librarymanagementsystem.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.uncledemy.librarymanagementsystem.service.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        logger.info("Method '{}' is about to be executed.", joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "execution(* com.uncledemy.librarymanagementsystem.service.*.*(..))", throwing = "ex")
    public void logAfterThrowingException(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception thrown in method '{}': {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
    @AfterReturning(pointcut = "execution(* com.uncledemy.librarymanagementsystem.service.*.*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {}", joinPoint.getSignature().toShortString());
    }

}
