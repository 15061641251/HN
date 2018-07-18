package com.sbq.web.controller;

import com.sbq.common.response.BaseSResponse;
import com.sbq.common.response.request_error.RequestErrorSReponse;
import com.sbq.common.response.server_error.ServerErrorSReponse;
import com.sbq.common.response.success.SuccessSReponse;
import com.sbq.tools.NullUtil;
import com.sbq.web.scan.JacksonObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhangyuan on 2017/2/13.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected JacksonObjectMapper jacksonObjectMapper;

    /**
     * 成功
     *
     * @return
     */
    protected Object renderSuccess() {
        return new SuccessSReponse();
    }

    /**
     * 成功，自定义描述
     *
     * @param msg
     * @return
     */
    protected Object renderSuccess(String msg) {
        BaseSResponse response = new SuccessSReponse();
        response.setDescription(msg);
        return response;
    }

    /**
     * 成功，附加返回的Object
     *
     * @param object
     * @return
     */
    protected Object renderSuccess(Object object) {
        BaseSResponse response = new SuccessSReponse();
        response.setResult(object);
        return response;
    }

    /**
     * 成功，附加返回的Object和List,可自定义描述
     *
     * @param object
     * @return
     */
    protected Object renderSuccess(String msg, Object object, List list) {
        BaseSResponse response = new SuccessSReponse();
        if (!NullUtil.isNull(msg)) {
            response.setDescription(msg);
        }
        if (!NullUtil.isNull(object)) {
            response.setResult(object);
        }
        if (!NullUtil.isNull(list)) {
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 成功，附加返回的List
     *
     * @param list
     * @return
     */
    protected Object renderSuccess(List list) {
        BaseSResponse response = new SuccessSReponse();
        response.setResultList(list);
        return response;
    }

    /**
     * 请求有误
     *
     * @return
     */
    protected Object renderRequestError() {
        return new RequestErrorSReponse();
    }

    /**
     * 请求有误，自定义错误描述
     *
     * @param msg
     * @return
     */
    protected Object renderRequestError(String msg) {
        BaseSResponse response = new RequestErrorSReponse();
        response.setDescription(msg);
        return response;
    }

    /**
     * 服务器异常
     *
     * @return
     */
    protected Object renderServerError() {
        return new ServerErrorSReponse();
    }
}
