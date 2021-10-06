package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	/**
	 * 글 목록 화면 + 페이징
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/post_list_view")
	public String postListView(
			@RequestParam(value="prevId", required = false) Integer prevIdParam, // 클라이언트에서 보낸 값을 받음(어떤 버튼을 눌렀는지) 파라미터로 받은 값, Param 붙혀주기
			@RequestParam(value="nextId", required = false) Integer nextIdParam,
			Model model, HttpServletRequest request) {
		// 글 목록들을 가져온다. (로그인 된 아이디가 쓴 글만)
		HttpSession session = request.getSession();
		
		// session 검증
		Integer userId = (Integer)session.getAttribute("userId");
		
		if (userId == null) {
			// 정확한 내용 추적하고 싶다면.
			logger.info("[post_list_view] userId is null. " + userId);
			
			return "redirect:/user/sign_in_view"; // 만약 운이 나쁘게 session에서 로그인이 풀렸다면, 다시 로그인창으로 이동시키기
		}
		
		List<Post> postList = postBO.getPostList(userId, prevIdParam, nextIdParam); // BO한테 prevIdParam,nextIdParam 같이 넘겨서 값 가져오게하기
		
		int prevId = 0;
		int nextId = 0;
		// 만약 postList가 null(비어있는 것x)일 때, .isEmpty를 하면 -> 에러가 남..!
		if (CollectionUtils.isEmpty(postList) == false) {
			prevId = postList.get(0).getId(); // 가장 왼쪽의 값을 보내주어야 함
			nextId = postList.get(postList.size() -1).getId(); // 가장 오른쪽의 값
			
			// 이전이나 다음이 없는 경우 0으로 세팅한다. (jsp에서 0인지 검사)
			
			// 마지막 페이지 (다음 기준) 인 경우 0으로 세팅한다.
			if (postBO.isLastPage(userId, nextId)) {
				nextId = 0;
			}
			
			// 첫번째 페이지인 경우 (이전 기준) 0으로 세팅한다.
			if (postBO.isFirstPage(userId, prevId)){
				prevId = 0;
			}
		}
			
		// 모델에 담는다.
		model.addAttribute("prevId", prevId); 
		model.addAttribute("nextId", nextId); 
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/post_list"); //section부분 바꿔주기
		return "template/layout";
	}
	
	/**
	 * 글 쓰기 화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 세션에 userId가 없으면 로그인 하는 페이지로 이동(redirect) -> intercepter가 있기 때문에 필요하지 않지만, marondal에서는..! 사용해주면 좋음
			return "redirect:/user/sign_in_view"; 
		}
		
		model.addAttribute("viewName", "post/post_create"); // 여기에 주소도 입력안해놓고 요청해서 400에러뜨게하지말라...
		return "template/layout";
	}
	
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(
			// 무슨 글을 클릭하고 들어왔는지..를 넘겨주어야 함 -> post의 id값이 파라미터로 필요
			@RequestParam("postId") int postId,
			Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 세션에 userId가 없으면 로그인 하는 페이지로 이동(redirect) -> intercepter가 있기 때문에 필요하지 않지만, marondal에서는..! 사용해주면 좋음
			return "redirect:/user/sign_in_view"; 
		}
		
		// postId에 해당하는 게시물을 가져와서 model에 담는다. -> 그리고 jsp로 보낸다.(model에 담아서)
		Post post = postBO.getPost(postId);
		
		model.addAttribute("post",post);
		model.addAttribute("viewName", "post/post_detail");
		return "template/layout";  
	}
	
}
