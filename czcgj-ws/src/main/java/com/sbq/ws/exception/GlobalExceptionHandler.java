package com.sbq.ws.exception;

import com.sbq.common.response.BaseSResponse;
import com.sbq.common.response.server_error.ServerErrorSReponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("error");
//        return mav;
//    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseSResponse jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        String exception_message = "";

        BaseSResponse response = new ServerErrorSReponse();

        if (e != null) {
            exception_message = ((UndeclaredThrowableException) e).getUndeclaredThrowable().getMessage();
        } else {
            exception_message = response.getDescription();
        }

        response.setDescription(exception_message);
        return response;
    }

}

