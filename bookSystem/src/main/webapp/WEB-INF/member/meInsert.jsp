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
	  width: 50%;
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
		// 2021-06-10_박현지
		// [중복 검사 버튼] 이메일 중복 검사 메소드 
		function duplCheck() {
			var err_email = $('#err_email').text();
			
			if (err_email == '') { // 유효성 검사가 통과되면 
				var meEmail = $('#meEmail').val();
				
				if (meEmail == '') { // 이메일 주소가 입력이 되어있지 않으면
					alert('이메일을 입력해주세요.');
				}else{ // 이메일 주소가 입력이 되었으면 중복 검사 컨트롤러로 이동 
					var url = "<%request.getContextPath();%>/meDuplCheck.me?meEmail=" + meEmail;
					window.open(url,"이메일 중복 검사","width=520 height=200");  
				}
			}else{ // 유효성 검사가 통과 되지 않으면
				alert('올바른 이메일 형식이 아닙니다.');
			}
		}
		
		// 2021-06-11_박현지 
		// 1. 최종 회원 가입 진행 시 이메일 중복 체크를 했는지 체크
		// 2. 최종 회원 가입 진행 시 유효성 검사를 체크 
		function emailCheck() {
			var isCheck = $('#isCheck').val();
			
			if (isCheck == 'false') {
				alert('이메일 중복 검사를 먼저 해주세요.');
				return false;
			}
			
			if ($('.errCss').text() != '') { //유효성 검사 통과가 안 되었으면
				alert('입력하신 값을 다시 확인해주세요.');
				return false;
			}else{
				return true;
			}
		}
		
		// 2021-06-11_박현지
		// 이메일을 입력 중에는 isCheck값을 false로 둔다
		// 중복 검사를 하면 isCheck는 true로 바뀌어서 회원 가입이 가능하나
		// 다시 이메일을 수정하면 isCheck값은 true로 고정 되어있다.
		// 따라서, 이메일을 입력할때마다 isCheck값을 false로 변경해준다.
		function emailInput() {
			$('#isCheck').val(false);
		}
		
		// 2021-06-11_박현지
		// 제이쿼리를 이용한 유효성 검사
		$(document).ready(function(){
			var email_RegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			var pass_RegExp = /^[a-zA-Z0-9]{4,10}$/;
			var phone_RegExp = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
			var name_RegExp = /^[가-힣]{2,10}$/;

			
			// ========== 이메일 유효성 검사 ============//
			 $("#meEmail").blur(function(){
				 var meEmail = $('#meEmail').val();
				 if (!email_RegExp.test(meEmail)){
					$('#err_email').text('잘못된 이메일 형식입니다. ex) helloword@gmail.com 형식으로 입력하세요.');
				}else{
					$('#err_email').text('');
				}
				 
			 });
		
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

<form action="${contextPath}/meInsert.me" method="POST" style="border:1px solid #ccc">
  <input type="hidden" id="isCheck" name="isCheck" value="false">
  <div class="container">
    <h1>회원 가입</h1>
    <p>가입하실 계정을 생성하세요.</p>
    <p>*는 필수로 입력해야합니다.</p>
    
    <input type="radio" id="meDivision" name="meDivision" value="관리자" required disabled="disabled">관리자
    <input type="radio" id="meDivision" name="meDivision" value="회원" required checked="checked">일반회원
    <hr>
    <label for="meEmail">
    	<b>* Email</b>
    	<button class="btn" onclick="duplCheck();">중복 검사</button>
    </label>
    <input type="email" placeholder="Enter Email" id="meEmail" name="meEmail" required value="${bean.meEmail}" onkeyup="emailInput();">
    <span id="err_email" class="errCss"></span><br>
    
    <label for="mePassword"><b>* Password</b></label>
    <input type="password" placeholder="Enter Password" id="mePassword" name="mePassword" required value="${bean.mePassword}">
    <span id="err_password" class="errCss"></span><br>
	
    <label for="mePhone"><b>* Phone Number</b></label>
    <input type="text" placeholder="Enter Phone Number" id="mePhone" name="mePhone" required value="${bean.mePhone}">
    <span id="err_phone" class="errCss"></span><br>
    
    
    <label for="meName"><b>* Name</b></label>
    <input type="text" placeholder="Enter Name" id="meName" name="meName" required value="${bean.meName}">
	<span id="err_name" class="errCss"></span><br>


    <div class="clearfix">
      <button type="reset" class="cancelbtn">Cancel</button>
      <button type="submit" class="signupbtn" onclick="return emailCheck();">Sign Up</button>
    </div>
  </div>
</form>

</body>
</html>
