package com.journaldev.servlet.datasource;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.io.PrintWriter;
import java.security.SecureRandom;

/**
 * Servlet implementation class JDBCDataSourceExample
 */
@WebServlet(name = "JDBCDataSourceExample", urlPatterns = {"/JDBCDataSourceExample"})
public class JDBCDataSourceExample extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest httpReq = (HttpServletRequest) request;
		Context ctx = null;
		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) 
				httpReq.getSession().getAttribute("csrfPreventionSaltCache");
  		String salt = RandomStringUtils.random(40,0,0,true, true, null, new SecureRandom());
		csrfPreventionSaltCache.asMap().put(salt,Boolean.TRUE);
		httpReq.setAttribute("csrfPreventionSalt", salt);   

		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		System.out.println(" yes we were called ");
		try {
			if ( con == null ) { 
				ctx = new InitialContext();
				BasicDataSource ds = ( BasicDataSource ) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
				con = ds.getConnection();	
			}			
			stmt = con.createStatement();
			rs = stmt.executeQuery("select empid, name from Employee");
			Cookie[] cookies = request.getCookies();
			Cookie domainCookie = new Cookie("Secure-Cookie","TestCookie" + String.valueOf(count));
			domainCookie.setComment("Secure-Cookie");
			domainCookie.setMaxAge(60*60);
			domainCookie.setPath("/Servlet/JDBCDataSourceExample");					  
			domainCookie.setHttpOnly(true);
			domainCookie.setSecure(true);
			domainCookie.setDomain("localhost");
			response.addCookie(domainCookie);
		    String X_Frame_Options = response.getHeader("X-Frame-Options");
		    String X_XSS_Protection = response.getHeader("X-XSS-Protection");
		    String X_CSRF_TOKEN = response.getHeader("X-CSRF-TOKEN");
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

				request.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);	
		      	response.addHeader("X-CSRF-TOKEN", salt);
		        
	        }

			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
			out.println("<html><head>");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=US-ASCII\">");
		    out.println("</head><body><h2>Employee Details </h2>");
			out.println("<table border = \"1\" cellspacing = 10 cellpadding = 5 >");
			out.println("<th>Employee ID </th>");
			out.println("<th>Employee Name </th>");
			while ( rs.next() ) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt("empid") + "</td>");
				out.println("<td>" + rs.getString("name") + "</td>");
				out.println("</tr>");		
			}
			out.println("</table></body><br/>");
			out.println("<h3> Database Details </h3>");
			out.println("Database Production : " + con.getMetaData().getDatabaseProductName() + "<br/> ");
			out.println("Database Driver : " + con.getMetaData().getDriverName() + "<br/> ");
			out.println("<form action = \"Logout\" method = \"post\" >");
			out.println("<input type = \"hidden\" name = \"csrfPreventionSalt\" value =\"< c:out value='" + salt + "' />\"/>");
			out.println("<input type = \"submit\" value = \"Logout\" >");
			out.println("</form>");
			out.println("<a href=\"home.jsp\">Home Page</a>");
			out.println("</html>");
		} catch ( SQLException e  ) {
			e.printStackTrace();
		} catch ( NamingException e ) {	
			e.printStackTrace();
		} finally {
			try {
				if ( rs == null ) rs.close();
				if ( stmt == null ) stmt.close();
				if ( con == null ) con.close();
		  	   } catch ( SQLException e ) {
		  		   System.out.println("Exception in closing DB resources. ");
			}
		}	
	}

}
