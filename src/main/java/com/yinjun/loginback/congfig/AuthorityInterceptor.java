package com.yinjun.loginback.congfig;

import com.yinjun.loginback.entity.SysUserEntity;
import com.yinjun.loginback.req.SysUserLoginReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    private static final Set<String> NOT_INTERCEPT_URI = new HashSet<>();//不拦截的URI

    static {
        NOT_INTERCEPT_URI.add("/sys-user/login");
        NOT_INTERCEPT_URI.add("/sys-user/register");
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

//
//         System.out.println("经过了拦截器");
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//支持跨域请求
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
//        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token

        String uri = request.getRequestURI();
        if (NOT_INTERCEPT_URI.contains(uri)) {
            log.info("不拦截" + uri);
            return true;
        }
        log.info("拦截" + uri);
        HttpSession session = request.getSession();
//        SysUserLoginReq sysUserLoginReq = (SysUserLoginReq) session.getAttribute("user_info_in_the_session");
        if (session.getAttribute("user_info_in_the_session") == null) {
            throw new RuntimeException("用户未登陆");
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv) throws Exception {

        System.out.println("controller 执行完了");

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行
     * （主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {

        System.out.println("我获取到了一个返回的结果："+response);
        System.out.println("请求结束了");

    }

}
