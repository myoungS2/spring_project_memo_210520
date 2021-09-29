package com.memo.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// 화면 url

@RequestMapping("/user")
@Controller
public class UserController {

	/**
	 * 회원가입 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping("/sign_up_view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/sign_up"); // viewName이 -> section에 들어갈 주소를 내린다.
		return "template/layout";
	}
	
	/**
	 * 로그인 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping("/sign_in_view")
	public String signInView(Model model) {
		model.addAttribute("viewName", "user/sign_in");
		return "template/layout";
	}
	
	
	@RequestMapping("/sign_out")
	public String signOut(HttpServletRequest request) {
		// 로그인 정보를 가져와서-> 없애기
		HttpSession session = request.getSession(); // 
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		session.removeAttribute("userLoginId");
		
		// gnb라서 페이지의 어느곳에서나 로그아웃 가능
		return "redirect:/user/sign_in_view"; // redirect
	}
	
}
