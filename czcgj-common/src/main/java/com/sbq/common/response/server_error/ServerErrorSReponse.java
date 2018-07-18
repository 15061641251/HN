package com.sbq.common.response.server_error;

import com.sbq.common.ServicesCode;
import com.sbq.common.response.BaseSResponse;

/**
 * Created by zhangyuan on 2017/2/14.
 */
public class ServerErrorSReponse extends BaseSResponse {

    public ServerErrorSReponse() {
        this.setReturnCode(ServicesCode.SERVER_ERROR.code);
        this.setDescription(ServicesCode.SERVER_ERROR.msg);
    }
}
