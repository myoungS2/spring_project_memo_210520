package com.memo.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
	// id 중복확인
	public boolean existLoginId(String loginId);
	
	// insert DB
	public int insertUser (
			@Param("loginId") String loiginId, 
			@Param("password")String password, 
			@Param("name")String name, 
			@Param("email")String email);
}
