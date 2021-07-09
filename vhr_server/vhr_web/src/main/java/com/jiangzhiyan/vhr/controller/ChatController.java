package com.jiangzhiyan.vhr.controller;

import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.HrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JiangZhiyan
 */
@Api("在线聊天")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private HrService hrService;

    @ApiOperation("/查询除了当前登录的hr之外所有的hr")
    @GetMapping("/hrs")
    public ResponseBean getAllHrsExceptCurrent(){
        return hrService.getAllHrsExceptCurrent();
    }
}
