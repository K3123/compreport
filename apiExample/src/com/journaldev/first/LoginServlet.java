package com.journaldev.first;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		description = "Login Servlet", 
		urlPatterns = { "/LoginServlet" }, 
		initParams = { 
				@WebInitParam(name = "user", value = "clarence"), 
				@WebInitParam(name = "password", value = "password")
		})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public void init() throws ServletException {
            if(   getServletContext().getInitParameter("dbURL").equals("jdbc:mysql://localhost/mysql_db")  && 
	        		getServletContext().getInitParameter("dbUser").equals("mysql_user")  && 
	        		getServletContext().getInitParameter("dbUserPwd").equals("mysql_pwd") ) {
	    	     getServletContext().setAttribute("DB_Success", "True");
	    	}else throw new ServletException("DB Connection Error");
    }

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		String userID = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");
		
		log("User="+user+"::password="+pwd);
		if(userID.equals(user) && password.equals(pwd)) {
			response.sendRedirect("jsps/LoginSuccess.jsp");
		}else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
			
		}
	}


}
