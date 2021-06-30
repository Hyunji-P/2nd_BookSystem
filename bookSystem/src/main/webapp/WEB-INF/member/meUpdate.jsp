<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<!DOCTYPE html>
<html>
	<style>
	body {font-family: Arial, Helvetica, sans-serif;}
	* {box-sizing: border-box}
	
	/* Full-width input fields */
	input[type=text], input[type=password], input[type=email] {
	  width: 100%;
	  padding: 15px;
	  margin: 5px 0 5px 0;
	  display: inline-block;
	  border: none;
	  background: #f1f1f1;
	}
	
	input[type=text]:focus, input[type=password]:focus , input[type=email]:focus{
	  background-color: #ddd;
	  outline: none;
	}
	
	hr {
	  border: 1px solid #f1f1f1;
	  margin-bottom: 25px;
	}
	
	/* Set a style for all buttons */
	button {
	  background-color: #04AA6D;
	  color: white;
	  padding: 14px 20px;
	  margin: 8px 0;
	  border: none;
	  cursor: pointer;
	  width: 100%;
	  opacity: 0.9;
	}
	
	button:hover {
	  opacity:1;
	}
	
	/* Extra styles for the cancel button */
	.cancelbtn {
	  padding: 14px 20px;
	  background-color: #f44336;
	}
	
	/* Float cancel and signup buttons and add an equal width */
	.cancelbtn, .signupbtn {
	  float: left;
	  width: 100%;
	}
	
	/* Add padding to container elements */
	.container {
	  padding: 16px;
	}
	
	/* Clear floats */
	.clearfix::after {
	  content: "";
	  clear: both;
	  display: table;
	}
	
	.errCss{
		color: red;
		font-weight: bold;
	}
	
	/* Change styles for cancel button and signup button on extra small screens */
	@media screen and (max-width: 300px) {
	  .cancelbtn, .signupbtn {
	     width: 100%;
	  }
	}
	</style>
	<script type="text/javascript">
		
		// 2021-06-11_박현지 
		// 최종 회원 수정 진행 시 유효성 검사를 체크 
		function updateCheck() {
			
			if ($('.errCss').text() != '') { //유효성 검사 통과가 안 되었으면
				alert('입력하신 값을 다시 확인해주세요.');
				return false;
			}else{
				return true;
			}
		}
		// 2021-06-11_박현지
		// 제이쿼리를 이용한 유효성 검사
		$(document).ready(function(){
			var pass_RegExp = /^[a-zA-Z0-9]{4,10}$/;
			var phone_RegExp = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
			var name_RegExp = /^[가-힣]{2,10}$/;

			
			// ========== 패스워드 유효성 검사 ============//
			$("#mePassword").blur(function(){
				 var mePassword = $('#mePassword').val();
				 if (!pass_RegExp.test(mePassword)){
					$('#err_password').text('영문자와 숫자만 사용 가능합니다. 4 ~ 10글자 이내로 입력하세요.');
				}else{
					$('#err_password').text('');
				}
				 
			 });
			
			// ========== 휴대폰 번호 유효성 검사 ============//
			$("#mePhone").blur(function(){
				 var mePhone = $('#mePhone').val();
				 if (!phone_RegExp.test(mePhone)){
					$('#err_phone').text('올바른 휴대폰 번호 형식이 아닙니다. ex) 01012345678 형식으로 입력하세요.');
				}else{
					$('#err_phone').text('');
				}
				 
			 });
			
			// ========== 이름 유효성 검사 ============//
			$("#meName").blur(function(){
				 var meName = $('#meName').val();
				 if (!name_RegExp.test(meName)){
					$('#err_name').text('한글만 사용 가능합니다.2 ~ 10글자 이내로 입력하세요.');
				}else{
					$('#err_name').text('');
				}
				 
			 });
		});
		
	</script>
<body>

<form action="${contextPath}/meUpdate.me" method="POST" style="border:1px solid #ccc">
  <div class="container">
    <h1>회원 수정</h1>
    <p>가입하신 계정을 수정하세요.</p>
    <hr>
     <label for="meEmail"><b>Email</b></label>
    <input type="email" placeholder="Enter Email" id="meEmail" name="meEmail" required value="${bean.meEmail}" disabled="disabled">
    <input type="hidden" placeholder="Enter Email" id="meEmail" name="meEmail" required value="${bean.meEmail}">
    
    <label for="mePassword"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" id="mePassword" name="mePassword" required value="${bean.mePassword}">
    <span id="err_password" class="errCss"></span><br>
	
    <label for="mePhone"><b>Phone Number</b></label>
    <input type="text" placeholder="Enter Phone Number" id="mePhone" name="mePhone" required value="${bean.mePhone}">
    <span id="err_phone" class="errCss"></span><br>
    
    
    <label for="meName"><b>Name</b></label>
    <input type="text" placeholder="Enter Name" id="meName" name="meName" required value="${bean.meName}">
	<span id="err_name" class="errCss"></span><br>


    <div class="clearfix">
      <button type="submit" class="signupbtn" onclick="return updateCheck();">Update</button>
    </div>
  </div>
</form>

</body>
</html>
