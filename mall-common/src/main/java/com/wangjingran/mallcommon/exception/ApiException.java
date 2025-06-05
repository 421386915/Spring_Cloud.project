package com.wangjingran.mallcommon.exception;
import com.wangjingran.mallcommon.result.ResultCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final Integer code;
    private final ResultCode errorCode;

    public ApiException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.errorCode = errorCode;
    }
    public ApiException(Integer code, String message) {
        super(message);
        this.code = ResultCode.FAILED.getCode();
        this.errorCode = null;
    }
    public ApiException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.errorCode = null;
    }

}
