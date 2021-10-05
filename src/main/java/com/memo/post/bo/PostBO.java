package com.memo.post.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	// logger 
	private Logger logger = LoggerFactory.getLogger(PostBO.class);
	
	// dao 연결
	@Autowired
	private PostDAO postDAO;
	
	// FMS 연결
	@Autowired 
	private FileManagerService fileManagerService;
	
	/**
	 * select DB
	 * @param userId
	 * @return
	 */
	public List<Post> getPostListByUserId(int userId) {
		return postDAO.selectPostListByUserId(userId); 
	}
	
	/**
	 * insert DB
	 * @param userId
	 * @param loginId
	 * @param subject
	 * @param content
	 * @param file
	 * @return
	 */
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
	
	// select DB (선택 된 게시물 하나만 가져오기)
	public Post getPost(int postId) {
		// ByPostId() 생략 (pk로 select DB하는 것은 너무 당연하니까..)
		
		return postDAO.selectPost(postId);
	}
	
	/**
	 * update DB
	 * @param postId
	 * @param loginId
	 * @param subject
	 * @param content
	 * @param file
	 */
	public void updatePost(int postId, String loginId, String subject, String content, MultipartFile file) {
		// String loginId는 실제 DB에 넣진 x
		
		// 글이 없다면..!
		// 검증-> postId로 select DB -> 내가 수정하려는 글이 있는지 여부를 확인 -> 글이 없다면..! logging
		Post post = getPost(postId); //  기존 이미지 파일이 들어있는 경로를 가져오기 위해..서 겸사겸사..! (삭제해야하기 때문에..!)
		if (post == null) {
			logger.error("[글 수정] post is null. postId:{}", postId); // 첫번째 파라미터가 {}안에 들어가게 됨 {}와 , 뒤의 파라미터 개수를 맞춰주면 여러개 사용 가능
			return;
		}
		
		// file이 있으면 업로드 후 imagePath를 얻어와야함.
		String imagePath = null;
		if (file != null) {
			// 파일 업로드
			try {
				imagePath = fileManagerService.saveFile(loginId, file);
				
				// 기존에 있던 파일 제거 -> 일단 imagePath가 존재(업로드 성공) && 기존에 파일이 있으면 
				if(imagePath != null && post.getImagePath() != null) {
					// 업로드가 실패할 수 있으므로, 업로드 성공 후 제거
					fileManagerService.deleteFile(post.getImagePath());
				}
			} catch (IOException e) {
				
			} 
			
			
		}
		
		// update DB
		postDAO.updatePost(postId, subject, content, imagePath);
		
	}
	
	
	
	
	
}
