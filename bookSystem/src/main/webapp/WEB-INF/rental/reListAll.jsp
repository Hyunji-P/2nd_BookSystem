<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
/* 검색 부분  시작*/
#search_btn{
	cursor: pointer;
}


#search {
	display: flex;
	flex-direction: row;
	justify-content: flex-end;
	padding-left : 15px;
	padding-right : 5px;
	margin: 10px 0px 10px 0px;
}


select#mode{
	width: 10%;
    height: 40px;
    font-size: 13px;
    line-height: 1.428571429;
    background-color: #fff;
    background-image: none;
    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
    border-radius: 1px;
    border: 1px solid rgba(111, 121, 122, 0.3);	
}

input#keyword{
	display: block !important;
	width: 20%;
    height: 40px !important;
    padding: 6px 12px !important;
    font-size: 13px !important;
    line-height: 1.428571429 !important;
    background-color: #fff !important;
    background-image: none !important;
    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s !important;
    border-radius: 1px !important;
    border: 1px solid rgba(111, 121, 122, 0.3) !important;
    -webkit-box-shadow: none !important;
}

#search button{
	height: 40px;
    font-size: 13px;
    line-height: 1.428571429;
    background-color: #fff;
    background-image: none;
    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
    border-radius: 1px;
    border: 1px solid rgba(111, 121, 122, 0.3);	
}

@media( max-width : 585px ) {
	select#mode{
		width: 30%;
	} 
	
	input#keyword{
		width: 60%;
	}
	
	button {
		width: 10%;
	}
	
	
}

/* 검색 부분  끝*/
	
</style>


</head>
<body>

<div class="container">
	<h2>대여 목록</h2>
	<p>${sessionScope.loginfo.meName}님의 대여 목록입니다.</p>
	<p>${requestScope.totalCount}건이 존재합니다.</p>
	<hr>
	<table>
	  <tr>
	    <th>대여번호</th>
	    <th>도서번호</th>
	    <th>대여 시작일</th>
	    <th>대여 종료일</th>
    	 <th>수정</th>
   		 <th>삭제</th>
	  </tr>
	  <c:forEach var="bean"  items="${requestScope.lists}">
		  <tr>
		  	<td>
		  		<a href="${contextPath}/reDetailView.re?reSeq=${bean.reSeq}">${bean.reSeq}</a></td>
		    <td>
	    		${bean.reBookNumber}
		    </td>
		    <td>${bean.reStartDate}</td>
		    <td>${bean.reEndDate}</td>
		    <td>
		    	<a href="${contextPath}/reUpdate.re?reSeq=${bean.reSeq}&reBookNumber=${bean.reBookNumber}">수정</a>
		    </td>
		    <td>
		    	<a href="${contextPath}/reDelete.re?reSeq=${bean.reSeq}">삭제</a>
		    </td>
		  </tr>
	  </c:forEach>
	</table>
	<div align="center">
		<footer>${requestScope.pagingHtml}</footer>
	</div>
</div>
</body>
</html>
