<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%-- ${post} --%> <%-- com.memo.post.model.Post@32b949c5 들어있는 값 --%>

 <div class="d-flex justify-content-center">
	<div class="w-50">
		<%-- test2:1234(pw) 까먹지말긔...ㅠ --%>
		<input type="text" id="subject" class="form-control" value="${post.subject}"> <!-- 모델에 담아져있는 post를 el표현식으로 가져오기 -->
		<textarea id="content" rows="10" cols="100" class="form-control">${post.content}</textarea>
		
		<div class="d-flex justify-content-end">
			<input type="file" id="file" accept=".jpg,.jpeg,.png,.gif"> 
			<%-- accept=".jpg,..." : img파일만 업로드할 수 있게 -> 나중에 script를 통해 검증 必(validation) --%>
		</div>
		
		<%-- 이미지가 있을 때만 이미지 영역 추가 --%>
		<c:if test="${not empty post.imagePath}">
			<div>
				<img src="${post.imagePath}" alt="업로드 이미지" width="200" class="mb-3">
			</div>
		</c:if>
		
		<div class="clearfix"> <%-- float를 사용하고, 1차 상위 부모에 clearfix를 해야함 --%>
			<button id="deleteBtn" class="btn btn-secondary float-left">삭제</button>
			
			<div class="float-right">
				<button id="listBtn" class="btn btn-dark">목록으로</button>
				<button type="button" id="saveBtn" class="btn btn-primary" data-post-id="${post.id}">수정</button> <%-- data-카멜케이스x! -기호를 단어사이에 넣기 --%>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 목록으로 버튼 클릭 -> 목록으로 이동
		$('#listBtn').on('click', function() {
			location.href = "/post/post_list_view";	
		}); // listBtn close
		
		// 글 수정
		$('#saveBtn').on('click', function() {
			
			// 제목 입력 확인
			let subject = $('#subject').val().trim();	
			if (subject == '') {
				alert("제목을 입력해주세요.");
				return;
			}
			// 내용 입력 확인
			let content = $('#content').val();	
			if (content == '') {
				alert("내용을 입력해주세요.");
				return;
			}
			
			// 파일 확인 -> 업로드시 확인하고 싶다면 #file-> change로 이벤트를 잡아서 검사도 가능!
			let fileName = $('#file').val(); 
			if (fileName != ''){
				// 파일 有 -> 확장자 체크
				let fileArr = fileName.split('.');
				let ext = fileArr.pop().toLowerCase(); // 확장자
				if ($.inArray(ext, ['gif','png','jpg','jpeg']) == -1 ){
					// -1이 true라면 잘못 된 확장자
					alert("잘못된 파일 형식입니다.");
					$('#file').val(''); // 잘못 된 형식의 확장자를 비워준다.
					return;
				}
			
			}
			
			// 어떤 글이 수정되는지에 대한 정보도 함께..!(<>create는 그냥 새로 만드는 것이기 때문에 필요한 파라미터만 넣어주면 됨@)
			let postId = $(this).data('post-id'); 
			console.log("postId:" + postId);
			
			
			// 폼태그를 자바스크립트에서 만든다.
			let formData = new FormData();
			formData.append('postId', postId); // requestParam으로 넘어가는 key이름과 들어있는 값
			formData.append('subject', subject); 
			formData.append('content', content); 
			formData.append('file', $('#file')[0].files[0]);  
			
			
			// ajax 통신으로 서버에 전송한다.
			$.ajax({
				type: 'put' // 서버에서 putMapping으로 받기
				, url: '/post/update'
				, data: formData
				// 시리얼 라이즈는 모든 데이터가 String일때만 가능..!
				, enctype: 'multipart/form-data' //  파일 업로드를 위한 필수 설정 1
				, processData: false //  파일 업로드를 위한 필수 설정 2 (글자x)
				, contentType: false //  파일 업로드를 위한 필수 설정 3 (글자x)
				, success: function(data) {
					if (data.result == 'success') {
						alert("메모가 수정되었습니다.");
						location.reload(true); // 새로고침 (true)는 생략가능!
					}				
				}
				, error: function(e) {
					alert("메모 수정에 실패했습니다. 관리자에게 문의해주세요." + e);
				}
			}); // ajax close
			
			
			
		}); // saveBtn close 
		
	}); // document close

</script>