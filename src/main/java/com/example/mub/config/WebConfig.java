package com.example.mub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.mub.interceptor.LoginCheckInterceptor;
import com.example.mub.repository.MemberMapper;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	private String[] excludePaths = {"/","/member/join","/member/login","/member/logout","/member/update",
			"/**.css", "/**.js", "/**.ico", "/**error", "/**.scss"};
	
	private String[] loginCheckAddPaths = {"/member/update"};
	
	private String[] artistCheckAddPaths = {};
	
	private String[] adminCheckAddPaths = {};
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())
				.order(1)
				.addPathPatterns(loginCheckAddPaths);
				
	}
}