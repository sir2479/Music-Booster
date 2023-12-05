package com.example.mub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.mub.repository.MemberMapper;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	private String[] excludePaths = {"/","/member/join","/member/login","/member/logout",
			"/*.css", "/*.js", "/*.ico", "/error"};
	
	
}
