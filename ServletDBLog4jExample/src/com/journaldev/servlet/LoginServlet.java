package com.journaldev.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.journaldev.util.User;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	static Logger logger = Logger.getLogger(LoginServlet.class);
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String errorMsg = null;
		Integer count = 0;
		System.out.println("Yes we did call the login servlet");
		if ( email == null || email.equals("")) {
			errorMsg = "User email can't be null or empty";
		}
		if ( password == null || password.equals("")) {
			
			errorMsg = "User password can't be null or empty";
		}
		if(errorMsg != null ) {
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			Cookie domainCookie = new Cookie("Set-Cookie","TestCookie" + String.valueOf(count));
			domainCookie.setComment("Set-Cookie");
			domainCookie.setMaxAge(60*60);
			domainCookie.setPath("/Servlet/LoginServlet");
			domainCookie.setHttpOnly(true);
			domainCookie.setSecure(true);
			domainCookie.setDomain("localhost");
			response.addCookie(domainCookie);
			response.setHeader("Set-Cookie","key=value;HttpOnly;SameSite=strict");
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				
				  ps = con.prepareStatement("Select id, name, email, country from Users where email = ? and password = ? limit 1");
				  ps.setString(1, email);
				  ps.setString(2, password);
				  rs = ps.executeQuery();
				  if ( rs != null && rs.next()) {
					  User user = new User(rs.getString("name"),rs.getString("email"), rs.getString("country"),rs.getInt("id"));
					  logger.info("User found wuth details=" + user);
					  HttpSession session = request.getSession();
					  session.setAttribute("User", user);
					  session.setMaxInactiveInterval(30*60);
					  String salt = RandomStringUtils.random(60,0,0,true, true, null, new SecureRandom());
					  session.setAttribute("csrfToken",generateCSRFToken(salt));
					  Cookie domainCookie = new Cookie("Set-Cookie","TestCookie" + String.valueOf(count));
					  domainCookie.setComment("Set-Cookie");
					  domainCookie.setMaxAge(60*60);
					  domainCookie.setPath("/Servlet/LoginServlet");					  
					  domainCookie.setHttpOnly(true);
					  domainCookie.setSecure(true);
					  domainCookie.setDomain("localhost");
					  response.addCookie(domainCookie);
					  response.setHeader("Set-Cookie","key=value;HttpOnly;SameSite=strict");
					  response.sendRedirect("home.jsp");
				  } else {
					  RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
					  PrintWriter out = response.getWriter();
					  logger.error("User not found with email=" + email);
					  out.println("<font color = red>No user found with email id, please register first. </font>");
					  rd.include(request, response);
				  }
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			} finally {
				 	try {
				 		if ( rs == null ) rs.close();
				 		if ( ps == null ) ps.close();
				 		if ( con == null ) con.close();
				 		
				 	} catch (SQLException e) {
				 		logger.error("SQLException in closing. PreparedStatement or Resultset");
				 }
			}
		}
	}

	private String generateCSRFToken(String arg0) {
		SecureRandom mystuff = new SecureRandom(arg0.getBytes());
		return mystuff.toString();
	}

}
