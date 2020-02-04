<%@ page import = "com.journaldev.util.User" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Home Page</title>
</head>
<body>
<% User user = (User) session.getAttribute("User"); 
  if ( user == null ) response.sendRedirect("login.html"); %>
<% System.out.println("This is the name " + user.getName()); %>
<h3>Hi <%= user.getName() %></h3>
<strong> Your Email</strong>: <%= user.getEmail() %><br>
<strong> Your Country</strong>: <%= user.getCountry() %><br>
<br>
<form action = "Logout" method = "post" >
<input type = "hidden" name = "csrfPreventionSalt" value ="< c:out value='${csrfPreventionSalt}' />"/>
<input type = "submit" value = "Logout" >
</form>
<form action = "JDBCDataSourceExample" method = "post" >
<input type = "submit" value = "Print" >
</form>
<a href="CheckoutPage.jsp">Checkout Page</a>
</body>
</html>