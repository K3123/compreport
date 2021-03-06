package com.journaldev.servlet.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.lang.Exception;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AppExceptionHandler
 */
@WebServlet("/AppExceptionHandler")
public class AppExceptionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processError(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processError(request, response);
	}
	
	private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
				
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		if ( servletName == null ) {
			servletName = "Unknown";
		}
		
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");		
		if ( requestUri == null ) {
			requestUri = "Unknown";
		}
		
	    
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
	        out.println("<html><head>");
	 	    out.println("<title> Exception/Error Details </title></head><body>");
	 	    out.println("<h3>###################################################################</h3>");
	 	    out.println("<h3>###################################################################</h3>");
		    out.println("</body>");
		    out.println("</html>");
		
		    out.println("<html><head>");
			out.println("<title> Exception/Error Details </title></head><body>");	
			if ( statusCode != 500 ) {
				out.println("<h3>Error Details</h3>");
				out.println("<h3>Status Code : " + statusCode + "</h3>");
				out.println("<br>");
				out.println("<h3>Required URI :" + requestUri + "</h3>");
				out.println("<br>");
			} else {
				out.println("<h3>Exception Details</h3>");
				out.println("<ul>");
				out.println("<li>Servlet Name : " + servletName + "</li>");
				out.println("<li>Exception Name : " + throwable.getClass().getName() + "</li>");
				out.println("<li>Requested URI : " + requestUri.toString() + "</li>");
				out.println("<li>Exception Message : " + throwable.getMessage() + "</li>");
				out.println("</ul>");
			}
			out.println("<br>");
			out.println("<br>");
			out.println("<a href=\"index2.html\"> Home Page </a>" );
			out.println("</body>");
			out.println("</html>");

	        out.println("<html><head>");
	 	    out.println("<title> Exception/Error Details </title></head><body>");
	 	    out.println("<h3>###################################################################</h3>");
			out.println("<h3>###################################################################</h3>");
		    out.println("</body>");
		    out.println("</html>");
			
			
	}

}
