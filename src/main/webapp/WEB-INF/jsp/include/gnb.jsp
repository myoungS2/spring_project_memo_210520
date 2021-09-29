<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  <%-- 요런애들은 페이지마다 있어야함 --%>  
<div class="d-flex justify-content-between"><%-- between: 완전 끝과 끝, around: 어느정도 끝부분 --%>
	<div class="logo d-flex align-items-center">
		<h1 class="font-weight-bold p-4">메모 게시판</h1>
	</div>
	<div class="login-info d-flex align-items-center mr-3">
		<%-- 세션 정보다 있는 경우에만 출력 --%>
		<c:if test="${not empty userId}" >
			<span class="font-weight-bold">${userName}님 안녕하세요.</span><%-- 나중에 로그인정보 불러와서 사용자이름 부분 채울 것 => el표현식 ${userName}--%> 
			<a href="/user/sign_out" class="font-weight-bold">로그아웃</a>
		</c:if>
	</div>
</div>