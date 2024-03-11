package com.sparta.deliveryproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j(topic = "ParameterAop")
@Aspect
@Component
public class ParameterAop {

    @Pointcut("execution(* com.sparta.deliveryproject.controller..*.*(..))")
    private void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info(method.getName() + "메서드 실행");

        Object[] args = joinPoint.getArgs();

        for (Object obj : args) {
            log.info("type : " + obj.getClass().getSimpleName());
            log.info("value : " + obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "obj")
    public void afterReturn(JoinPoint joinPoint, Object obj) {
        log.info("return obj" + obj);
    }
}