<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
welcome : Member

<hr>
<!-- 만약 로그인 상태라면 request.userPrincipal 에 사용자의 로그인 정보가 들어있다. -->
<c:if test="${not empty pageContext.request.userPrincipal }">
<p> is Log-In</p>
</c:if>

<!-- 만약 로그아웃 상태라면 request.userPrincipal 에 사용자의 로그인 정보가 들어있지 않다. -->
<c:if test="${empty pageContext.request.userPrincipal }">
<p> is Log-Out</p>
</c:if>

USER ID : ${pageContext.request.userPrincipal.name}<br/>
<a href="/logout">Log Out</a> <br />

</body>
</html>