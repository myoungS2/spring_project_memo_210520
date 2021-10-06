package com.memo.post.bo;

import java.io.IOException;
import java.util.Collections;
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
	
	// 한 페이지에 몇개의 글을 가져올 것인가 정의하기
	private static final int POST_MAX_SIZE = 3;
	
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
	public List<Post> getPostList(int userId, Integer prevId, Integer nextId) {
		
		// 페이징을 위한 로직
	
//		10 9 8 / 7 6 5 / 4 3 2 / 1		
//		
//		1 / 2 3 4 / 5 6 7 / 8 9 10
//		(7 6 5 페이지가 기준일 때)
//		
//		이전 클릭: 7보다 큰 3개 -> 오름차순 8 9 10                 -> 조건문 
//		               코드에서 역순으로 변경 10 9 8
//		다음 클릭: 5보다 작은 3개 -> 내림차순 4 3 2                 -> 조건문
//			코드에서 변경 없이 4 3 2

		// 어떤 페이지 상태인지를 담을 변수 -> 방향
		String direction = null; // null || next || prev (3가지 방향 있음)
		
		// 어떤 id값을 담을 변수 -> 페이지 기준이 되는 수
		Integer standardId = null; 
		
		if(prevId != null) {
			// "이전"이 클릭
			direction = "prev"; // direction의 값이 oo이면~
			standardId = prevId;
			
			// 7보다 큰 3개  => 8 9 10 reverse 시켜야 한다. => 10 9 8
			List<Post> postList = postDAO.selectPostList(userId, direction, standardId, POST_MAX_SIZE);
			Collections.reverse(postList); // Collections는 return x, 그냥 reverse하고 끝
			return postList;
			
		} else if (nextId != null) {
			// "다음"이 클릭
			direction = "next";
			standardId = nextId;
		}
		
		return postDAO.selectPostList(userId, direction, standardId, POST_MAX_SIZE); 
		
	}
	
	// 다음 기준으로 마지막 페이지 인가?
	public boolean isLastPage(int userId, Integer nextId) {
		// 오름차순으로 limit 1 제일 작은 값을 가져와서 nextId와 비교 -> 같다면, 마지막 페이지
		return nextId == postDAO.selectIdByUserIdAndSort(userId, "ASC");
		
	}
	
	// 이전 기준으로 첫번째 페이지 인가?
	public boolean isFirstPage(int userId, Integer prevId) {
		// 내림차순으로 limit 1 제일 큰 값을 가져와서 prevId와 비교 -> 같다면, 첫번째 페이지
		return prevId == postDAO.selectIdByUserIdAndSort(userId, "DESC");
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
	
	
	
	public void deletePost(int postId) {
		// postId로 post를 가져온다.
		Post post = getPost(postId);
		
		// 무작정 이미지 파일 가져오지 않고, null 검사 먼저 -> null인데 가져오려고하면, 에러가 날 수 있음
		if (post == null) {
			logger.error("[delete post] 삭제할 게시물이 없습니다. {}", postId);
			return;
		}
		
		// 그림이 있으면 삭제한다.
		String imagePath = post.getImagePath();
		if (imagePath != null) {
			try {
				fileManagerService.deleteFile(imagePath); // fms에 이미지를 지워달라고 요청
			} catch (IOException e) {
				logger.error("[delete post] 그림 삭제 실패 postId: {}, path: {}", postId, imagePath); // 그림 삭제를 실패하더라도, post는 지울 것..! (return x)
			} 
		}
		
		// post를 삭제한다.
		postDAO.deletePost(postId);
	}
	
	
}
