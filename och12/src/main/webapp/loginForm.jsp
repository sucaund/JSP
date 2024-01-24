<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<h2>로그인</h2>
<form action="loginPro.jsp" method="post">
	<table border="1">
		<tr><td>아이디</td><td><input type="text" name="id" required="required"></td></tr>
		<tr><td>패스워드</td><td><input type="password" name="passwd" required="required"></td></tr>
			<tr><td><input type="submit" value="확인"></td>
			<td><input type="reset" value="취소"></td></tr>
	</table>

</form><p>
</body>
</html>