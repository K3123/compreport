<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login Success Page</title>
</head>
<body>
<% 
String user = null;
if ( session.getAttribute("user") == null ){
	response.sendRedirect("login.html");
} else user = (String) session.getAttribute("user");

String UserName = null;
String sessionID = null;
Cookie[] cookies = request.getCookies();

if ( cookies != null ){
	for(Cookie cookie : cookies){
		if ( cookie.getName().equals("user")) UserName = cookie.getValue();
		if ( cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
		
	}
}else{ 
	sessionID = session.getId();
}
%>
<h3>Hi <%= UserName %>, Login successful. Your sessionID = <%= sessionID %></h3>
<br>
<a href = "<%= response.encodeUrl("CheckoutPage.jsp") %>">Checkout Page</a>
<form action = "<%= response.encodeUrl("LogoutServlet") %>" method = "post">
<input type="submit" value = "Logout">
</form>
</body>
</html>