package com.jiangzhiyan.vhr.controller;

import com.jiangzhiyan.vhr.model.Hr;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.HrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author JiangZhiyan
 */
@Api("个人中心")
@RestController
@RequestMapping("/hr/info")
public class HrInfoController {

    @Autowired
    private HrService hrService;

    @ApiOperation("从security中获取当前登录的hr信息")
    @GetMapping("/")
    public ResponseBean getCurrentHrInfo(@ApiIgnore Authentication authentication){
        return ResponseBean.success(authentication.getPrincipal());
    }

    @ApiOperation("更新个人资料")
    @PutMapping("/")
    public ResponseBean updateCurrentHrInfo(@RequestBody Hr hr,@ApiIgnore Authentication authentication){
        hrService.updateCurrentHrInfo(hr,authentication);
        return ResponseBean.success("个人资料更新成功");
    }

    @ApiOperation("修改密码")
    @PutMapping("/updatePassword")
    public ResponseBean updatePassword(@RequestBody Map<String,Object> passwordInfo){
        hrService.updatePassword(passwordInfo);
        return ResponseBean.success("密码修改成功,系统即将退出...");
    }

    @ApiOperation("更改头像")
    @PostMapping("/updateUserFace")
    public ResponseBean updateUserFace(MultipartFile file,@ApiIgnore Authentication authentication){
        hrService.updateUserFace(file,authentication);
        return ResponseBean.success("头像更新成功");
    }
}
