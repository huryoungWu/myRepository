package com.example.demo.config;

import com.example.demo.interceptor.LoginInterceptor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                // 哪些路径不拦截
                .excludePathPatterns("api/user/login","/error");
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] path = {
                "/api/**"
        };
        String[] excludePath = {

        };
        registry.addInterceptor(loginInterceptor()).addPathPatterns(path).excludePathPatterns(excludePath);
    }



    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
}

