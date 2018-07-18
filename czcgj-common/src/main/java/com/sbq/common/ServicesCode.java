package com.sbq.common;

public enum ServicesCode {


    SUCCESS(1, "成功"),
    REQUEST_ERROR(0, "请求有误"),
    SERVER_ERROR(-1, "服务器异常"),
    AUTH_ERROR(0, "权限不足");

    ServicesCode(int code, String msg) {
        this.msg = msg;
        this.code = (byte) code;
    }

    public final byte code;
    public final String msg;

    public static ServicesCode toEnum(int code) {
        for (ServicesCode errorCode : values()) {
            if (errorCode.code == code) {
                return errorCode;
            }
        }
        return SERVER_ERROR;
    }
}
