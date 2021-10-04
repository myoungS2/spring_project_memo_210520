package com.memo.post.bo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	// dao 연결
	@Autowired
	private PostDAO postDAO;
	
	// FMS 연결
	@Autowired 
	private FileManagerService fileManagerService;
	
	// select DB
	public List<Post> getPostListByUserId(int userId) {
		return postDAO.selectPostListByUserId(userId); 
	}
	
	// insert DB
	public int createPost(int userId, String loginId, String subject, String content, MultipartFile file) {
		String imagePath = null; // 로직으로 null값을 변환시킬 것..!(이미지가 있는 경우에만..!) userLoginId는 DB에 저장하진 않음..!
		
		if (file != null) {
			// url 생성 -> BO가 하지않고, 또다른...-> 이미지를 처리할 수 있는 role을 가진..클래스..! 를 불러와서 변수(imgPath)에 담기
			try {
				imagePath = fileManagerService.saveFile(loginId, file);
			} catch (IOException e) {
				imagePath = null;  // null값 넣어주기..! (에러나도 큰 문제x)
			} // imagePath변수에 담기 , import -> 위로 던질것인가/ try catch
		}
		
		return postDAO.insertPost(userId, subject, content, imagePath);
		// return 1; test
	}
}
