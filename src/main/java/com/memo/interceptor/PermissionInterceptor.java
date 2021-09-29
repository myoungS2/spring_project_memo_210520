package com.memo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.mybatis.logging.LoggerFactory; -> 엉뚱한 임포트
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component // 아무것도 해당하지 않는..스프링빈을 만들때..!
public class PermissionInterceptor implements HandlerInterceptor {
	
	// private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	
	// 1.
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws IOException {
		// 이 요청의 path가 어디인지... => request.getRequestURI();
		logger.info("[### preHandle]" + request.getRequestURI());
		
		// 1. 세션을 가져온다.
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId"); // 로그인이 되어있지 않을 시 null일 수 있기 때문에 (Integer)로 캐스팅
		
		// 2. URL Path 를 가져온다.(어떤 주소로 요청이 온 것 인지)
		String uri = request.getRequestURI();
		
		// 3. 원래의 요청대로 Controller로 가지 못하고, redirect
		
		if (userId != null && uri.startsWith("/user")) {
		// 3-1. 만약 login이 되어있으면(+ /user로 시작) => /post 쪽으로 보낸다.
			response.sendRedirect("/post/post_list_view"); //  로그인 했는데 자꾸 user쪽 화면으로 오려고 할 때 -> post(게시물)로 가!
			return false;
		} else if (userId == null && uri.startsWith("/post")) {
		// 3-2. 만약 login이 안되어있으면(+ /post로 시작) => /user 쪽으로 보낸다.
			response.sendRedirect("/user/sign_in_view");
			return false; // 로그인 안하고 왜 자꾸 post쪽으로 오려고해! -> user(sign in)으로 가!
		}
		
		
		return true; // 무조건 controller 요청 됨
	}
	
	// 2.
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		logger.warn("[### postHandle]" + request.getRequestURI());
	}
	
	// 3.
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			Exception exception) {
		
		logger.error("[### afterCompletion]" + request.getRequestURI()); // catch문에서 error를 찍음
	}
	
}
