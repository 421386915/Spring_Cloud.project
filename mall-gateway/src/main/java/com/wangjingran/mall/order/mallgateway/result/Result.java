package com.wangjingran.mall.order.mallgateway.result;

import com.wangjingran.mallcommon.result.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private  Integer code;
    private  String message;
    private  T data;
    public static <T> Result<T> success(){
        return  success(null);
    }
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }
    public static <T> Result<T> failure(ResultCode resultCode){
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }
    public static <T> Result<T> failure(Integer code,String message){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
