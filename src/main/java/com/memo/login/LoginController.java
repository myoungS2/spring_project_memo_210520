package com.memo.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	// 1. 로그인 화면
		// 요청 url : localhost/user/sign_in_view
		@RequestMapping("/user/sign_in_view1")
		public String sign_in_view() {
			return "memo/signInView";
		}

		// 2. 회원가입 화면
		// 요청 url : localhost/user/sign_up_view
		@RequestMapping("/user/sign_up_view1")
		public String sign_up_view() {
			return "memo/signUpView";
		}

}
