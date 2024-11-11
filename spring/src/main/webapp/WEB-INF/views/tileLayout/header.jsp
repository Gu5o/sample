<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>

</head>
<body>
<!--header.jsp  -->
<h1>Spring Sample</h1>

	<c:if test="${sessionScope.LOGIN_USER != null }">
		<div align="right">
			${LOGIN_USER.mem_name }(${LOGIN_USER.mem_id })님 환영합니다.
		</div>	
	</c:if>

</body>
</html>