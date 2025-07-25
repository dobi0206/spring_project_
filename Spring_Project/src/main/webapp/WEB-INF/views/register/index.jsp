<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<link rel = "stylesheet" type = "text/css"  href = "${pageContext.request.contextPath}/resources/css/register/style.css">
</head>
<body>
	<%@ include file = "/WEB-INF/views/common/header.jsp" %>
	
	<div id = 'register-container-wrapper'>
		<div class = 'register-container'>
			<h2>회원가입</h2>
			<form action="${pageContext.request.contextPath}/register" method = "post"> <!--  해당 주소로 데이터 전송 -->
			<!-- CSRF토큰 추가 -->
			<input type = "hidden" name="_csrf" value="${_csrf.token}">
			
			<div class = "input-group">
				<label for = "username">아이디</label>
				<input type = "text" id="username" name="username" required>
			</div>
			<div class = "input-group">
				<label for = "password">비밀번호</label>
				<input type = "password" id="password" name="password" required>
			</div>
			<div class = "input-group">
				<label for = "writer">작성자</label>
				<input type = "text" id="writer" name="writer" required>
			</div>			
				<div class = "input-group">
					<button type="submit" class = "register-button">회원가입</button>
				</div>
				<div class ="login-link">
					<a href = "${pageContext.request.contextPath}/loginPage">이미 계정이 있으신가요?</a>
				</div>
			</form>
		</div>
	</div>
	
	
	<%@ include file = "/WEB-INF/views/common/footer.jsp" %>
</body>
</html>