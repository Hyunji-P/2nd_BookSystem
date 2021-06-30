<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="./../common/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 상세</title>
<script>
</script>
<style type="text/css">
	.bottom a {
		border-radius: 4px;
		width: 10%;
		height: 7%;
		padding: 15px;
		background-color:  #4682B4;
		text-decoration: none;
		color: #fff;
	}
	
	.bottom a:hover {
		color: #fff;
		text-decoration: none;
	}
	
	.header{
		display: flex;
		justify-content: space-between;
	}
</style>
</head>
<body>
	<div class="container" role="main">
		<h2>게시판 상세</h2>
		<hr>
		<div class="panel panel-info">
      		<div class="panel-heading header" style="font-size:18px; font-weight:bold;">
      			<p id="first">제목 : ${boBean.boTitle}</p>
      			<p id="last">작성일 :
      				<fmt:parseDate var="dateString" value="${boBean.boRegDate}" pattern="yyyy-MM-dd" />
      				<fmt:formatDate pattern="yyyy-MM-dd" value="${dateString}"/>
      			</p>
      		</div>
      		<div class="panel-body" style="font-size:18px; height: 500px;">${boBean.boContent}</div>
        </div>
		<div class="bottom" style="margin-top: 20px">
			<c:if test="${sessionScope.loginfo.meEmail eq boBean.boEmail}">
				<a href="${contentPath}/boUpdate.bo?boSeq=${boBean.boSeq}">수정</a>
				<a href="${contentPath}/boDelete.bo?boSeq=${boBean.boSeq}">삭제</a>
			</c:if>
			<a href="${contentPath}/boList.bo?${requestScope.parameters}">목록</a>
			<c:if test="${whologin != 0}">
				<c:if test="${boBean.boGroupOrd == 0 && boBean.boGroupLayer == 0}">
					<a href="${contentPath}/boFromOriToRepl.bo?boOriginSeq=${boBean.boOriginSeq}&${requestScope.parameters}">원글에 답글쓰기</a>
				</c:if>
				<c:if test="${boBean.boGroupOrd != 0 && boBean.boGroupLayer < 2}">
					<a href="${contentPath}/boFromReplToRepl.bo?boSeq=${boBean.boSeq}&${requestScope.parameters}">답글에 답글쓰기</a>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>



