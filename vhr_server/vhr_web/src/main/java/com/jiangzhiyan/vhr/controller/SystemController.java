package com.jiangzhiyan.vhr.controller;

import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/config")
@Api("系统设置相关接口")
public class SystemController {

    @Resource
    private MenuService menuService;

    @GetMapping("/menu")
    @ApiOperation("获取登录验证通过的用户拥有的菜单")
    public ResponseBean loadMenusByHrId(){
        return ResponseBean.success(menuService.loadMenusByHrId());
    }
}
