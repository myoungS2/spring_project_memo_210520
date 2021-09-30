package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	// dao 연결
	@Autowired
	private PostDAO postDAO;
	
	// select DB
	public List<Post> getPostList(int userId) {
		return postDAO.selectPostList(userId); 
	}
}
