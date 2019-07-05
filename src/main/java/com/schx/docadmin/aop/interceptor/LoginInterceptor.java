package com.schx.docadmin.aop.interceptor;

import com.schx.docadmin.aop.annotation.Login;
import com.schx.docadmin.aop.annotation.Menu;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录拦截器
 * @author: xutao
 * @create: 2019-06-13 14:17
 **/
@Service
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {//只拦截方法
            Login signAnnotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
            //请求的方法被标记了@Login注解,并且请求的参数不为空
            if (!ObjectUtils.isEmpty(signAnnotation)) {//需要对cookie进行验证
                System.out.println("验证cookie-------------------------");
            }
            Menu menuAnotation= ((HandlerMethod) handler).getMethodAnnotation(Menu.class);
            if (!ObjectUtils.isEmpty(menuAnotation)) {//菜单验证
                System.out.println("menuAnotation："+menuAnotation.value());
            }
        }
        return true;
    }
}
