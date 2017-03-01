package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;
import com.taotao.util.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    // 在handler执行之前处理
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        // 返回值决定handler是否执行
        // 判断用户是否登陆，从cookie中取token，根据token获取用户信息，调用sso系统的接口。
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        // 根据token换取用户信息，调用sso系统的接口。
        TbUser user = userService.getUserByToken(token);
        // 取不到用户信息
        if (user == null) {
            // 跳转到登录页面，把用户请求的url作为参数传递给登录页面。
            response.sendRedirect(
                    userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN
                            + "?redirect=" + request.getRequestURL());
            return false;
        }

        // 取到用户信息，放行
        // 返回值决定handler是否执行。true：执行，false：不执行。
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // handler执行之后，返回ModelAndView之前

    }

    // 返回modelAndView之后，响应用户操作
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
