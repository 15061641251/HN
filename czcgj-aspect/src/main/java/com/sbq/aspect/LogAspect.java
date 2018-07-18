package com.sbq.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author TIM
 * @create 2016-11-16 14
 **/
@Aspect
@Component
public class LogAspect {


    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.sbq.web.controller..*.*(..)) || execution(public * com.sbq.ws.controller..*.*(..))")
    public void allController() {

    }

    @Before("allController()")
    public void tip(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        Object[] args = joinPoint.getArgs();
        System.out.println(("REQUEST : " + Arrays.toString(args)));
    }

    @AfterReturning(returning = "ret", pointcut = "allController()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("RESPONSE : " + ret);
        System.out.println("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }


}
