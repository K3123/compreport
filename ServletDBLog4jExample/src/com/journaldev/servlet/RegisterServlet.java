package com.journaldev.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static Logger logger = Logger.getLogger(RegisterServlet.class);
	
	private static boolean validation_Password(final String PASSWORD_Arg)    {
	    boolean result = false;
	    if ( PASSWORD_Arg.length() > 20 ) return false;
	    try {
	        if (PASSWORD_Arg!=null) {
	            //_________________________
	            //Parameteres
	            final String MIN_LENGHT="8";
	            final String MAX_LENGHT="20";
	            final boolean SPECIAL_CHAR_NEEDED=true;

	            //_________________________
	            //Modules
	            final String ONE_DIGIT = "(?=.*[0-9])";  //(?=.*[0-9]) a digit must occur at least once
	            final String LOWER_CASE = "(?=.*[a-z])";  //(?=.*[a-z]) a lower case letter must occur at least once
	            final String UPPER_CASE = "(?=.*[A-Z])";  //(?=.*[A-Z]) an upper case letter must occur at least once
	            final String NO_SPACE = "(?=\\S+$)";  //(?=\\S+$) no whitespace allowed in the entire string
	            //final String MIN_CHAR = ".{" + MIN_LENGHT + ",}";  //.{8,} at least 8 characters
	            final String MIN_MAX_CHAR = ".{" + MIN_LENGHT + "," + MAX_LENGHT + "}";  //.{5,10} represents minimum of 5 characters and maximum of 10 characters

	            final String SPECIAL_CHAR;
	            if (SPECIAL_CHAR_NEEDED==true) SPECIAL_CHAR= "(?=.*[@#$%^&+=])"; //(?=.*[@#$%^&+=]) a special character must occur at least once
	            else SPECIAL_CHAR="";
	            //_________________________
	            //Pattern
	            //String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	            final String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;
	            //_________________________
	            result = PASSWORD_Arg.matches(PATTERN);
	            //_________________________
	        }    

	    } catch (Exception ex) {
	        result=false;
	    }

	    return result;
	}       
	
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String country = request.getParameter("country");
		String errorMsg = null;
		String regexEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		String regexUsername = "^([\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*){7,15}$";
		String regexCountry = "^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$";
		Integer count = 0;
		
		Pattern pattern = Pattern.compile(regexEmail);
		Matcher matcherEmail = pattern.matcher(email);
		Pattern patternUN = Pattern.compile(regexUsername);
		Matcher matcherUsername = patternUN.matcher(name);
		Pattern patternCT = Pattern.compile(regexCountry);
		Matcher matcherCountry = patternCT.matcher(country);
		
		if ( email == null || email.equals("") || matcherEmail.matches() == false ) {
			errorMsg = "Email ID must be a valid email address.";
		}
		if ( password == null || password.equals("") || password.length() < 8) {
			errorMsg = "Password can't be null or empty.";
		}
		if ( password.length() < 8 || validation_Password(password) == false) {
			errorMsg = "Password not valid format. Password must be greater than 8 chars, include numbers, lower and upper case letters and special characters";
		}
		if ( name == null || name.equals("") ) {
			errorMsg = "Name can't be null or empty. ";
		}
		if ( matcherUsername.matches() == false ) {
			errorMsg = "Name should also only contain upper or lower case letters.";
		}
		if ( country == null || country.equals("")) {
			errorMsg = "Country can't be null or empty.";
		}
		if ( matcherCountry.matches() == false ) {
			errorMsg = "Country can only be uppercase and lowercase characters.";
		}
		if ( errorMsg != null ) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
			Cookie domainCookie = new Cookie("Set-Cookie","TestCookie" + String.valueOf(count));
			domainCookie.setComment("Set-Cookie");
			domainCookie.setMaxAge(60*60);
			domainCookie.setPath("/Servlet/RegisterServlet");					  
			domainCookie.setHttpOnly(true);
			domainCookie.setSecure(true);
			domainCookie.setDomain("localhost");
			response.addCookie(domainCookie);
			response.setHeader("Set-Cookie","key=value;HttpOnly;SameSite=strict");
			PrintWriter out = response.getWriter();
			out.println("<font color = red>" + errorMsg + "</font>");
			rd.include(request, response);
		}else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement("insert into Users ( name, email, country, password ) values (?,?,?,?)");
				ps.setString(1,name);
				ps.setString(2,email);
				ps.setString(3,country);
				ps.setString(4,password);
				ps.execute();
				logger.info("User registered with email=" + email);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				Cookie domainCookie = new Cookie("Set-Cookie","TestCookie" + String.valueOf(count));
				domainCookie.setComment("Set-Cookie");
				domainCookie.setMaxAge(60*60);
				domainCookie.setPath("/Servlet/RegisterServlet");					  
				domainCookie.setHttpOnly(true);
				domainCookie.setSecure(true);
				domainCookie.setDomain("localhost");
				response.addCookie(domainCookie);
				response.setHeader("Set-Cookie","key=value;HttpOnly;SameSite=strict");
				PrintWriter out = response.getWriter();
				out.println("<font color = green>Registeration successful, please login below. </font>");
				rd.include(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Database connection problem.");
				throw new ServletException("DB Connection problem.");
			} finally {
				try {
					ps.close();
					con.close();
				} catch ( SQLException e ) {
					logger.error("SQLException in closing PreparedStatement");
				}
			}
		}
	}

}
