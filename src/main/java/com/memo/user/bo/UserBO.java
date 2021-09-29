package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.dao.UserDAO;
import com.memo.user.model.User;

@Service
public class UserBO {
	@Autowired
	private UserDAO userDAO;
	
	// id 중복확인
	public boolean existLoginId(String loginId) { // 0이 아닌 수는 모두 true -> 중복
		return userDAO.existLoginId(loginId); 
	}
	
	// insert DB
	public void addUser(String loiginId, String password, String name, String email) {
		userDAO.insertUser(loiginId, password, name, email);
	}
	
	// select DB
	public User getUserByLoginIdAndPassword(String loginId, String password) {
		return userDAO.selectUserByLoginIdAndPassword(loginId, password);
	}
}
