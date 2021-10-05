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
	
	// select DB (선택 된 게시물 하나만 가져오기)
	public Post selectPost(int id);
	
	
	// update DB
	public void updatePost(
			@Param("id") int id,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
			
}
