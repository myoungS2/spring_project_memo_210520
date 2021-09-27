<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<div class="d-flex">
		<input type="text" id="loginIdInput" name="loginId" class="form-control mt-3" placeholder="ID">
		<button type="button" id="LoginIdConfirmBtn" class="btn btn-secondary ml-2">중복확인</button>
	</div>
	<input type="password" id="passwordInput" name="password" class="form-control mt-3" placeholder="PASSWORD"> 
	<input type="password" id="passwordConfirmInput" class="form-control mt-3"placeholder="PASSWORD CHECK"> 
	<small id="errorPassword" class="text-danger d-none">비밀번호가 일치하지 않습니다.</small> 
	<input type="text" id="nameInput" name="name" class="form-control mt-3" placeholder="NAME">
	<input type="text" id="emailInput" name="email" class="form-control mt-3" placeholder="E-MAIL">
	<button type="button" id="signupBtn" class="btn btn-secondary mt-3 w-100">SIGN UP</button>
</div>
