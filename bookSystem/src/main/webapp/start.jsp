<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시작</title>
</head>
<body>
<%
	String imsi = request.getContextPath() + "/main.co" ;
	//out.print(imsi);
	response.sendRedirect(imsi) ;
%>
</body>
</html>