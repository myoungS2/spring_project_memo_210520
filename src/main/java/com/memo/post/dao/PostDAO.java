package com.memo.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.model.Post;

@Repository
public interface PostDAO {
	
	// select DB
	public List<Post> selectPostListByUserId(int userId);
	
	// insert DB
	public int insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
}
