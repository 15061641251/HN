package com.sbq.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 加密aop
 * Created by zhangyuan on 2017/3/22.
 */
@Aspect
@Component
public class RSAAspect {

    @Pointcut("execution(public * com.sbq.ws.controller..*.*(..))")
    public void rsaController() {

    }

    /**
     * 修改目标函数的参数和返回值
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("rsaController()")
    public Object door(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();


        //在此之前修改方法参数
        Object retVal = pjp.proceed(args);

        //在此之后对方法处理的返回值进行修改

        return retVal;
    }
}
