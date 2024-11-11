<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">


<script>
$(document).ready(function(){
	$('#btnEdit').click(function(){
		location.href = 'boardForm?boSeqNo=' + ${board.bo_seq_no};
	});
	$('#btnDelete').click(function(){
		location.href = 'boardDelete?boSeqNo=' + ${board.bo_seq_no};
	});
});
</script>

<title>Insert title here</title>
</head>
<body>


<div class="container">
	<h2 align="center">게시글 상세보기</h2>
	
		<table class="table">	
			<c:choose>
				<c:when test="${board.bo_open_yn == 'Y' || (not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.mem_id == board.bo_writer )}">		
					<tr>
						<td>제목</td>
						<td>${board.bo_title }</td>
					</tr>
					<tr>
						<td>작성일</td>
						<td>${board.reg_date }</td>
					</tr>
					
					<tr>
						<td>작성자</td>
						<td>${board.bo_writer_name }</td>
					</tr>
					<tr>
						<td>조회수</td>
						<td>${board.bo_hit_cnt }</td>
					</tr>
					<tr>
						<td>공개여부</td>
						<td>${board.bo_open_yn  == 'Y' ? '공개' : '비공개'}</td>
					</tr>
					
					<!-- 첨부파일 -->
					<tr>
						<td>첨부파일</td>
						<td>
							<c:if test= "${board.fileList != null }">
								<c:forEach var="fileItem" items="${board.fileList}">
								
									<div>
										<a href="${pageContext.request.contextPath}/common/download?file_seq_no=${fileItem.file_seq_no}" >${fileItem.file_name}</a>${fileItem.file_fancy_size}
									</div>
									
								</c:forEach>
							</c:if>
								
							<c:if test="${board.fileList eq null }">
								파일이 없습니다.
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td>내용</td>
						<td>${board.bo_content }</td>
					</tr>
			</c:when>
			
				<c:otherwise>
					<tr>
						<td colspan="2">비밀글입니다.</td>				
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		
		<p align="center">
		<c:if test="${not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.mem_id == board.bo_writer}">	
			<input type="button" id="btnEdit" value="수정" class="btn btn-primary" >
			<input type="button" id="btnDelete" value="삭제" class="btn btn-primary">
		</c:if>		
			<input type="button" value="목록" class="btn btn-primary" onclick="location.href='boardList?bo_type=BBS'">
			</p>		
		
</div>

</body>
</html>


