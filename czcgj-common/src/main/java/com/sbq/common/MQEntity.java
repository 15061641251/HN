package com.sbq.common;

import java.util.Map;

/**
 * MQ通用实体类
 * Created by zhangyuan on 2017/3/22.
 */
public class MQEntity {

    /**
     * Constants.TIP_SMS-短信；
     * Constants.TIP_SYSTEM-推送；
     * Constants.TIP_EMAIL-短信
     */
    private int type;

    /**
     * 自定义扩展属性
     */
    private Map<String, Object> ext;

    public int getType() {
        return type;
    }

    public MQEntity setType(int type) {
        this.type = type;
        return this;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public MQEntity setExt(Map<String, Object> ext) {
        this.ext = ext;
        return this;
    }
}
