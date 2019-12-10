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
 * Servlet implementation class SetCookie
 */
@WebServlet("/cookie/SetCookie")
public class SetCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int count = 0;   
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
			out.write("<h3>Request Cookies:</h3>");
			for ( Cookie cookie : requestCookies ) {
				out.write("Name = " + cookie.getName() + ", Value = " + cookie.getValue() + ", comment = " + cookie.getComment() + 
						", Domain = " + cookie.getDomain() + ", MaxAge = " + cookie.getMaxAge() + ",  Path = " + cookie.getPath() + 
						", Version = " + cookie.getVersion());
				out.write("<br>");
			}
		}
		count++;
		Cookie counterCookie = new Cookie("Counter", String.valueOf(count));
		counterCookie.setComment("SetCookie Counter");
		counterCookie.setMaxAge(24*60*60);
		counterCookie.setPath("/ServletCookie/cookie/SetCookie");
		response.addCookie(counterCookie);
		Cookie domainCookie = new Cookie("Test","TestCookie" + String.valueOf(count));
		domainCookie.setComment("Test Cookie");
		response.addCookie(domainCookie);
		out.write("</body></html>");
	}

}
