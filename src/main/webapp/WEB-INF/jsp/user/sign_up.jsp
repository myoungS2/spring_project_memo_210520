<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<form id="signUpForm" method="post" action="/user/sign_up">
		<div class="d-flex">
			<input type="text" id="loginIdInput" name="loginId" class="form-control mt-3" placeholder="ID">
			<button type="button" id="LoginIdConfirmBtn" class="btn btn-secondary ml-2">중복확인</button>
		</div>
		
		<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
		<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
		<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
		
		<input type="password" id="passwordInput" name="password" class="form-control mt-3" placeholder="PASSWORD"> 
		<input type="password" id="passwordConfirmInput" class="form-control mt-3"placeholder="PASSWORD CHECK"> 
		<small id="errorPassword" class="text-danger d-none">비밀번호가 일치하지 않습니다.</small> 
		<input type="text" id="nameInput" name="name" class="form-control mt-3" placeholder="NAME">
		<input type="text" id="emailInput" name="email" class="form-control mt-3" placeholder="E-MAIL">
	
		<button type="button" id="signupBtn" class="btn btn-secondary mt-3 w-100">SIGN UP</button>
	</form>
</div>

<script>
	$(document).ready(function() {
		// 아이디 중복확인
		$('#LoginIdConfirmBtn').on('click', function(e){
			// loginId 값 가져오기
			let loginId = $('input[name=loginId]').val().trim();
			// 값 잘 가져와지는지 체크
			// alert(loginId);
			
			// idCheckLength, idCheckDuplicated, idCheckOk 3개의 상태값
			// 1. id가 4 미만인 경우
			if (loginId.length < 4) {
				$('#idCheckLength').removeClass('d-none'); //  경고 문구 노출
				$('#idCheckDuplicated').addClass('d-none'); 
				$('#idCheckOk').addClass('d-none'); 
				
			}
			
			// 2. id 중복여부 확인 -> ajax 서버 호출
			$.ajax({
				// request info
				type: 'get'
				, url: '/user/is_duplicated_id' // api -> view화면x (주의!!!)
				, data: {"loginId":loginId}
				// 응답값
				, success: function(data){
					 alert(data.result); // true or false 
					
					if (data.result) {
						// 중복이다.
						$('#idCheckLength').addClass('d-none'); 
						$('#idCheckDuplicated').removeClass('d-none'); // 경고 문구 노출
						$('#idCheckOk').addClass('d-none'); 
					} else {
						// 중복이 아니면 => 가능
						$('#idCheckLength').addClass('d-none'); 
						$('#idCheckDuplicated').addClass('d-none'); 
						$('#idCheckOk').removeClass('d-none'); // 문구 노출
					}
				} // success close
				
				, error: function(e) {
					alert("아이디 중복확인에 실패했습니다. 관리자에게 문의해주세요.")
				} //  error close
				
			}); // ajax close
			
		}); // LoginIdConfirmBtn close	
		
		// signupBtn
		$('#signupBtn').on('click', function() {
			let loginId = $('#loginIdInput').val().trim();
			if (loginId == '') {
				alert("아이디를 입력하세요.");
				return;
			}
			let password = $('#passwordInput').val();
			let confirmPassword = $('#passwordConfirmInput').val();
			if (password == '' || confirmPassword == ''){
				alert("비밀번호를 입력하세요.");
				return;
			}
			
			// password check
			if (password != confirmPassword) {
				alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
				// 다 지워버리기
				$('#passwordInput').val('');
				$('#passwordConfirmInput').val('');
				return;
			}
			
			let name = $('#nameInput').val().trim();
			if (name == '') {
				alert("이름을 입력하세요.");
				return;
			}
			
			let email = $('#emailInput').val().trim();
			if (email == '') {
				alert("이메일을 입력하세요.");
				return;
			}
			
			// id 중복확인이 완료되었는지 확인
			// #idCheckOk가 <div> 클래스에 d-none이 없으면 중복확인 완료
			if ($('#idCheckOk').hasClass('d-none')) {
				alert("아이디 중복확인을 해주세요.")
				return;
			}
			
			// 서버에 보낼 준비 완..!
			
			// 서버에 요청
			let url = $('#signUpForm').attr('action'); // 오늘의 에러 2 :  signUpForm -> signUPForm로..입력해서 error...ㅠ 복붙을 생활화하자..!
			// alert(url);
			let data = $('#signUpForm').serialize(); // serialize : 도메인객체(model)에 있는 필드-> String 구성하는 것..? (다른서버한테 필드에 있는 값을 보낼 때 사용함)
			console.log(data);		
			// 폼 태그에 있는 데이터를 한번에 보낼 수 있게 구성한다. 그렇지 않으면 json으로 구성해야한다.
			
			$.post(url, data)
			.done(function(data) {
				if (data.result == 'success'){
					alert("가입을 환영합니다!! 로그인 해주세요.");
					location.href = '/user/sign_in_view';
				} else { // ajax 통신은 성공, 로직상 실패
					alert("가입에 실패했습니다.");					
				}
			}); // post ajax close
			
			
			
			/* form 태그를 사용했을 경우 -> 새로운 유형 post ajax (jquery post함수 찾아볼 것!)
			$.post(url, data)
			.done(function(data) {
				
			}); // post ajax close
			*/
			
		}); // signupBtn close
		
		
		
	}); // document close


</script>