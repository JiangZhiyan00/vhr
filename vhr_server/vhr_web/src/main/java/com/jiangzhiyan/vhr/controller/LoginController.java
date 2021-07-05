package com.jiangzhiyan.vhr.controller;

import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.utils.GenerateVerifyCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@Api("登录页相关接口")
public class LoginController {

    @GetMapping("/login")
    @ApiOperation("未登录时访问需要登录的资源,重定向到此接口并返回提示信息")
    public ResponseBean login(){
        return ResponseBean.error("尚未登录,请先登录");
    }

    @GetMapping(value = "/getVerifyCode",produces = "image/jpeg")
    @ApiOperation("登录页验证码")
    public void getVerifyCode(@ApiIgnore HttpServletResponse resp,@ApiIgnore HttpSession session) throws IOException {
        GenerateVerifyCode code = new GenerateVerifyCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        //将验证码文字储存到session中
        session.setAttribute("verifyCode",text);
        GenerateVerifyCode.output(image,resp.getOutputStream());
    }
}
