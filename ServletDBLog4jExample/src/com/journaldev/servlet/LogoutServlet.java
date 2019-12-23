package com.journaldev.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet( name = "Logout", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(LogoutServlet.class);   
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		Cookie[] cookies = request.getCookies();
		if ( cookies != null ) {
		  for(Cookie cookie : cookies ) {
			  if(cookie.getName().equals("JSESSIONID")) {
				  logger.info("JESSIONID=" + cookie.getValue());
				  break;
			  }
		  }
		}
		
		HttpSession session = request.getSession(false);
		logger.info("User=" + session.getAttribute("User"));
		if ( session != null ) {
			session.invalidate();
		}
		response.sendRedirect("login.html");
	}

}
