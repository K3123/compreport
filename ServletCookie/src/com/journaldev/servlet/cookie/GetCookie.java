package com.journaldev.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetCookie
 */
@WebServlet("/cookie/GetCookie")
public class GetCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Cookie[] requestCookies = request.getCookies();
		out.write("<html><head></head><body>");
		out.write("<h3> Hello Browser !! </h3>");
		if ( requestCookies != null ) {
			out.write("<h3> Request Cookies: </h3>");
			for ( Cookie cookie : requestCookies ) {
				out.write("Name = " + cookie.getName() + ", Value = " + cookie.getValue() + ", Comment = " + cookie.getComment() + 
						", Domain = " + cookie.getDomain() + ", MaxAge = " + cookie.getMaxAge() + ", Path = " + 
						cookie.getPath() + ", Version = " + cookie.getVersion());
				out.write("<br>");
				if ( cookie.getName().equals("Test")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		out.write("</body></html>");
	}

}
