<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>회원별 월별 집계</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <style type="text/css">
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
  <h2>회원별 통계현황</h2>
  <p>회원별 통계현황입니다.</p>  
  <hr>         
  <%-- [검색 모드] ==== 시작 ==== --%>
	<form id="contact-form search" method="get" action="${contextPath}/meMonthStat.me" role="form">
		<div id="search">
			<select class="form-control" name="mode" id="mode"
				data-toggle="tooltip" title="검색할 조건을 선택하세요!">
				<option class="form-control" value="all">전체</option>
				<option class="form-control" value="reEmail">이메일</option>
			</select> 
			<input type="text" class="form-control"placeholder="검색할 내용을 입력하세요" id="keyword" name="keyword">
			<button type="submit">
				검색
			</button>
		</div>
	</form>
  <table class="table table-bordered">
    <thead>
      <tr>
        <c:forEach var="bean" items="${requestScope.header}">
        	<th>${bean}</th>
        </c:forEach>
        <th>총건수</th>
      </tr>
    </thead>
    <tbody>
    	
    	<c:forEach var="item" items="${requestScope.resultList}">
    		<tr>
    			<td>${item.email}</td>
    			<c:forEach var="mapData" items="${item.contentMap}">
    				<c:if test="${mapData.value == 0}">
	    				<td>
    						${mapData.value}
	    				</td>
    				</c:if>
    				<c:if test="${mapData.value != 0}">
	    				<td>
	    					<a href="${contextPath}/meMonthDetail.me?month=${mapData.key}&email=${item.email}">
	    						${mapData.value}
	    					</a>
	    				</td>
    				</c:if>
    			</c:forEach>
    			<td>${item.sumCount}</td>
    		</tr>
    	</c:forEach>
    </tbody>
  </table>
  <div align="center">
		<footer>${requestScope.pagingHtml}</footer>
	</div>
</div>
</body>