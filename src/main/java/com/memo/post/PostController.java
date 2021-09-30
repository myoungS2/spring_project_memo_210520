package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); // 에러원인 찾기 쉽게하기 (console창)
	// private Logger logger = LoggerFactory.getLogger(PostController.getClass());
	
	// bo 연결
	@Autowired
	private PostBO postBO;
	
	@RequestMapping("/post_list_view")
	public String postListView(Model model, HttpServletRequest request) {
		// 글 목록들을 가져온다. (로그인 된 아이디가 쓴 글만)
		HttpSession session = request.getSession();
		
		// session 검증
		Integer userId = (Integer)session.getAttribute("userId");
		
		if (userId == null) {
			// 정확한 내용 추적하고 싶다면.
			logger.info("[post_list_view] userId is null. " + userId);
			
			return "redirect:/user/sign_in_view"; // 만약 운이 나쁘게 session에서 로그인이 풀렸다면, 다시 로그인창으로 이동시키기
		}
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		// 모델에 담는다.
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/post_list"); //section부분 바꿔주기
		return "template/layout";
	}
}
