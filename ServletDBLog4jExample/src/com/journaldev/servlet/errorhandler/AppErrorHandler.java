package com.journaldev.servlet.errorhandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

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
	    String X_Frame_Options = response.getHeader("X-Frame-Options");
	    String X_XSS_Protection = response.getHeader("X-XSS-Protection");
	    String X_CSRF_TOKEN = response.getHeader("X-CSRF-TOKEN");

		Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) 
				request.getSession().getAttribute("csrfPreventionSaltCache");
	    
        if ( X_Frame_Options == null ) {
        	response.addHeader("X-Frame-Options", "ALLOW-FROM http://localhost:8181");
        }
        if ( X_XSS_Protection == null ) {
        	response.addHeader("X-XSS-Protection", "1; mode=block");
        }
        if ( X_CSRF_TOKEN == null ) {
			csrfPreventionSaltCache = CacheBuilder.newBuilder()
					.maximumSize(5000)
					.expireAfterWrite(20, TimeUnit.MINUTES)
					.build(
					new CacheLoader<String, Boolean>() {
		               	@Override
						public Boolean load(String arg0) throws Exception {
							// TODO Auto-generated method stub
							return createExpensiveGraph(arg0);
						}

						private Boolean createExpensiveGraph(String arg0) {
							// TODO Auto-generated method stub
							SecureRandom mystuff = new SecureRandom();
							return mystuff.nextBoolean();
						}
		         }		
			);

		   	String salt = RandomStringUtils.random(40,0,0,true, true, null, new SecureRandom());
			csrfPreventionSaltCache.asMap().put(salt,Boolean.TRUE);
		
			request.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);	
	      	response.addHeader("X-CSRF-TOKEN", salt);
	        
        }

         
		PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
 	    out.println("<title> Exception/Error Details </title>");
 	    out.println("</head>");
 	    out.println("<body>");
 	    out.println("<h3>###################################################################</h3>");
 	    out.println("<h3>###################################################################</h3>");
	    out.println("</body>");
	    out.println("</html>");		
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Exception/Error Details</title>");
		out.println("</head>");
		out.println("<body>");
		if (statusCode != 500 ) {
			out.println("<h3>Error Details</h3>");
			out.println("<strong>Status Code</strong>:" + statusCode + "<br>");
			out.println("<strong>Requested Uri</strong>:" + requestUri + "<br>");
		} else {
			out.println("<h3>Exception Details</h3>");
			out.println("<ul>");
			out.println("<li>Servlet Name: " + servletName + "</li>");
			out.println("<li>Exception Name: " + throwable.getClass().getName() + "</li>");
			out.println("<li>Requested Uri :" + requestUri + "</li>");
			out.println("<li>Exception Message: " + throwable.getMessage() + "</li>");
			out.println("</ul>");
			
		}
		
		out.println("<br/>");
		out.println("<br/>");
		out.println("<a href=\"login.jsp\">Login Page</a>");
		out.println("</body>");
	    out.println("</html>");
	
        out.println("<html>");
        out.println("<head>");
 	    out.println("<title> Exception/Error Details </title>");
 	    out.println("</head>");
 	    out.println("<body>");
 	    out.println("<h3>###################################################################</h3>");
 	    out.println("<h3>###################################################################</h3>");
	    out.println("</body>");
	    out.println("</html>");
	
		
	}

}
