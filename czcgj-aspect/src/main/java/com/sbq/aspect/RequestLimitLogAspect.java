package com.sbq.aspect;

import com.sbq.annotation.RequestLimitLog;
import com.sbq.exception.RequestLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Aspect
@Component
public class RequestLimitLogAspect {

    @Before("@annotation(requestLimitLog)")
    public void beforeExec(JoinPoint joinPoint, RequestLimitLog requestLimitLog) throws RequestLimitException {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String ip = request.getLocalAddr();
        String url = request.getRequestURL().toString();
        String key = "req_limit_".concat(url).concat(ip);

        System.out.println("RequestLimitLogAspect-" + ip + "-" + url + "-" + key);

        //限流要结合redis,参考：http://blog.csdn.net/linzhiqiang0316/article/details/52671293
        if (new Random().nextInt() % 2 == 0) {
            throw new RequestLimitException("请求太过频繁");
        }
    }

}
