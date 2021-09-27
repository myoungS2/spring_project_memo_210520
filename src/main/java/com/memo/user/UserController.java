package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// 화면 url

@RequestMapping("/user")
@Controller
public class UserController {
	
	
	@RequestMapping("/sign_up_view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/sign_up"); // viewName이 -> section에 들어갈 주소를 내린다.
		return "template/layout";
	}
	
}
