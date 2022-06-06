package com.uqierp.config;

import com.uqierp.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Autowired
	AccessInterceptor accessInterceptor;//自定义拦截器
	
	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(accessInterceptor);
		interceptorRegistration.excludePathPatterns("/img/**");
		interceptorRegistration.excludePathPatterns("/js/**");
		interceptorRegistration.excludePathPatterns("/login");
        interceptorRegistration.addPathPatterns("/**");
	}
}
