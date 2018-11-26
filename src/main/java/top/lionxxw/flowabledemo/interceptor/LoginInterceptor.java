package top.lionxxw.flowabledemo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.constant.AppConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * token登陆拦截器

 * created on 2017/11/13 19:24
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Component
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final static List<String> ignoreUrl = Arrays.asList(new String[]{"/login","/logout","/error"});

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("requestTime", System.currentTimeMillis());
        String uri = request.getRequestURI();
        log.info("**********uri:{} **********", uri);
        if (ignoreUrl.contains(uri)){
            return true;
        }
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        if (null == loginUser){
            response.sendRedirect("/login");
            return false;
        }
        request.setAttribute("name", loginUser.getUserName());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String uri = request.getRequestURI();
        if (ex != null) {
            log.error("请求 ：" + uri + " 异常，Exception：{}", ex);
        }
        String requestTime = String.valueOf(request.getAttribute("requestTime"));
        Long useTime = System.currentTimeMillis() - Long.parseLong(requestTime);
        log.info("请求 ：{}结束，耗时【{}】", uri, useTime);
        if (useTime > 5000) {
            log.info("请求：{}耗时超过5秒，实际耗时{}", uri, useTime);
        }
    }
}
