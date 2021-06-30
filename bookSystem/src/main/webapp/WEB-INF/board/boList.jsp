<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="./../common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://kit.fontawesome.com/0bccbc6608.js" crossorigin="anonymous"></script>
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

#boardTop{
	display: flex;
	justify-content: flex-end;
}

#boardTop button {
	color: #fff;
 	margin-bottom: 10px;
	width: 10%;
	height: 40px;
    font-size: 13px;
    line-height: 1.428571429;
    background-color: #4682B4;
    background-image: none;
    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
    border-radius: 1px;
    border: 1px solid rgba(111, 121, 122, 0.3);	
}

table i{
	font-size: 22px;
}

#sort{
	width: 10%;
    height: 35px;
    font-size: 13px;
    line-height: 1.428571429;
    background-color: #fff;
    background-image: none;
    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
    border-radius: 1px;
    border: 1px solid rgba(111, 121, 122, 0.3);	
}

#myForm button{
	color: #fff;
	width: 30px;
	height: 30px;
    font-size: 13px;
    line-height: 1.428571429;
    background-color: #000;
    background-image: none;
    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
    border-radius: 4px;
    border: 1px solid rgba(111, 121, 122, 0.3);	
}

#myForm button i{
	text-align: center;
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
<script type="text/javascript">

	function getContextPath() {
	    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
	    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
	}

	function boardInsert() {
		location.href= getContextPath() + "/boInsert.bo" ; 
	}
	
	function sortASC() {
		$('#sortMethod').val('ASC');
		return true;
	}
	
	function sortDESC() {
		$('#sortMethod').val('DESC');
		return true;
	}

</script>
</head>
<body>
	<div class="container">
	  <h2>게시판</h2>
	  <p>게시판 페이지입니다.</p>
	  <c:if test="${whologin == 0}">
	  	<p style="color:red;">* 로그인을 해야지만 게시글 등록 및 답글 쓰기가 가능합니다.</p>
	  </c:if>
	   <div id="boardTop">
	 	 <c:if test="${whologin == 1 || whologin == 2}">
	 	 	 <button onclick="boardInsert();">글쓰기</button>
	 	 </c:if>
	  </div>  
	  <hr>          
	  <%-- [검색 모드] ==== 시작 ==== --%>
		<form id="contact-form search" method="get" action="${contextPath}/boList.bo" role="form">
			<div id="search">
				<select class="form-control" name="mode" id="mode"
					data-toggle="tooltip" title="검색할 조건을 선택하세요!">
					<option class="form-control" value="all">전체</option>
					<option class="form-control" value="boTitle">제목</option>
				</select> 
				<input type="text" class="form-control"placeholder="검색할 내용을 입력하세요" id="keyword" name="keyword">
				<button type="submit">
					검색
				</button>
			</div>
		</form>
		<%-- [검색 모드] ==== 끝 ==== --%>
		<p><b>[총 ${requestScope.totalCount}건]</b></p>
	   <table class="table table-striped">
	    <thead>
	      <tr>
	        <th width="15%">NO</th>
	        <th>제목</th>
	        <th width="15%">작성자</th>
	        <th width="10%">조회수</th>
	        <th width="15%">등록일자</th>
	      </tr>
	    </thead>
	    <tbody>
	      <c:forEach var="bean" items="${requestScope.lists}">
		      <tr>
		        <td>${bean.boSeq}</td>
		        <c:if test="${bean.boGroupLayer == 0}"><!-- 원글인 경우 -->
		        	 <td>
		        		<a href="${contextPath}/boDetailView.bo?boSeq=${bean.boSeq}&${requestScope.parameters}">${bean.boTitle}</a>
		       		 </td>
		        </c:if>
		         <c:if test="${bean.boGroupLayer == 1}"><!-- 원글에 답글이 달린 경우 -->
		        	 <td>
		        		<a href="${contextPath}/boDetailView.bo?boSeq=${bean.boSeq}&${requestScope.parameters}">
		        			<i class="fas fa-comment-dots"></i>&nbsp;${bean.boTitle}
		        		</a>
		       		 </td>
		        </c:if>
		        <c:if test="${bean.boGroupLayer > 1}"><!-- 답글에 답글이 달린 경우 -->
		        	<td>
		        		<a href="${contextPath}/boDetailView.bo?boSeq=${bean.boSeq}&${requestScope.parameters}">
		        			<i class="fas fa-comment-dots"></i>&nbsp;<i class="fas fa-comment-dots"></i>&nbsp;${bean.boTitle}
		        		</a>
		       		 </td>
		        </c:if>
		        <td>${bean.writer}</td>
				<td>${bean.boHits}</td>	
				<td>${bean.boRegDate}</td>	
		      </tr>
	      </c:forEach>
	    </tbody>
	  </table>
	  <div align="center">
		<footer>${requestScope.pagingHtml}</footer>
	</div>
	</div>
</body>
</html>