<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
   href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<script
   src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.js"></script>


<title>회원가입</title>
<script>
var duplicateCheck = false; // ID중복 검사

$(function(){
   $("#btn_idCheck").click(function(){
      fn_idCheck();
   });
   
   function fn_idCheck(){
      var frm = document.memberForm;
      var params = {"mem_id" : frm.mem_id.value};
      
      console.log(frm.mem_id.value);
      
      if(frm.mem_id.value == ""){
         alert("아이디를 입력하세요");
         return;
      }
      //아이디 중복 체크
      $.ajax({
         type : 'post', //get,post
         url : 'memberExists', //요청하는 url
         data : params, //전달할 파라미터 , string이나 json 형태
         success : function(data,status){
            //요청이 성공했을 때 실행되는 내용
         duplicateCheck = data.result;
            if(duplicateCheck == "true"){
               $("#lbl_result").text("해당 아이디는 사용중입니다.");
               duplicateCheck  = false;
            }else{
               $("#lbl_result").text("해당 아이디는 사용 가능합니다.");
               duplicateCheck  = true;
            }
         },
         error : function(data,status){
            console.log(data);
            console.log(data.status);
            
         }
      }) //ajax end
   }//fn_idCheck() end
}); //function end

function doSubmit(type) {
   
   if(type == 2){
      duplicateCheck = true;
   }
   //유효성검사 , validate , validation , invalidate, invalidation
   if(!validate()){
      return false;
   }
      
   var frm = document.memberForm;
   if (type == 1) {
      //memberInsert 호출   
      frm.action = "memberInsert";
   } else if (type == 2) {
      //memberUpdate 호출
      frm.action = "memberUpdate"
   }
   frm.submit();
}

function validate(){
   var frm = document.memberForm;
      
   if(!duplicateCheck){
         alert("아이디 중복체크를 해주세요");
         return false;
   }
   
   if(frm.mem_name.value == ""){
         alert("이름을 입력하세요.");
         frm.mem_name.focus();
         return false;
   }
      
   if(frm.mem_birth.value == ""){
      alert("생년월일을 입력하세요.");
      frm.mem_birth.focus();
      return false;
   }   
   
   if(frm.mem_pwd.value == ""){
         alert("비밀번호를 입력하세요.");
         frm.mem_pwd.focus();
         return false;
      
   }else{
      if(frm.mem_pwd_confirm.value ==""){
         alert("비밀번호 확인을 입력하세요.");
         frm.mem_pwd_cofirm.focus();
         return false;
         
        }else
           if(frm.mem_pwd.value != frm.mem_pwd_confirm.value){
              alert("비밀번호가 일치하지 않습니다.");
              return false;
        }
   }
   
   if(frm.mem_phone.value == ""){
       alert("전화번호를 입력하세요.");
       frm.mem_phone.focus();
       return false;
    }
     
    if(frm.mem_email.value == ""){
       alert("이메일을 입력하세요.");
       frm.mem_email.focus();
       return false;
    }
    
    return true;
}

</script>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                   // document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('mem_zipcode').value = data.zonecode;
                document.getElementById("mem_addr_master").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("mem_addr_detail").focus();
            }
        }).open();
    }
</script>
</head>
<body>


   <div>

      <form name="memberForm" method="post">
         <input type="hidden" name="mem_seq_no" value="${member.mem_seq_no}" />
         <table class="table table-bordered">
            <tr>
               <td>성명</td>
               <td><input type="text" name="mem_name" size="20"
                  value="${member.mem_name}"> ${member.mem_name == null ? '' : 'readonly'}
                  실명을 입력하세요.</td>
            </tr>
            <tr>
               <td>아이디</td>
               <td><input type="text" name="mem_id" id="mem_id" size="20"
                  value="${member.mem_id}"> <c:if
                     test="${member.mem_name == null}">
                     <button type="button" class="btn btn-default" id="btn_idCheck">ID중복검사</button>
                   8~20자리 숫자와 영문자 조합<br>
                     <label id="lbl_result"></label>
                  </c:if></td>
            </tr>
            <tr>
               <td>비밀번호</td>
               <td><input type="password" name="mem_pwd" size="20">
                  8~20자리 숫자와 영문자 조합</td>
            </tr>
            <tr>
               <td>비밀번호확인</td>
               <td><input type="password" name="mem_pwd_confirm" size="20">
                  8~20자리 숫자와 영문자 조합</td>
            </tr>
            <tr>
               <td>생년월일</td>
               <td><input type="text" name="mem_birth" size="20"
                  value="${member.mem_birth}"></td>
            </tr>
            <tr>
               <td>전화번호</td>
               <td><input type="text" name="mem_phone" size="20"
                  value="${member.mem_phone}"></td>
            </tr>
            <tr>
               <td>이메일</td>
               <td><input type="text" name="mem_email" size="20"
                  value="${member.mem_email}"></td>
            </tr>
            <tr>
               <td>주소</td>
               <td>
                  <p>
                     <input type="text" name="mem_zipcode" id="mem_zipcode" size="5"
                        value="${member.mem_zipcode}" readonly="readonly">
                     <button type="button" class="btn btn-default" onclick="execDaumPostcode();">우편번호검색</button>
                  </p>
                  <p>
                     <input type="text" name="mem_addr_master" id="mem_addr_master"
                        size="50" value="${member.mem_addr_master}" readonly="readonly">
                  </p>
                  <p>
                     <input type="text" name="mem_addr_detail" id="mem_addr_detail"
                        size="50" value="${member.mem_addr_detail}">
                  </p>
               </td>
            </tr>

            <tr>
               <td colspan="2"><c:if test="${empty member.mem_id}">
                     <input type="button" value="가입하기" class="btn btn-default"
                        onclick="doSubmit(1)">
                  </c:if> <c:if test="${not empty member.mem_id}">
                     <input type="button" value="수정하기" class="btn btn-default"
                        onclick="doSubmit(2)">
                  </c:if> <input type="reset" value="취소" class="btn btn-default"> <input
                  type="button" value="목록" class="btn btn-default"
                  onclick="location.href='memberList'"></td>
            </tr>

         </table>

      </form>



   </div>


</body>
</html>










