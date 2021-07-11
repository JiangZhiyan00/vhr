package com.jiangzhiyan.vhr.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangzhiyan.vhr.filter.AccessDecisionFilter;
import com.jiangzhiyan.vhr.filter.GetRequiredRolesOfUrlFilter;
import com.jiangzhiyan.vhr.filter.VerifyCodeFilter;
import com.jiangzhiyan.vhr.model.Hr;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.HrService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private HrService hrService;

    /**
     * 校验验证码正确性的过滤器
     */
    @Resource
    private VerifyCodeFilter verifyCodeFilter;

    /**
     * 获取当前请求所需权限的过滤器
     */
    @Resource
    private GetRequiredRolesOfUrlFilter getRequiredRolesOfUrlFilter;

    /**
     * 决策是否有权访问的过滤器
     */
    @Resource
    private AccessDecisionFilter accessDecisionFilter;

    /**
     * 密码加密器bean
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 会话实例注册表bean
     */
    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 基于自定义的UserDetailsService的身份验证
     * HrService实现了UserDetailsService接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    /**
     * 定义不需要拦截的请求地址
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/getVerifyCode",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        );
    }

    /**
     * 登录验证及登出
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //在验证用户名和密码前验证验证码
        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);

        //表单提交
        http.formLogin()
                /*//登录提交的参数,默认username,password
                .usernameParameter("username")
                .passwordParameter("password")
                //自定义登陆页面,默认/login
                .loginPage("/login")*/
                //登录提交地址
                .loginProcessingUrl("/doLogin")
                //验证成功执行的方法:将用户信息返回前端
                .successHandler((req, resp, authentication) -> {
                    //authentication.getPrincipal():返回被认证或认证后的用户对象
                    Hr hr = (Hr) authentication.getPrincipal();
                    //将返回的用户信息中的password去掉
                    hr.setPassword(null);
                    ResponseBean responseBean = ResponseBean.success("登录成功", hr);
                    printResInfoAsJson(resp, responseBean);
                })
                //验证失败执行的方法
                .failureHandler((req, resp, exception) -> {
                    ResponseBean responseBean = ResponseBean.error("服务器异常,登陆失败");
                    if (exception instanceof AccountExpiredException) {
                        responseBean.setMsg("账号过期,登录失败");
                    } else if (exception instanceof LockedException) {
                        responseBean.setMsg("账号被锁定,登录失败");
                    } else if (exception instanceof CredentialsExpiredException) {
                        responseBean.setMsg("密码过期,登录失败");
                    } else if (exception instanceof DisabledException) {
                        responseBean.setMsg("账号被禁用,登录失败");
                    } else if (exception instanceof BadCredentialsException) {
                        responseBean.setMsg("用户名或密码错误,请重新输入");
                    }
                    printResInfoAsJson(resp, responseBean);
                });
        //设置同一用户最大的会话数量为1(即只能有一处地方登录),前提:用户对象需要实现hashCode和equals方法
        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());

        //注销登录
        http.logout()
                //注销成功执行的方法
                .logoutSuccessHandler((req, resp, authentication) -> {
                    ResponseBean responseBean = ResponseBean.success("注销成功");
                    printResInfoAsJson(resp, responseBean);
                }).permitAll();

        //授权认证
        http.authorizeRequests()
                //所有请求必须用户登录后才能访问
                //.anyRequest().authenticated()
                //将获取当前请求所需权限的拦截器和决策用户是否能够访问当前请求的拦截器配置到验证执行链中
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(getRequiredRolesOfUrlFilter);
                        object.setAccessDecisionManager(accessDecisionFilter);
                        return object;
                    }
                });

        //禁用CSRF
        http.csrf().disable();

        //没有认证时，在这里处理结果，不要重定向
        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401
                    ResponseBean responseBean = ResponseBean.error("访问失败");
                    if (authException instanceof InsufficientAuthenticationException) {
                        responseBean.setMsg("尚未登录,请重新登录");
                    }
                    printResInfoAsJson(response, responseBean);
                });

        //同一用户多处登录过滤器
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
            HttpServletResponse resp = event.getResponse();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String nowTime = sdf.format(new Date());
            ResponseBean responseBean = ResponseBean.error
                    (nowTime + " 此账号在别处登录,您被强制下线,如果不是您的操作,请尽快修改密码!");
            printResInfoAsJson(resp, responseBean);
        }), ConcurrentSessionFilter.class);
    }

    private void printResInfoAsJson(HttpServletResponse resp, Object result) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String resInfo = new ObjectMapper().writeValueAsString(result);
        out.print(resInfo);
        out.flush();
        out.close();
    }
}
