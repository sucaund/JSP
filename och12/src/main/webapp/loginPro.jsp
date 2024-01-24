
<%@page import="och12.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String id = request.getParameter("id");
	String passwd = request.getParameter("passwd");
	//객체 무한생성을 방지 -싱글톤!
	MemberDao md = MemberDao.getInstance();
	
	int result = md.check(id, passwd); //hw2
	//존재하는  USER ---> preparedststement 사용
	if(result ==1 ){
		session.setAttribute("id",id);
		response.sendRedirect("main.jsp");
		//password X
	}else if (result==0){
%>	
		
	}
<script type="text/javascript">
alert("암호몰라!");
history.go(-1);
</script>
<% }else { %>
<script type="text/javascript">
alert("user 미존재(-1) 넌누구니?");
history.go(-1);
</script>
<%} %>
</body>
</html>