package com.testP.configuration;

import com.testP.interceptor.LoginRequiredInterceptor;
import com.testP.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(passportInterceptor);//拦截器加入顺序很重要
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");//表示拦截的是哪些路径
        super.addInterceptors(registry);
    }

}
