package com.sbq.aspect;

import com.sbq.annotation.RequestLog;
import com.sbq.entity.Log;
import com.sbq.exception.RequestLimitException;
import com.sbq.service.ILogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class RequestLogAspect {


    @Autowired
    private ILogService logService;


    @Before("@annotation(requestLog)")
    public void beforeExec(JoinPoint joinPoint, RequestLog requestLog) throws RequestLimitException {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String ip = request.getRemoteAddr();
//        String url = request.getRequestURL().toString();

        String moduleName = requestLog.moduleName();

        Object[] args = joinPoint.getArgs();
        String params = Arrays.toString(args);

        Log log = new Log();

        log.setIp(ip);
        log.setModule_name(moduleName);
        log.setParams(params);

        logService.insertLog(log);

    }
}
