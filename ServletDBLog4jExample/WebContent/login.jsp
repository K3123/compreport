<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login Page</title>
</head>
<body>
<% 
Object name = request.getAttribute("csrfPreventionSalt");
out.println("Attribute Value : " + name);
%>

<h3> Login with email and password </h3>
<form action = "Login" method = "post">
<input type = "hidden" name = "csrfPreventionSalt" value ="< c:out value='${name}' />"/>
<strong> User Email </strong>:<input type = "text" name = "email"><br>
<strong> Password </strong>:<input type = "password" name = "password"><br>
<input type = "submit" value = "Login">
</form>
<br>
If you are a new user, please <a href="register.html">register</a>.
</body>
</html>