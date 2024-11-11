<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>표</title>
</head>
<body>
<table>
   <tr>
      <th>아이디</th>
      <th>이름</th>
      <th>이메일</th>
   <tr>
<c:forEach var = "member" items="${memberList}">
   <tr>
      <td>${member.mem_id}</td>
      <td>${member.mem_name}</td>
      <td>${member.mem_email}</td>
   <tr>
   </c:forEach>
</table>
</body>
</html>