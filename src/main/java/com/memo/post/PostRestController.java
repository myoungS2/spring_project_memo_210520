package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
}
