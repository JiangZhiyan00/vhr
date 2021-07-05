package com.jiangzhiyan.vhr.exception;

/**
 * 请求参数异常
 */
public class IllegalReqParamsException extends RuntimeException{

    private Integer code=300;
    private String msg="参数异常!";


    public IllegalReqParamsException() {
        super("参数异常!");
    }

    public IllegalReqParamsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public IllegalReqParamsException(Integer code) {
        super("参数异常!");
        this.code = code;
    }

    public IllegalReqParamsException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
