<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<%@taglib  prefix="spring" uri="http://www.springframework.org/tags" %>  
<!DOCTYPE html>
<html lang="en">
<head>
  <title>대여 상세</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <style type="text/css">
  	.tdCss{
  		background-color: #4682B4;
  		text-align: center;
  		font-weight: bold;
  		color: #fff;
  	}
  	body {
	  font-family: Arial, Helvetica, sans-serif;
	  background-color: #fff;
	}
	
	* {
	  box-sizing: border-box;
	}
	
	/* Add padding to containers */
	.container {
	  padding: 16px;
	  background-color: white;
	}
	
	/* Full-width input fields */
	input[type=date]{
	  width: 70%;
	  padding: 15px;
	  margin: 5px 0 20px 0;
	  display: inline-block;
	  border: none;
	  background: #f1f1f1;
	}
	
	input[type=text]:focus{
	  background-color: #ddd;
	  outline: none;
	}
	
	/* Overwrite default styles of hr */
	hr {
	  border: 1px solid #f1f1f1;
	  margin-bottom: 25px;
	}
	
	/* Set a style for the submit button */
	.registerbtn {
	  background-color: #4682B4;
	  color: white;
	  padding: 15px;
	  margin: 5px 0 20px 0;
      border: none; 
	  cursor: pointer;
	  width: 25%;
	  opacity: 0.9;
	}
	
	.registerbtn:hover {
	  opacity: 1;
	}
	
	/* Add a blue text color to links */
	a {
	  color: dodgerblue;
	}
	
	/* Set a grey background color and center the text of the "sign in" section */
	.signin {
	  background-color: #f1f1f1;
	  text-align: center;
	}
	
	#rentInput{
		display: flex;
		justify-content: space-between;
	}
	
	.rentText{
		 padding: 15px;
	  margin: 5px 0 20px 0;
	}
  </style>
  
</head>
<body>

<div class="container">
  <h2>대여 상세보기</h2>
  <p>대여 상세보기 페이지입니다.</p>  
  <hr>
   <div class="panel panel-primary">
    <div class="panel-heading">대여기간</div>
    <div class="panel-body">${requestScope.reBean.reStartDate} ~ <b style="color:red;">${requestScope.reBean.reEndDate}</b> </div>
  </div>
  <table class="table table-bordered">
    <tbody>
      <tr>
        <td rowspan="10" width="40%">
        	<img src="./upload/${bkBean.bkImage}" alt="error" width="500" height="400">
        </td>
        <td colspan="2" align="center" style="background-color:#C0C0C0;font-weight: bold;">내용</td>
      </tr>
      <tr>
        <td width="20%"  class="tdCss">도서번호</td>
        <td width="40%">${bkBean.bkSeq}</td>
      </tr>
      <tr>
        <td width="20%"  class="tdCss">도서명</td>
        <td width="40%">${bkBean.bkTitle}</td>
      </tr>
       <tr>
        <td width="20%" class="tdCss">작가</td>
        <td width="40%">${bkBean.bkAuthor}</td>
      </tr>
      <tr>
        <td width="20%" class="tdCss">출판사</td>
        <td width="40%">${bkBean.bkPublisher}</td>
      </tr>
      <tr>
        <td width="20%" class="tdCss">출판일자</td>
        <td width="40%">${bkBean.bkPublDate}</td>
      </tr>
      <tr>
        <td width="20%" class="tdCss">책내용</td>
        <td width="40%">${bkBean.bkContent}</td>
      </tr>
    </tbody>
  </table>
  <hr>
  
</div>

</body>