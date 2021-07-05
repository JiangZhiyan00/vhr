package com.jiangzhiyan.vhr.responseData;

public class ResponseBean {

    private Integer statusCode;
    private String msg;
    private Object result;

    public static ResponseBean success(Object result){
        return new ResponseBean(200,result);
    }

    public static ResponseBean success(String msg){
        return new ResponseBean(200,msg,null);
    }

    public static ResponseBean success(String msg,Object result){
        return new ResponseBean(200,msg,result);
    }

    public static ResponseBean error(String msg){
        return new ResponseBean(500,msg,null);
    }

    public static ResponseBean error(String msg,Object result){
        return new ResponseBean(500,msg,result);
    }

    private ResponseBean() {
    }

    private ResponseBean(Integer statusCode, Object result) {
        this.statusCode = statusCode;
        this.result = result;
    }

    private ResponseBean(Integer statusCode, String msg, Object result) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.result = result;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
