package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	// 어떤 interceptor를 쓸 것인지 Autowired로 불러온다.
	@Autowired
	private PermissionInterceptor interceptor;
	
	@Override // .으로 계속 설정 추가 가능
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**")/* 어떤 주소로 들어왔을 때 상기 인터셉터를 태울 것인가 -> /** 모든 디렉토리 확인(손주를 포함) */
		.excludePathPatterns("/user/sign_out", "/static/**", "/error"); /* 인터셉터 예외 path 설정 -> 로그아웃, static하위 파일(그냥 일반 파일이기 때문), 에러 발생 시 */
	}
	
	// images 저장
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**") // http://localhost//images/qwer_2389283923/alien.png -> 를 넣어줌
		.addResourceLocations("file:///D:\\심미영_웹개발_210520\\6_spring_project\\ex\\memo_workspace\\images/"); // 실제 파일 저장 위치  (=file:///(윈도우일때..!)FileManagerService.FILE_UPLOAD_PATH/)
	}
}
