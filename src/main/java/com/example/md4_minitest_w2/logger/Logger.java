package com.example.md4_minitest_w2.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logger {

    @AfterThrowing(pointcut = "execution(public * com.example.md4_minitest_w2.service.impl.ComputerService.findAll(..))", throwing = "e")
    public void logFindAllException(Exception e) {
        System.out.println("[CMS] An error occurred in findAll: " + e.getMessage());
    }

    @AfterThrowing(pointcut = "execution(public * com.example.md4_minitest_w2.service.impl.ComputerService.*(..))", throwing = "e")
    public void logAllComputerServiceExceptions(JoinPoint joinPoint, Exception e) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.printf("[CMS] An error occurred in %s.%s%s: %s%n", className, methodName, args, e.getMessage());
    }
}
