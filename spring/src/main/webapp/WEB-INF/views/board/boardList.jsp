
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<title>Insert title here</title>
<script type="text/javascript">

function goViewpage(boSeqNo){
	location.href = "boardView?boSeqNo="+boSeqNo;
}

function doSearch(page){
	var frm = document.searchForm;	
/* 	if(frm.searchType.value != "" && frm.searchWord.value == ""){
		alert("검색어를 입력하세요");
		return;
	} */
	
	frm.currentPage.value = page;
	frm.action = "boardList?bo_type=BBS";
	frm.submit();
	
}

</script>
</head>
<body>

<div class="container">
	<h2 align="center">게시글 목록</h2>
	
	<p align="right"><input type="button" value="글쓰기" class="btn btn-primary" onclick="location.href='boardForm'"></p>
	
	<p align="center">
		<form name="searchForm" method="post">
			<input type="hidden" name="currentPage" value="${param.currentpage }">
			<select name="searchType">
				<option value="">전체</option>
				<option ${param.searchType == 'title' ? 'selected' : ''} value="title">제목</option>
				<option ${param.searchType == 'contents' ? 'selected' : 'contents'} value="contents">내용</option>
				<option ${param.searchType == 'titleAndContent' ? 'selected' : 'titleAndContent'} value="titleAndContent">제목+내용</option>
				<option ${param.searchType == 'writer' ? 'selected' : 'writer'} value="writer">작성자</option>				
			</select>	
			<input type="text" name="searchWord" size="40" value="${param.searchWord}">
			<input type="button" value="검색" onclick="doSearch(1);">			
		
		<p>
			<label>총 게시글 : ${pagingUtil.totalCount }</label>
			
			<span style="float: right;">
				<label>페이지 사이즈 : </label>
				<select name="pageSize" onchange="doSearch(1)">
						<option ${param.pageSize == '10' ? 'selected' : ''} value="10" >10개</option>
						<option ${param.pageSize == '20' ? 'selected' : ''} value="20" >20개</option>
						<option ${param.pageSize == '50' ? 'selected' : ''} value="50" >50개</option>	
				</select>
			</span>
		</p>		
		
		</form>
		
	</p>
	
	<table class="table table-bordered table-hover">
		<thead>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</thead>
		
		<tbody>
				<c:if test="${not empty boardList}">
				<c:forEach var="board" items="${boardList}">
				<tr onclick="goViewPage(${board.bo_seq_no})">
					<td>${board.bo_seq_no}</td>
					<td>${board.bo_title}</td>
					<td><a href="boardView?boSeqNo=${board.bo_seq_no}">${board.bo_writer_name}</a></td>
					<td>${board.reg_date }</td>
					<td>${board.bo_hit_cnt }</td>
				</tr>
				</c:forEach>
				</c:if>


				<c:if test="${empty boardList}">
				<tr><td colspan="5" align="center">데이터가 존재하지 않습니다.</td></tr>
				</c:if>

		</tbody>
	
	</table>
	
	<!-- 페이지 네이게이션 -->
	
	<div style="text-align: center;">
		<ul class="pagination">
			${pagingUtil.pageHtml}
		</ul>
	</div>
	
	<!-- //페이지 네이게이션 -->

</div>




</body>
</html>