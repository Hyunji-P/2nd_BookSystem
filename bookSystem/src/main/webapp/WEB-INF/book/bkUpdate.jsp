<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<!DOCTYPE html>
<html>
	<style>
	body {font-family: Arial, Helvetica, sans-serif;}
	* {box-sizing: border-box}
	
	/* Full-width input fields */
	input[type=text], input[type=password], input[type=email], input[type=date],textarea {
	  width: 100%;
	  padding: 15px;
	  margin: 5px 0 5px 0;
	  display: inline-block;
	  border: none;
	  background: #f1f1f1;
	}
	
	input[type=text]:focus, input[type=password]:focus , input[type=email]:focus , input[type=date]:focus,textarea:focus{
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
	// 2021-06-11_박현지
	// 제이쿼리를 이용한 유효성 검사
	$(document).ready(function(){
		var title_RegExp = /^[0-9가-힣a-zA-Z]{1,15}/;
		var author_RegExp = /^[가-힣a-zA-Z]{1,10}/;
		var publisher_RegExp = /^[가-힣a-zA-Z]{1,10}/;
		
		// ========== 도서 제목 유효성 검사 ============//
		 $("#bkTitle").blur(function(){
			 var bkTitle = $('#bkTitle').val();
			 if (!title_RegExp.test(bkTitle)){
				$('#err_title').text('숫자,한글,영문자를 이용하여 1~15글자 이내로 입력하세요.');
			}else{
				$('#err_title').text('');
			}
		 });
		// ========== 도서 제목 유효성 검사 ============//
		 $("#bkAuthor").blur(function(){
			 var bkAuthor = $('#bkAuthor').val();
			 if (!author_RegExp.test(bkAuthor)){
				$('#err_author').text('한글,영문자를 이용하여 1~10글자 이내로 입력하세요.');
			}else{
				$('#err_author').text('');
			}
		 });
		// ========== 출판사 유효성 검사 ============//
		 $("#bkPublisher").blur(function(){
			 var bkPublisher = $('#bkPublisher').val();
			 if (!publisher_RegExp.test(bkPublisher)){
				$('#err_publisher').text('한글,영문자를 이용하여 1~10글자 이내로 입력하세요.');
			}else{
				$('#err_publisher').text('');
			}
		 });
	});
	</script>
<body>

<form action="${contextPath}/bkUpdate.bk" method="POST" style="border:1px solid #ccc" enctype="multipart/form-data" >
  <input type="hidden" id="bkEmail" name="bkEmail" value="${sessionScope.loginfo.meEmail}">
  <input type="hidden" id="bkSeq" name="bkSeq" value="${bean.bkSeq}">
  <div class="container">
    <h1>도서 수정</h1>
    <p>${bean.bkSeq}번 도서를 수정합니다.</p>
    <p>*는 필수로 입력해야합니다.</p>
    <hr>
    <label for="bkTitle"><b>*Title</b></label>
    <input type="text" placeholder="Enter Title" id="bkTitle" name="bkTitle" required value="${bean.bkTitle}">
    <span id="err_title" class="errCss"></span><br>
	
    <label for="bkAuthor"><b>*Author</b></label>
    <input type="text" placeholder="Enter Author" id="bkAuthor" name="bkAuthor" required value="${bean.bkAuthor}">
    <span id="err_author" class="errCss"></span><br>
    
    
    <label for="bkPublisher"><b>*Publisher</b></label>
    <input type="text" placeholder="Enter Publisher" id="bkPublisher" name="bkPublisher" required value="${bean.bkPublisher}">
	<span id="err_publisher" class="errCss"></span><br>
	
	<label for="bkPublDate"><b>*Publishing Date</b></label>
    <input type="date" placeholder="Enter Publishing Date" id="bkPublDate" name="bkPublDate" required value="${bean.bkPublDate}">
	<span id="err_publiDate" class="errCss"></span><br>
	
	<label for="bkImage"><b>Image</b></label>
    <input type="file" placeholder="Enter Image" id="multiImage" name="multiImage" required><br>
    <span>기존 이미지 이름 : <b id="oldImage">${bean.bkImage}</b></span><br>
    <input type="hidden" id="oldImg" name="oldImg" required value="${bean.bkImage}"><!-- 기존 이미지  -->
<!--     <span style="color:red;">※ 파일을 수정하지 않으면 기존 이미지로 업로드됩니다.</span><br>  -->    
	<span id="err_image" class="errCss"></span><br>
	
	<label for="bkContent"><b>Content</b></label>
	<textarea id="bkContent" name="bkContent" placeholder="Enter Content">${bean.bkContent}</textarea>
	<span id="err_content" class="errCss"></span><br>


    <div class="clearfix">
      <button type="reset" class="cancelbtn">Cancel</button>
      <button type="submit" class="signupbtn" onclick="">Update</button>
    </div>
  </div>
</form>

</body>
</html>
