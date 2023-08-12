package com.test.auth.enums;

public enum AppHttpCodeEnum {

    SUCCESS(200, "操作成功"), NEED_LOGIN(403, "没有登录"),
    SYSTEM_ERROR(500, "系统错误"), AUTH_EXPIRE(401, "认证过期"), CXC_AUTH(415, "非法访问?");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
