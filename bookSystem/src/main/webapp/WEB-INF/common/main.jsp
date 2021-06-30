<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<%@taglib  prefix="spring" uri="http://www.springframework.org/tags" %>  
<!DOCTYPE html>
<html lang="en">
<head>
  <title>메인 화면</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>

<div class="container">
  <h2>나만의 도서</h2>
  <p>다양한 책을 대여해드립니다.</p>
  <hr>
  <div class="col">
    <div class="col-md-6">
      <div class="thumbnail">
       	 <a href="" target="blank">
	          <img src="<spring:url value='/resources/img/mainImage.png'/>" alt="Lights" style="width:100%" height="500">
<!--           <div class="caption"> -->
      	  </a>
       </div>
      </div>
       <div class="col-md-6">
      <div class="thumbnail">
       	 <a href="" target="blank">
	          <img src="<spring:url value='/resources/img/mainImage1.png'/>" alt="Lights" style="width:100%" height="500">
<!--           <div class="caption"> -->
      	  </a>
       </div>
      </div>
    </div>
</div>
</body>
</html>
