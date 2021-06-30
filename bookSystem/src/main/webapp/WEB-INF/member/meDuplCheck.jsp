<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>아이디 중복 체크</title>
	<script type="text/javascript">
	
		// 2021-06-11_박현지
		// [닫기] 버튼 클릭 → 사용 가능한 이메일이면 true, 불가능한 이메일이면 fasle 
		function meClose(isCheck) {
			
			window.opener.document.getElementById("isCheck").value = isCheck;
			self.close();
		}
	</script>
	<style type="text/css">
	body {
		margin: 0;
		paddin: 0;
	}
	
	.idck {
		display: flex;
		justify-content: center;
		flex-direction: column;
		text-align: center;
		padding-top: 35px;
	}
	
	
	.idck button{
            width: 100px ;
            height: 40px;
            background-color: #f44336;
            line-height: 40px;
            font-size: 18px;
            color: #ffffff;
            border:#04AA6D;
            font-weight: bold;
        }
	
	</style>
	</head>
	<body>
	<div class="idck">
		<div class="idck_body">
			<p>${requestScope.dupl_Check_Message}</p>
		</div>
		<br>
		<div class="idck_footer">
			<button type="button" onclick="meClose(${requestScope.isCheck});">
				닫기
			</button>
		</div>
	</div>
</body>
</html>