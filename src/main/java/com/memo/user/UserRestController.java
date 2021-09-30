package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.model.User;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	// BO 연결
	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복확인 체크
	 * @param lodinId
	 * @return 
	 */
	@RequestMapping("/is_duplicated_id")
	public Map<String, Boolean> isDubplicatedId(
			@RequestParam("loginId") String lodinId) {
		// 로그인 아이디 중복 여부 -> DB 조회
		
		// 중복 여부에 대한 결과 Map 생성
		// ajax 동작여부 체크용
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", userBO.existLoginId(lodinId));
		
		// return Map
		return result;
		
	}
	/**
	 * 회원가입
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// 비밀번호 -> 해싱(암호화가 되지만 복호화는 안됨)
		String encryptPassword = EncryptUtils.md5(password);
		
		// insert DB
		userBO.addUser(loginId, encryptPassword, name, email);
		
		
		// 응답값 생성 후 리턴
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
	/**
	 * 로그인
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	
	@PostMapping("/sign_in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// 파라미터로 받은 비밀번호를 해싱한다.
		String encryptPassword = EncryptUtils.md5(password);
		
		// DB select <- id와 해싱 된 pw로
		User user = userBO.getUserByLoginIdAndPassword(loginId, encryptPassword); // null -> 로그인 실패
		
		Map<String, Object> result = new HashMap<>();
		// 있으면 로그인 성공 -> 로그인 상태를 담아놓는 기능 = 세션
		if (user != null) {
			result.put("result", "success");
			
			// 로그인 처리 - 세션에 저장 (로그인 상태 유지)
			HttpSession session = request.getSession();
			// 담고싶은 정보 담기
			session.setAttribute("userId", user.getId()); // map과 비슷 (key:value)
			session.setAttribute("userName", user.getName());
			session.setAttribute("userLoginId", user.getLoginId()); 
			// key로 value 꺼내기 -> jsp에서는 ${userName}  하면 바로 꺼내올 수 있음 유무로 로그인여부 알 수 있음 -> jstl if문 써서 로그인/로그아웃 시 페이지 나눠주기 가넝
		} else {
			// 없으면 로그인 실패
			result.put("result", "error");
		}
		
		
		// 결과 리턴
		return result;
	}
}
