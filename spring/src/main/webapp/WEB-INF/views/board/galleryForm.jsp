<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<html>
<head>

<script src="${pageContext.request.contextPath }/resources/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jquery.validate.min.js"></script>

<script>
$(document).ready(function(){
	$("#bo_title").focus(); //화면이 실행이되고 제목에 커서 놓기
	
	
	function checkFiles(){
		
		var maxSize = 10485760; // 10mb, 파일의 최대 사이즈
		
		if(!$(".btn-delete-file").length){ //이미지가 없는 경우
			if (!$("input=name[uploadFiles]").length || !$("input[name='uploadFiles']".val)) {
				alert("이미지를 하나 이상 업로드 해주세요");
				return false;
			}
		}else{ //이미지가 있는 경우
			var files = $("input[name='uploadFiles']")[0].files;
		 	
			if (!files.length){
				alert("이미지를 하나 이상 업로드 해주세요");
				return false;
			}
			
			for(var i=0; i<files.length; i++){
				var fileName = files[i].name;
				var fileSize = files[i].size;
				var ext = fileName.split('.').pop().toLowerCase();
				//.으로 잘라서 맨 마지막 값만 꺼내기
				
				console.log("fileName : " + fileName);
				console.log("fileSize : " + fileSize);
				
				if(fileSize >= maxSize){
					alert("파일 사이즈는 10mb를 초과할 수 없습니다.");
					return false;
				}
				
				//특정 확장자 체크
				if($.inArray(ext, ['gif', 'png', 'jpeg', 'jpg']) == -1 ){
					alert("gif, png, jpeg, jpg 파일만 업로드 할 수 있습니다.");
					return false;
				}
			}
		}
		
		
		return true;
	} //checkFiles() 끝
	
	$("#galleryForm").validate({
		rules : { 
			bo_title : {required : true},
			bo_content : "required"
		},
		message : {
			bo_title : "제목을 입력하세요.",
			bo_content : "내용을 입력하세요."
		},
		//여기서부터
		submitHandler : function(frm){
			if(!checkFiles()){
				return false;
			}else{
				dosubmit(); //유효성 검사 통과시 전송	
			}
		},
		success : function(e){
			
		}
		//여기까지는 생략 가능
	});
	
	$('#btnGolist').click(function(){
		location.href = 'galleryList?bo_type=GALLERY';
	});
	
	$frm = $("#galleryForm");
	
	//insert 파일 추가
	$(".btn-new-file", $frm).click(function(){
		$("#fileList").append(
		'<div>' +
		'<input type="file" name="uploadFiles" multiple="multiple" style="display: inline;" >' +
		'<button type="button" class="btn btn-danger btn-xs btn-delete-file" style="margin-left:4px;">x</button>' + 
		'</div>'			
		);
	});
	
	//insert 신규 파일 삭제
	$("#fileList").on("click", ".btn-delete-file", function(){
		$(this).parent().remove();
	});
	
	$(".btn-delete-exist").click(function(){
		$(this).parent().html('<input type="hidden" name="delFileSeq" value="' + $(this).data("file_seq_no") + '">')
	});
	
	
	
});

function dosubmit(){
	var frm = document.galleryForm;
	<c:if test="${gallery.bo_seq_no == 0}">
		frm.action = "galleryInsert";
	</c:if>
	<c:if test="${gallery.bo_seq_no != 0}">
		frm.action = "galleryUpdate";
	</c:if>
	
	frm.submit();
}
</script>

<title>Insert title here</title>
</head>
<body>


<div class="container">
	<h2 align="center">게시글 등록</h2>
	
	<form name="galleryForm" id="galleryForm" method="post" enctype="multipart/form-data">
		<input type="hidden" id="bo_type" name="bo_type" value="GALLERY">
		<input type="hidden" id="bo_seq_no" name="bo_seq_no" value="${gallery.bo_seq_no }">
		
		<table class="table">
		
			<tr>
				<td>제목</td>
				<td><input type="text" id="bo_title" name="bo_title" size="100" value="${gallery.bo_title }"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
				<input type="hidden" id="bo_writer" name="bo_writer" size="20" value="${gallery.bo_writer }">
				<input type="text" id="bo_writer_name" name="bo_writer_name" size="20" value="${gallery.bo_writer_name }" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>공개여부</td>
				<td><label for="open_yn"><input type="checkbox" name="bo_open_yn" id="open_yn" value="Y" ${gallery.bo_open_yn == 'N' ? '' : 'checked' }> 공개</label></td>
			</tr>
			
			<!-- 첨부 파일 관련 -->
			<tr>
				<td>첨부파일</td>
				<td>
					<!-- 업로드된 파일 목록 -->
					<p>
						<c:forEach var="fileItem" items="${gallery.fileList}">
					
							<div>
								<a href="${pageContext.request.contextPath}/common/download?file_seq_no=${fileItem.file_seq_no}" >${fileItem.file_name}</a>${fileItem.file_fancy_size}
								<button type="button" class="btn btn-danger btn-xs btn-delete-exist" 
								data-file_seq_no="${fileItem.file_seq_no}">x</button>
							</div>
						
						</c:forEach>			
					</p>
					<p><button type="button" class="btn btn-primary btn-xs btn-new-file">추가</button>
					<div id="fileList">
						<div>
							<input type="file" name="uploadFiles" multiple="multiple" style="display: inline;" >
							<button type="button" class="btn btn-danger btn-xs btn-delete-file">x</button>
						</div>
					</div>
					</p>
					
				</td>
			</tr>			
			<!-- //첨부 파일 관련 -->
			
			<tr>
				<td>내용</td>
				<td><textarea id="bo_content" name="bo_content" rows="15" cols="100">${gallery.bo_content}</textarea></td>
			</tr>
		
		</table>
		
		<p align="center">
			
			<input type="submit" value="저장" class="btn btn-primary" >
				
			<input type="reset" value="취소" class="btn btn-primary">
			<input type="button" id="btnGolist" value="목록" class="btn btn-primary">
		</p>		
	
	</form>

</div>

</body>
</html>