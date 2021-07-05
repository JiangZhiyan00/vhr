package com.jiangzhiyan.vhr.utils;

import com.jiangzhiyan.vhr.exception.IllegalReqParamsException;
import com.jiangzhiyan.vhr.exception.ServerException;

/**
 * 异常断言工具类
 * @author JiangZhiyan
 */
public class AssertExceptionUtil {

    private AssertExceptionUtil(){}

    /**
     * 请求参数不合法异常断言
     * @param flag 为ture:则抛出IllegalReqParamsException
     * @param msg 并携带指定的异常信息
     */
    public static void isTrue(Boolean flag,String msg){
        if(flag){
            throw  new IllegalReqParamsException(msg);
        }
    }

    /**
     * 服务器内部异常断言
     * @param flag 为ture:则抛出ServerException
     */
    public static void isTrue(Boolean flag){
        if (flag){
            throw new ServerException();
        }
    }
}

