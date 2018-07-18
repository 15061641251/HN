package com.sbq.common.response.request_error;

import com.sbq.common.ServicesCode;
import com.sbq.common.response.BaseSResponse;

/**
 * Created by zhangyuan on 2017/2/14.
 */
public class RequestErrorSReponse extends BaseSResponse {

    public RequestErrorSReponse() {
        this.setReturnCode(ServicesCode.REQUEST_ERROR.code);
        this.setDescription(ServicesCode.REQUEST_ERROR.msg);
    }
}
