package com.example.mub.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

/*@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("loginMember") == null) {
			log.info("미인증 사용자 요청");
			
			Enumeration<String> parameterNames = request.getParameterNames();
			StringBuffer stringBuffer = new StringBuffer();
			while (parameterNames.hasMoreElements()) {
				String parameterName = parameterNames.nextElement();
				stringBuffer.append(parameterName + "=" + request.getParameter(parameterName) + "&");
			}
			
			response.sendRedirect("/member/login?redirectURL=" + requestURI + "?" + stringBuffer.toString());
			return false;
		}
		
		return true;
	}
}
*/
