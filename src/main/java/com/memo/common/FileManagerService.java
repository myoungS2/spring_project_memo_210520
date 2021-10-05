package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger; // mybatis x, slf4j o
import org.slf4j.LoggerFactory; // mybatis x, slf4j o
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // 스프링 빈 (bo, dao, controller도 아님)
public class FileManagerService {
	// 로직이 들어간 경우 -> Service라는 이름을 많이 사용..!
	
	// WebMvcConfig도 같이 볼 것 => 실제 저장된 파일과 이미지패스를 매핑해줌 (*)
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// was에 url을 만들어내고, 그 url과 우리컴퓨터에 있는 이미지파일 -> 맵핑
	// 1. 실제 이미지가 저장될 경로 (public -> 다른곳에서도 저장. final, static -> 수정 x)
	public final static String FILE_UPLOAD_PATH = "D:\\심미영_웹개발_210520\\6_spring_project\\ex\\memo_workspace\\images/";
	
	/**
	 * save file
	 * @param loginId
	 * @param file
	 * @return
	 * @throws IOException
	 */
	// 2. 파일을 받아서 String(url) return -> 내 컴퓨터상의 url
	public String saveFile(String loginId, MultipartFile file) throws IOException {
		// 3. 파일 디렉토리 경로 -> 사용자별로 다른 폴더를 갖게...!(파일명이 겹치지 않게하기 위해서, loginId와 현재시간을 경로에 붙여준다.) 
		// ex) marobiana_162099585780(current time)/apple.png   
		String directoryName = loginId + "_" + System.currentTimeMillis() + "/" ; 
		String filePath = FILE_UPLOAD_PATH + directoryName; // 이 경로에 만들 것이다..!
		
		// 4. 찐 폴더 만들기
		File directory = new File(filePath); // 변수 必
		// mkdir(make directory) -> boolean값으로 리턴
		if (directory.mkdir() == false ) {
			logger.error("[파일업로드] 디렉토리 생성 실패 " + directoryName + ", filePath" + filePath); // false -> 폴더만들기 실패
			return null; // 실패했기 때문에 null값을 리턴
		}
		
		// 5. 파일 업로드(byte 단위로 업로드 된다.)
		byte[] bytes = file.getBytes(); // 예외처리 위로 던지기~!~!
		// Path path = Paths.get(filePath + file); // 어디에 어떤 이름으로 올릴건지 (파일 경로 + MutipartFile로 가져온 file명)
		Path path = Paths.get(filePath + file.getOriginalFilename()); // input에 올릴 파일명이다.
		// 진짜로 내 컴퓨터에 쓰기..!
		Files.write(path, bytes);
		
		// 6. 이미지 URL path를 리턴한다.
		// ex) http://localhost/images/qwer_164545623243/apple.png -> 이것을 주소창에 치면 이미지가 나와야함..!
		return "/images/" + directoryName + file.getOriginalFilename(); // test 시 null값 리턴했었음..!
	} 
	
	/**
	 * delete file
	 * @param imagePath
	 * @throws IOException 
	 */
	public void deleteFile(String imagePath) throws IOException{
		// 파라미터 값 : ex) /images/qwer_164545623243/apple.png
		// 실제 경로 : ex) D:\\심미영_웹개발_210520\\6_spring_project\\ex\\memo_workspace\\images/
		// 실제경로 + 파라미터 => images가 겹치게 됨 한쪽(파라미터쪽) /images/를 제거해줌
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", "")); 
		if (Files.exists(path)) {
		 // 파일이 존재하면 삭제한다.
			Files.delete(path); // exception -> 위로던지기~
		}
		
		// 디렉토리 삭제
		path = path.getParent(); // 그림의 부모->디렉토리
		if (Files.exists(path)) {
		// 디렉토리가 존재하면 삭제한다.
			Files.delete(path);
		}
	}

}