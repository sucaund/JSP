<%@page import="och12.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ include file="memberCheck.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% String passwd = request.getParameter("passwd");
	MemberDao md = MemberDao.getInstance();
	System.out.println("DeletePro passwd->"+passwd);
	int result = md.delete(passwd);
	if(result==1){
		session.invalidate();
%>
	<script type="text/javascript">
	alert("우린이제남 ~ 탈퇴완료");
	location.href="main.jsp";
	</script>
<% } else if (result==0){ %>
	<script type="text/javascript">
	alert("암호를 바로입력하시오");
	history.go(-1);
	</script>
	<% } else{ %>
		<script type="text/javascript">
	alert("유저가 다름?");
	history.go(-1);
	</script>
	<% }  %>
</body>
</html>