<%@ page language="java" contentType="text/html; charset=US-ACCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login Success Page</title>
</head>
<body>
<% 

String userName = null;
if ( session.getAttribute("user") == null ){
	response.sendRedirect("login.html");
}else userName = (String) session.getAttribute("user");

String sessionID = null;
Cookie[] cookies = request.getCookies();
if ( cookies != null ){
	for(Cookie cookie : cookies){
		if ( cookie.getName().equals("user")) userName = cookie.getValue();
		break;
	}
}
%>
<h3>Hi <%= userName  %>, do the checkout. </h3>
<br>
<form action = "<%= response.encodeUrl("LogoutServlet") %>" method = "post">
<input type = "submit" value = "Logout">
</form>
</body>
</html>