package com.jiangzhiyan.vhr.exception;

/**
 * 服务器内部错误异常类
 * @author JiangZhiyan
 */
public class ServerException extends RuntimeException{

    private Integer code=500;
    private String msg="服务器异常,请稍后重试...";

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
