<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sign up</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<!--  Ajax를 쓰려면 jquery slim버전이 아닌 더 많은 함수가 있는 jquery js사용 -->
  <script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/memo_style.css">
</head>
<body>
	<div id="wrap">
		<header>
			<span class="display-4"><b>메모 게시판</b></span>
		</header>

		<section class="contents d-flex justify-content-center align-items-center">
			<div>
				<div class="d-flex">
					<input type="text" id="loginIdInput" name="loginId" class="form-control mt-3" placeholder="아이디">
					<button  type="button" id="LoginIdConfirmBtn" class="btn btn-secondary ml-2">중복확인</button>
				</div>
				<input type="password" id="passwordInput" name="password" class="form-control mt-3" placeholder="패스워드">
				<input type="password" id="passwordConfirmInput" class="form-control mt-3" placeholder="패스워드 확인">
				<small id="errorPassword" class="text-danger d-none">비밀번호가 일치하지 않습니다.</small>
				<input type="text" id="nameInput" name="name" class="form-control mt-3" placeholder="이름">
				<input type="text" id="emailInput" name="email" class="form-control mt-3" placeholder="이메일">

				<button type="button" id="signupBtn" class="btn btn-secondary mt-3 w-100">회원가입</button>
			</div>
		</section>

		<footer class="text-center">
			<span>copyright© myoung.e 2021</span>
		</footer>
	</div>	
</body>
</html>