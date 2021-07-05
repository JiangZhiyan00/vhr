package com.jiangzhiyan.vhr.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 校验验证码的过滤器
 * @author JiangZhiyan
 */
@Component
public class VerifyCodeFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //只验证登录请求
        if (HttpMethod.POST.name().equals(req.getMethod()) && "/doLogin".equals(req.getServletPath())){
            String loginCode = req.getParameter("code");
            String verifyCode = (String) req.getSession().getAttribute("verifyCode");
            if (StringUtils.isBlank(loginCode) || !loginCode.equalsIgnoreCase(verifyCode)){
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.print(new ObjectMapper().writeValueAsString(ResponseBean.error("验证码填写错误")));
                out.flush();
                out.close();
            }else {
                chain.doFilter(req,resp);
            }
        }else {
            chain.doFilter(req,resp);
        }
    }
}
