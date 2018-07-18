package com.sbq.common.response.success;

import com.sbq.common.ServicesCode;
import com.sbq.common.response.BaseSResponse;

/**
 * Created by zhangyuan on 2017/2/14.
 */
public class SuccessSReponse extends BaseSResponse {

    public SuccessSReponse() {
        this.setReturnCode(ServicesCode.SUCCESS.code);
        this.setDescription(ServicesCode.SUCCESS.msg);
    }
}
