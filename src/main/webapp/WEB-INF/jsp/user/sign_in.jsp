<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="login-box">
	<form id="loginForm" action="/user/sign_in" method="post">
		<div class="input-group">
			<div class="input-group-prepend">
				<span class="input-group-text">ID</span>
			</div>			
			<input type="text" class="form-control" id="loginId" name="loginId">					
		</div>
		
		<div  class="input-group">
			<div class="input-group-prepend">
				<span class="input-group-text">PW</span>
			</div>			
			<input type="password" class="form-control mt-2" id="password" name="password">	
		</div>
		
		<input type="submit" class="btn btn-primary btn-block mt-3" value="로그인"> <%-- btn-block -> 버튼 블록화 (한행 다 차지) --%>	
		<a href="/user/sign_up_view" class="btn btn-secondary btn-block">회원가입</a> <%-- a태그로 화면전환 -> sign_up_view --%>
	</form>

</div>

<script>
	$(document).ready(function(){
		// loginForm이 submit 될 때
		$('#loginForm').submit(function(e){
			e.preventDefault(); // submit 자동 수행 중단 -> 404로 넘어가지 않으면 잘 중단된 것!
			
			// validation
			let loginId = $('input[name=loginId]').val().trim();
			
			if (loginId == ''){
				alert("아이디를 입력해주세요.");
				return false;
			}
			let password = $('input[name=password]').val();
			
			if (password == ''){
				alert("비밀번호를 입력해주세요.");
				return false;
			}
			
			let url = $(this).attr('action'); // this = loginForm
			let data = $(this).serialize(); // 쿼리스트링으로 name 값들을 구성하고 request body(post방식)에 넣어 보낸다. 
			console.log("url:" + url);
			console.log("data:" + data); // 오늘의 에러 2 :  console -> colsole로..입력해서 error...ㅠ
			
			$.post(url, data)
			.done(function(data) {
				if (data.result == 'success'){ // data중에 key명이 result인것의 value가 success일 때..!
					location.href= '/post/post_list_view';  // 화면이동 시 복잡한 데이터를 들고있을 경우 ajax가 아닌 submit으로 처리하는 것이 낫다.
				} else {
					alert("로그인에 실패했습니다. 다시 시도해주세요.")
				}
			}); // post.done close
			
		}); // loginForm submit close
	}); // document close

</script>