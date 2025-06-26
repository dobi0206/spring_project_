<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 상대경로 / root 경로..? (localhost:8080)-->
<link rel = "stylesheet" type = "text/css"  href = "${pageContext.request.contextPath}/resources/css/common/header.css">

</head>
<body>
	<div id = "header">
		<div style = "color:white; text-align:left; folat:left; cursor:pointer" onclick="location.href=`${pageContext.request.contextPath}/`">
			CanesBlack
		</div>
	
		<c:choose>
			<c:when test ="${isAuthenticated != null && isAuthenticated == true}">
				<div style = "float:right;">
					<a href = "${pageContext.request.contextPath}/logout" style="color:#C0C0C0; margin-right:15px;text-decoration:none;font-size:15px;">로그아웃</a>
				</div>
			</c:when>
			<c:otherwise>
				<div style = "float:right;">
				<a href = "${pageContext.request.contextPath}/loginPage" style="color:#C0C0C0; margin-right:15px;text-decoration:none;font-size:15px;">로그인</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	
</body>
</html>