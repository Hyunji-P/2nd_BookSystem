<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>통계 화면</title>
</head>
<body>
	<div class="container">
  <h2>통계 화면</h2>
  <p>회원별, 도서별 통계 페이지입니다.</p>            
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>도서번호</th>
        <th>대여자 이메일</th>
        <th>대여 건수</th>
     </tr>
    </thead>
    <tbody>
      <c:forEach var="bean" items="${requestScope.lists}">
          <c:if test="${bean.bkSeq != '총 대여 건수'}">
		      <tr>
		        <td>${bean.bkSeq}</td>
		        <td>${bean.reEmail}</td>
		        <td>${bean.cnt}</td>
		      </tr>
	      </c:if>
	      <c:if test="${bean.bkSeq == '총 대여 건수'}">
		      <tr>
		        <td colspan="2" align="center">${bean.bkSeq}</td>
		        <td>${bean.cnt}</td>
		      </tr>
	      </c:if>
      </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>