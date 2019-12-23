package com.journaldev.servlet.errorhandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AppErrorHandler
 */
@WebServlet("/AppErrorHandler")
public class AppErrorHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppErrorHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		processError(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		processError(request, response);
	}
	
	private void processError(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		
		if( servletName == null ) {
			servletName = "Unknown";
		}
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if( requestUri == null ) {
			requestUri = "Unknown";
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head><title>Exception/Error Details</title></head><body>");
		if (statusCode != 500 ) {
			out.write("<h3>Error Details</h3>");
			out.write("<strong>Status Code</strong>:" + statusCode + "<br>");
			out.write("<strong>Requested Uri</strong>:" + requestUri + "<br>");
		} else {
			out.write("<h3>Exception Details</h3>");
			out.write("<ul>");
			out.write("<li>Servlet Name: " + servletName + "</li>");
			out.write("<li>Exception Name: " + throwable.getClass().getName() + "</li>");
			out.write("<li>Requested Uri :" + requestUri + "</li>");
			out.write("<li>Exception Message: " + throwable.getMessage() + "</li>");
			out.write("/<ul>");
			
		}
		
		out.write("<br><br>");
		out.write("<a href=\"login.html\">Login Page</a>");
		out.write("</body></html>");
	}

}
