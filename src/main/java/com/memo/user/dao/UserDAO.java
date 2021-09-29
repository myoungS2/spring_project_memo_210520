package com.memo.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.user.model.User;

@Repository
public interface UserDAO {
	// id 중복확인
	public boolean existLoginId(String loginId);
	
	// insert DB
	public void insertUser (
			@Param("loginId") String loiginId, 
			@Param("password")String password, 
			@Param("name")String name, 
			@Param("email")String email);
	
	// select DB
	public User selectUserByLoginIdAndPassword(
			@Param("loginId") String loginId,
			@Param("password")String password);
	
}
