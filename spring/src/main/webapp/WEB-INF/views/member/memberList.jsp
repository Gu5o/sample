<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">

<script>

function goViewpage(memSeqNo){
	location.href = "memberView?memSeqNo="+memSeqNo;
}

function doSearch(page){
	var frm = document.searchForm;	
	if(frm.searchType.value != "" && frm.searchWord.value == ""){
		alert("검색어를 입력하세요");
		return;
	}
	
	frm.currentPage.value = page;
	frm.action = "memberList";
	frm.submit();
	
}

</script>


<title>회원목록</title>
</head>
<body>

	<div>
		<!-- 
		<p align="right">
			<input type="button" value="회원가입" class="btn btn-default" onclick="location.href='memberForm'">
		</p>
		-->
		<!-- 검색폼 -->
		<p align="center">
		<form name="searchForm" method="post">
		<input type = "hidden" name="currentPage" value="${param.currentPage}"/>
			<select name="searchType">
				<option value="">전체</option>
				<option ${param.searchType == 'id' ? 'selected' : ''} value="id">아이디</option>
				<option ${param.searchType == 'name' ? 'selected' : ''} value="name">이름</option>
			</select> <input type="text" size="20" name="searchWord" value="">
			<input type="button" value="검색" onclick="doSearch(1);">

			<p>
				<label style="float: left;">총 게시글 : ${pagingUtil.totalCount}</label>

				<span style="float: right;"> <label>페이지 사이즈 : </label>
				<select name="pageSize" onchange="doSearch(1)">
						<option ${param.pageSize == '10' ? 'selected' : ''} value="10" >10개</option>
						<option ${param.pageSize == '20' ? 'selected' : ''} value="20" >20개</option>
						<option ${param.pageSize == '50' ? 'selected' : ''} value="50" >50개</option>	
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
				<c:if test="${not empty memberList}">
				<c:forEach var="member" items="${memberList}">
				<tr onclick="goViewPage(${member.mem_seq_no})">
					<td>${member.mem_seq_no}</td>
					<td>${member.mem_id}</td>
					<td><a href="memberView?memSeqNo=${member.mem_seq_no}">${member.mem_name}</a></td>
					<td>${member.mem_phone}</td>
					<td>${member.mem_email}</td>
				</tr>
				</c:forEach>
				</c:if>


				<c:if test="${empty memberList}">
				<tr><td colspan="5" align="center">데이터가 존재하지 않습니다.</td></tr>
				</c:if>

			</tbody>
		</table>
	</div>
	
	<div style = "text-align:center;">
		<ul class=pagination>
			${pagingUtil.pageHtml}
		</ul>
	</div>




</body>
</html>