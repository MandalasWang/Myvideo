package com.djcps.djvideo.config;

import com.djcps.djvideo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author 有缘
 * @date 19/5/21
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String [] str1 = {"/user/api/v1/*/**"};
        String [] str2 = {"/user/api/v1/userRegister","/user/api/v1/userLogin"};
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(str1).excludePathPatterns(str2);
        WebMvcConfigurer.super.addInterceptors(registry);

    }


}
