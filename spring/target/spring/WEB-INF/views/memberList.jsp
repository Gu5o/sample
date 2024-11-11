<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">

<title>회원목록</title>
</head>
<body>

	<div>
		<p align="right">
			<input type="button" value="회원가입" class="btn btn-default">
		</p>

		<!-- 검색폼 -->
		<p align="center">
		<form name="searchForm" method="post">
			<select name="searchType">
				<option value="">전체</option>
				<option value="id">아이디</option>
				<option value="name">이름</option>
			</select> <input type="text" size="20" name="searchWord" value=""> <input
				type="button" value="검색">

			<p>
				<label style="float: left;">총 게시글 : 30</label> <span
					style="float: right;"> <label>페이지 사이즈 : </label> <select
					name="pageSize">
						<option value="10">10개</option>
						<option value="20">20개</option>
						<option value="50">50개</option>
				</select>
				</span>
			</p>
		</form>
		</p>
		<!-- //검색폼 -->

		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th>순번</th>
					<th>아이디</th>
					<th>이름</th>
					<th>핸드폰</th>
					<th>이메일</th>
				</tr>
			</thead>

			<tbody>


				<tr>
					<td>1</td>
					<td>ID1</td>
					<td><a href="#">이름1</a></td>
					<td>전화번호1</td>
					<td>이메일1</td>
				</tr>



				<tr>
					<td colspan="5" align="center">데이터가 존재하지 않습니다.</td>
				</tr>


			</tbody>
		</table>

	</div>

</body>
</html>