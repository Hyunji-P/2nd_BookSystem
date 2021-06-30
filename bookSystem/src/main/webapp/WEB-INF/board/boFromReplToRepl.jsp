<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="./../common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <style type="text/css">
  	.bottom{
  		display: flex;
  		justify-content: space-between;
  	}
  	
  	.errCss{
  		color: red;
  	}

	
	.header{
		display: flex;
		justify-content: space-between;
	}
  </style>
  <script type="text/javascript">
    // 2021-06-25_박현지
    // 목록으로 버튼을 클릭시 이전 페이지로 이동
  	function backList() {
  		window.history.back();
	}
  	
   // 2021-06-25_박현지
   // 제이쿼리를 이용한 유효성 검사
	$(document).ready(function(){
		// ========== 게시글 제목 유효성 검사 ============//
		 $("#boTitle").blur(function(){
			 var boTitle = $('#boTitle').val();
			 if (boTitle.length < 4 || boTitle.length > 15 ){
				$('#err_title').text('4글자 ~ 15글자 이내로 입력하세요.');
			}else{
				$('#err_title').text('');
			}
			 
		 });
		// ========== 게시글 내용 유효성 검사 ============//
		 $("#boContent").blur(function(){
			 var boContent = $('#boContent').val();
			 if (boContent.length < 5 || boContent.length > 50 ){
					$('#err_content').text('5글자 ~ 50글자 이내로 입력하세요.');
				}else{
					$('#err_content').text('');
				}
			 
		 });
	 });
   
   // 2021-06-25_박현지
   // 최종 유효성 검사 체크 
   function validCheck() {
	   var boTitle = $('#boTitle').val();
	   var boContent = $('#boContent').val();
	   
	   if ($('.errCss').text() != '' || boTitle.length == 0 || boContent == 0) { //유효성 검사 통과가 안 되었으면
			alert('입력하신 값을 다시 확인해주세요.');
			return false;
		}else{
			return true;
		}
   }
  </script>
</head>
<body>
  <div class="container" role="main">
    <h2>게시판 답글</h2>
  	<p>답글에 답글을 작성하세요.</p>
    <hr>
       <form action="${contextPath}/boFromReplToRepl.bo" method="POST">
       	<input type="hidden" id="boSeq" name="boSeq" value="${boBean.boSeq}">
	    <div class="form-group">
	      <label for="boTitle">답글 제목:</label>
	      <input type="text" class="form-control" id="boTitle" name="boTitle" placeholder="게시글 제목을 입력하세요.">
	      <span id="err_title" class="errCss"></span>
	    </div>
	    <div class="form-group">
	      <label for="boContent">답글 내용:</label>
	      <br>
	      <textarea rows="10" cols="10" id="boContent" name="boContent" class="form-control" placeholder="게시글 내용을 입력하세요."></textarea>
	      <span id="err_content" class="errCss"></span>
	    </div>
	    <div class="bottom">
	    	<a href="${contentPath}/boDetailView.bo?boSeq=${boBean.boSeq}" class="btn btn-primary">뒤로가기</a>
	    	<button type="submit" class="btn btn-primary" onclick="return validCheck();">답글 등록</button>
	    </div>
      </form>
  </div>
</body> 
</html>