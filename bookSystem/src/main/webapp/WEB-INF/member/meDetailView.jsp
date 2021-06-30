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
	  margin: 5px 0 22px 0;
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
	
	/* Change styles for cancel button and signup button on extra small screens */
	@media screen and (max-width: 300px) {
	  .cancelbtn, .signupbtn {
	     width: 100%;
	  }
	}
	</style>
<body>

  <div class="container">
    <h1>회원 상세 정보</h1>
    <hr>
    <label for="meEmail">
    	<b>Email</b>
    </label>
    <div class="panel panel-default">
	<div class="panel-body">${requestScope.bean.meEmail}</div>
	</div>
	<label for="mePassword">
    	<b>Password</b>
    </label>
    <div class="panel panel-default">
	<div class="panel-body">${requestScope.bean.mePassword}</div>
	</div>
	<label for="Phone">
    	<b>Phone Number</b>
    </label>
    <div class="panel panel-default">
	<div class="panel-body">${requestScope.bean.mePhone}</div>
	</div>
	<label for="meEmail">
    	<b>Name</b>
    </label>
    <div class="panel panel-default">
	<div class="panel-body">${requestScope.bean.meName}</div>
	</div>
    <a href="${contextPath}/meUpdate.me?meEmail=${requestScope.bean.meEmail}">수정할까요?</a><br>
    <c:if test="${whologin == 1}">
    	<a href="${contextPath}/meDelete.me?meEmail=${requestScope.bean.meEmail}">탈퇴할까요?</a>
    </c:if>
  </div>
</body>
</html>
