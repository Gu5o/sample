<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
 
<title>Insert title here</title>

</head>
<body>
<div class="container">
	<div>
		<tiles:insertAttribute name="header" />
	</div>
	
	<div class="navbar navbar-default">
		<tiles:insertAttribute name="menu"/>
	</div>
	<!--article-->
	<div class="container-fiuid" style="min-height: 400px;">
		<tiles:insertAttribute name="body"/>
	</div>
	<!-- /article -->
	
	<div class="container-fluid text-center"
		style="background-color: #555; color:white; padding: 15px;">
		<tiles:insertAttribute name="footer"/>
	</div>		 
</div>
</body>
</html>