package com.wangjingran.mall.order.mallgateway.result;

import lombok.Getter;

@Getter
public enum ResultCode {  // Changed from class to enum
    SUCCESS(200, "成功"),
    FAILED(500, "失败"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "请求的资源不存在");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
