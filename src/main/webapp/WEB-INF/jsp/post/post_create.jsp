<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="d-flex justify-content-center">
	<div class="w-50">
		<%-- test2:1234(pw) 까먹지말긔...ㅠ --%>
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력해주세요."> <!-- form태그 없이, ajax사용할 예정 -->
		<textarea id="content" rows="10" cols="100" class="form-control" placeholder="내용을 입력해주세요."></textarea>
		<%-- form-control대신 form-comtrol해서 안먹었었음..제발 정신차려어 --%>
		<div class="d-flex justify-content-end">
			<input type="file" id="file" accept=".jpg,.jpeg,.png,.gif"> <%-- accept=".jpg,..." : img파일만 업로드할 수 있게 -> 나중에 script를 통해 검증 必 --%>
		</div>
		
		<div class="clearfix"> <%-- float를 사용하고, 1차 상위 부모에 clearfix를 해야함 --%>
			<a href="/post/post_list_view" class="btn btn-dark float-left">목록</a>
			
			<div class="float-right">
				<button type="button" id="clearBtn" class="btn btn-secondary">모두지우기</button>
				<button type="button" id="saveBtn" class="btn btn-primary">저장</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 모두지우기 버튼 클릭
		$('#clearBtn').on('click', function(){
			// 제목(input)과 내용(textarea) 영역을 빈칸으로 만든다.
			if (confirm("모든 내용을 지우시겠습니까?")) {
				// true -> 지워지기
				$('#subject').val('');
				$('#content').val('');
			} // false -> 그대로
		}); // clearBtn close
		
		// 저장 버튼 클릭 (클라이언트..! 리퀘스트 문서를 완성-> // 서버에 보내고 서버에서는 업로드..!)
		$('#saveBtn').on('click', function(){
			// 제목 가져오기
			let subject = $('#subject').val().trim();  // val, vla로...제발..오타그만..멈춰
			console.log(subject); // 잘 가져와지는지 확인
			if (subject == ''){
				alert("제목을 입력해주세요.");
				return;
			}
			
			// 내용 가져오기
			let content = $('#content').val();
			console.log(content); // 잘 가져와지는지 확인
			if (content == ''){
				alert("내용을 입력해주세요.");
				return;
			}
			
			// file 가져오기(이미지)
			// 파일이 업로드 된 경우에 확장자 검사
			let file = $('#file').val(); // 파일명(경로) -> 바이너리 파일x
			console.log("file:" + file);
			
			if (file != '') {
				// 1.  파일명을 .을 기준으로 split -> 배열에 저장
				// 확인 : console.log(file.split('.'));
				// 2. pop : 마지막값(확장자) 빼내기 <> push
				let ext = file.split('.').pop().toLowerCase() // 3. toLowerCase : 빼낸 값을 몽땅 소문자로 변경해서 값 비교하기/ 4. ext 변수에 담기
				// 5. if문
				if ($.inArray(ext,['jpg', 'jpeg', 'png', 'gif']) == -1) {
					// 배열(허용하고자하는 확장자가 들어있음)에 들어있냐 -> 참이면, 잘못들어 온 확장자 
					alert("잘못된 파일 형식입니다.");
					// 잘못 된 파일 형식 -> 비워주기
					$('#file').val('');
					return;
				}
			}
			
			// java script에서 동적인 (가상의)폼태그 만들기..!
			let formData = new FormData();  // 변수가 아닌 객체..! (자바스크립트에서 제공해주는 객체(=object))
			// 객체에 데이터(제목/내용) 넣기 -> key:value쌍으로
			formData.append('subject', subject);
			formData.append('content', content);
			//  객체에 데이터(이미지파일 = 진짜 바이너리파일..!) 넣기 -> key:value쌍으로
			formData.append('file', $('#file')[0].files[0]); // input태그를 통째로 가져오기/ [0] -> 첫번째것..왜인지는 모름 / .files[0] -> 여러파일 중 첫번째파일..(multiple이라는 어트리뷰트 추가시..!)
			
			// ajax -> request를 위한 설정..!
			$.ajax({
				type:'post' // file이기 때문에 무조건 post로..! get은 url로 데이터를 구성하는데..어..말이안됨..(ㅇㅁㅇ..아무튼 안됨)
				, url: '/post/create'
				, data: 'formData' // 만들어 둔 formData객체를 통째로..!
				// 업로드 시 -> 필수 파라미터가 있어야함(아래 3개)
				, enctype: 'multipart/form-data'
				, processData: false // boolean -> flase/ true로 할 경우 url이 쿼리스트링으로..말이 안돼!! 아무튼!!
				, contentType: false 
				// sucess와 error 밑에 쓰기!
				, success: function(data) {
					if (data.result == 'success'){
						alert('메모가 저장되었습니다.');
						// 링크걸기
						
					}
				}	
				, error: function(e) {
					alert('메모 저장에 실패했습니다.' + e);
				}
				
				
			}); // ajax close
		
		
		}); // saveBtn close
		
	}); // document close

</script>