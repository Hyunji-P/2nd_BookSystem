<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String contextPath = request.getContextPath();
%>
<c:set var="contextPath" value="<%=contextPath%>" scope="application"/>

<!-- 로그인 정보를 담고 있는 변수 -->
<c:set var="whologin" value="0"/>
<!-- 일반 회원으로 로그인한 경우 -->
<c:if test="${empty sessionScope.loginfo}">
	<c:set var="whologin" value="0"/>
</c:if>
<c:if test="${not empty sessionScope.loginfo}">
	<c:set var="whologin" value="1" />
</c:if>
<!-- 관리자로 로그인한 경우 -->
<c:if test="${sessionScope.loginfo.meDivision eq '관리자'}">
	<c:set var="whologin" value="2" />
</c:if> 

<!DOCTYPE html>
<html lang="en">
<head>
  <title>도서관리 시스템</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="${contextPath}/main.co">DATASOLUTION</a>
	    </div>
	    <ul class="nav navbar-nav">
	      <li class="active"><a href="${contextPath}/main.co">Home</a></li>
	      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">회원<span class="caret"></span></a>
	        <ul class="dropdown-menu">
	          <c:if test="${whologin == 1 || whologin == 2}">
		          <li><a href="${contextPath}/meUpdate.me?meEmail=${loginfo.meEmail}">내 정보수정</a></li>
		          <li><a href="${contextPath}/meDetailView.me?meEmail=${loginfo.meEmail}">회원 상세보기</a></li>
	          </c:if>
	          <c:if test="${whologin == 2}">
		          <li><a href="${contextPath}/meList.me">회원 목록</a></li>
	          </c:if>
	        </ul>
	      </li>
	      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">도서<span class="caret"></span></a>
	      	<ul class="dropdown-menu">
	      		<c:if test="${whologin == 2}">
			       <li><a href="${contextPath}/bkInsert.bk">도서 등록</a></li>
		       </c:if>
		      	   <li><a href="${contextPath}/bkList.bk">도서 목록</a></li>
	        </ul>
	      </li>
	       <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">대여<span class="caret"></span></a>
	      	<ul class="dropdown-menu">
	      		<c:if test="${whologin == 1}">
			       <li><a href="${contextPath}/reList.re?reEmail=${loginfo.meEmail}">나의 대여내역</a></li>
		       </c:if>
		       <c:if test="${whologin == 2}">
			       <li><a href="${contextPath}/reListAdmin.re">회원별 대여현황(관리자용)</a></li>
		       </c:if>
	        </ul>
	      </li>
	       <c:if test="${whologin == 2}">
		       <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">통계<span class="caret"></span></a>
		      	<ul class="dropdown-menu">
				       <li><a href="${contextPath}/meMonthStat.me">회원별 통계 현황</a></li>
				       <li><a href="${contextPath}/bkMonthStat.bk">도서별 통계 현황</a></li>
		        </ul>
		      </li>
	       </c:if>
	      <li><a href="${contextPath}/boList.bo">게시판</a></li>
	    </ul>
	    <ul class="nav navbar-nav navbar-right">
	    	<c:if test="${whologin == 0}">
	      		<li><a href="${contextPath}/meInsert.me"><span class="glyphicon glyphicon-user"></span>회원 가입</a></li>
	      		<li><a href="${contextPath}/meLogin.me"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
	      </c:if>
	      <c:if test="${whologin != 0}">
	      		<li><a href="${contextPath}/meDetailView.me?meEmail=${loginfo.meEmail}"><span class="glyphicon glyphicon-user"></span> ${loginfo.meName}님</a></li>
	      		<li><a href="${contextPath}/meLogout.me"><span class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
	      </c:if>
	    </ul>
	  </div>
	</nav>
    <c:if test="${not empty sessionScope.err_message}">
		<script type="text/javascript">
			alert('${sessionScope.err_message}') ;	 
		</script>
		<% session.removeAttribute("err_message") ; %>
	</c:if>		
</body>
</html>