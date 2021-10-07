package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {
	// bo 연결
	@Autowired
	private PostBO postBO;
	
	/**
	 * 글 입력
	 * @param subject
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam(value="content", required= false) String content,
			@RequestParam(value="file", required= false) MultipartFile file,
			HttpServletRequest request) {
		//  보내는 쪽과 맵핑이 잘 안되면(parameter,...etc) -> 400대 에러 
		
		// session에서 유저 id를 가져온다.
		HttpSession session = request.getSession(); // post_create에서 ajax가 잘 작동되면 여기로 들어오게 됨..!(breakPoint)
		Integer userId = (Integer) session.getAttribute("userId"); // 어디에 session을 넣었는지 잘 확인하기(UserRestController)
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "error");
		
		// insert DB(BO가..) -> 유저id, 유저 loginId, subject, content, file(url화 -> 로직 -> BO에서)
		int row = postBO.createPost(userId, userLoginId, subject, content, file);
		if (row > 0) {
			// 성공
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		// 결과값 response 
		return result;
	}
	
	/**
	 * 글 수정
	 * @param postId
	 * @param subject
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PutMapping("/update") // put = 수정 이라는 늒힘..!
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("userLoginId");
				
		// update DB
		postBO.updatePost(postId, loginId, subject, content, file);
		
		// 결과값 response -> 여기까지 도달했다는 것은 success라는 뜻이므로, 그냥 성공 내려주기
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
	
	/**
	 * 글 삭제
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId){
		
		// delete DB
		postBO.deletePost(postId);
	
		
		// 결과 return
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
}















