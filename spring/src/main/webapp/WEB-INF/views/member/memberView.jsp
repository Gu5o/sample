<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">


<title>회원가입</title>

<script type="text/javascript">

</script>

</head>
<body>

<div>

	<!-- <h2 align="center">회원상세정보</h2> -->
		
		<table class="table table-bordered">
		
			<tr>
				<td>순번</td>
				<td>${member.mem_seq_no}</td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>${member.mem_id}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${member.mem_name}</td>
			</tr>
			<tr>
				<td>핸드폰</td>
				<td>${member.mem_phone}</td>
			</tr>
			<tr>
				<td>이메일</td>
				<td>${member.mem_email}</td>
			</tr>
			
			<tr>
            <td>주소</td>
            <td>
               <p>${member.mem_zipcode}</p>
               <p>${member.mem_addr_master}</p>
               <p>${member.mem_addr_detail}</p>
            </td>
         </tr>
			
			
			<tr>
				<td colspan="2">
				<input type="button" value="수정" class="btn btn-default" onclick="location.href='memberForm?seqNoBer=${member.mem_seq_no}'">
					
				<input type="button" value="삭제" class="btn btn-default" onclick="location.href='memberDelete?seqNoBer=${member.mem_seq_no}'">
					
				<input type="button" value="목록" class="btn btn-default" onclick="history.go(-1)">
				</td>
			</tr>
			
		</table>
	
</div>


</body>
</html>
