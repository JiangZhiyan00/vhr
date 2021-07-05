package com.jiangzhiyan.vhr.handler;

import com.jiangzhiyan.vhr.exception.IllegalReqParamsException;
import com.jiangzhiyan.vhr.exception.ServerException;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理类
 * @author JiangZhiyan
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 数据库操作异常处理
     */
    @ExceptionHandler(SQLException.class)
    public ResponseBean sqlException(SQLException e){
        //外键约束异常
        if (e instanceof SQLIntegrityConstraintViolationException){
            return ResponseBean.error("该数据存在关联数据,操作失败");
        }
        return ResponseBean.error("数据库异常,操作失败");
    }

    @ExceptionHandler(IllegalReqParamsException.class)
    public ResponseBean illIllegalReqParamsException(IllegalReqParamsException e){
        return ResponseBean.error(e.getMsg());
    }

    @ExceptionHandler(ServerException.class)
    public ResponseBean serverException(ServerException e){
        return ResponseBean.error(e.getMsg());
    }
}
