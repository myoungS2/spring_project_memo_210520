<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
	<table class="table table-hover"> <%-- table-hover: 마우스를 올릴 때 색 변경 --%>
		<thead>
			<tr>
				<th>No.</th>
				<th>제목</th>
				<th>작성일자</th>
				<th>수정일자</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${postList}" var="post"> <%-- items로 꺼낸것을 var에 담는다. --%>
				<tr>
					<td>${post.id}</td>
					<td><a href="/post/post_detail_view?postId=${post.id}">${post.subject}</a></td>
					<td>
						<%-- Date 객체로 내려온 값을 String Format으로 변경해서 출력 --%>
						<fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${post.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" var="updatedAt"/>
						${updatedAt}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="d-flex justify-content-center">
		<c:if test="${prevId ne 0}">
			<a href="/post/post_list_view?prevId=${prevId}" class="mr-3">&lt;&lt; 이전</a> <%-- &lt; == < --%>
		</c:if>
		<c:if text="${nextId ne 0}">
			<a href="/post/post_list_view?nextId=${nextId}" >다음 &gt;&gt;</a> <%-- &gt; == > --%>
		</c:if>
	</div>
	
	<div class="d-flex justify-content-end"> <%-- float를 사용하게되면 밑의 태그들에도 적용됨..!(clearfix를 사용해주어야 적용해제됨) --%>
		<a href="/post/post_create_view" class="btn btn-primary">글쓰기</a> <%-- a태그 버튼처럼 사용=> class="btn" --%>
	</div>
</div>