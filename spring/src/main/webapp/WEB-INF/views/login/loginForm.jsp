<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css">
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
$(document).ready(function(){
	$("#mem_id").focus();
	
	$("#mem_pwd").keydown(function(key){
		if(key.keyCode == 13)
			login();
	});
});

function login(){
	
   var frm = document.loginForm;
   if(!validate()){
      return false;
   }
   
   frm.action="login";
   frm.submit();
}

function validate(){
   var frm = document.loginForm;
   
   if(frm.mem_id.value == ''){
      alert("아이디를 입력하세요");
      frm.mem_id.focus();
      return false;
   }
   if(frm.mem_pwd.value == ''){
      alert("패스워드를 입력하세요");
      frm.mem_pwd.focus();
      return false;
   }
   return true;
}

</script>

</head>
<body>
<div class = "container" align="center">
   <h3>로그인</h3>
   <form name="loginForm" method="post">
      <table class="table table-bordered" style="width:300px">
         <tr>
            <td>아이디</td>
            <td><input type="text" name="mem_id" id="mem_id" size="20"></td>
         </tr>
         
         <tr>
            <td>패스워드</td>
            <td><input type = "password" name="mem_pwd" id="mem_pwd" size="20"></td>
         </tr>
         
         <tr>
            <td colspan = "2">
               <input type="button" value="로그인" class="btn btn-default" onclick="login();">
               <input type ="button" value="회원가입" class="btn btn-default"
               onclick = "location.href='${pageContext.request.contextPath}/member/memberForm'">
            </td>
         </tr>
      </table>
   </form>
</div>
</body>
</html>