<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sign in</title>
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
						<input type="text" id="loginIdInput" class="form-control mt-3" placeholder="아이디">
						<input type="password" id="passwordInput" class="form-control mt-3" placeholder="패스워드">

						<button type="button" id="signinBtn" class="btn btn-primary w-100 mt-3">로그인</button>
						<button type="button" id="signupViewBtn" class="btn btn-secondary w-100 mt-3">회원가입</button> <%-- view 이동버튼 --%>
				</div>
		</section>

		<footer class="text-center">
			<span>copyright© myoung.e 2021</span>
		</footer>
	</div>
</body>
</html>