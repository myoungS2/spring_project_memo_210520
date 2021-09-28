package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;

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
	
}
