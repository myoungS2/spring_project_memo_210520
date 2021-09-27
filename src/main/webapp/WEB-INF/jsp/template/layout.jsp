<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMO 게시판</title>
<!-- bootstrap CDN link -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<!-- AJAX를 사용하기 위해 slim 아닌 풀버전의 jquery로 교체 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<!-- css 연결 -->
<link rel="stylesheet" type="text/css" href="/static/css/style.css"> <!-- application.yml에 새로운 설정을 해주었기 때문에 기존과 다르게 /static/을 포함 -->
</head>
<body>
	<div id="wrap" class="container">
		<header class="bg-warning">
			<jsp:include page="../include/gnb.jsp" /> <!-- ../ : 현재위치에서 하나위의 부모에게로 올라간다 (상대경로), 맨앞에 /가 있다면 (절대경로) -->
		</header>
			
		<section class="contents"> <!-- 동적으로 달라지는 부분 -->
			<jsp:include page="../${viewName}.jsp" />
		</section>
		
		<footer class="bg-warning">
			<jsp:include page="../include/footer.jsp" />
		</footer>
	</div>
</body>
</html>