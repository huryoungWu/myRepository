package com.example.demo.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.Utils.JwtUtil;
import com.example.demo.Utils.TokenUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class LoginInterceptor implements HandlerInterceptor {


    //Controller逻辑执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle....");
        String uri = request.getRequestURI();
        System.out.println("当前路径"+uri);




        /**
         * HandlerMethod=>Controller中标注@RequestMapping的方法
         *  需要配置静态资源不拦截时，添加这块逻辑  => 前后端分离项目
         */
        /*if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token=request.getHeader("token");

        if (!TokenUtil.verify(token)) {
            // 未登录跳转到登录界面
            throw  new RuntimeException("no login!");
        } else {
            return true;
        }*/
        return true;


            //放行登录请求
            /*if (request.getRequestURI().contains("/user/login")) {
                return true;
            }

            //取出请求头中的token
            String token = request.getHeader("token");
            System.out.println("token: " + token);
            if (StringUtils.isEmpty(token)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }

            //解析token
            try {
                JwtUtil.parseJWT(token);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                e.printStackTrace();
                return false;
            }
            return true;*/
        }



    //Controller逻辑执行完毕但是视图解析器还未进行解析之前
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle....");
    }

    //Controller逻辑和视图解析器执行完毕
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion....");
    }
}

