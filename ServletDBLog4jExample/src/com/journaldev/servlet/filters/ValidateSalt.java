package com.journaldev.servlet.filters;

import com.google.common.cache.Cache;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class ValidateSalt
 */
@WebFilter("/ValidateSalt")
public class ValidateSalt implements Filter {

	private Logger logger = Logger.getLogger(ValidateSalt.class);
	
	
    /**
     * Default constructor. 
     */
    public ValidateSalt() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpServletResponse httpres = (HttpServletResponse) response;
		// pass the request along the filter chain
		String salt = (String) httpreq.getParameter("csrfPreventionSalt");
	    String X_Frame_Options = httpres.getHeader("X-Frame-Options");
	    String X_XSS_Protection = httpres.getHeader("X-XSS-Protection");
	    String X_CSRF_TOKEN = (String) httpres.getHeader("X-CSRF-TOKEN");
        if ( X_Frame_Options == null ) {
        	throw new ServletException("Potential CSRF detected !! Inform a scary sysadmin ASAP.");
        }
        if ( X_XSS_Protection == null ) {
        	throw new ServletException("Potential CSRF detected !! Inform a scary sysadmin ASAP.");
        }
		Cache<String,Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) httpreq.getSession().getAttribute("csrfPreventionSaltCache");
		if ( csrfPreventionSaltCache != null && salt != null && 
			 csrfPreventionSaltCache.asMap().get(salt).equals(true) &&
			 csrfPreventionSaltCache.asMap().get(X_CSRF_TOKEN).equals(true) ) {
			 System.out.println("Yes we did check the page for the token "); 
			 System.out.println("Salt was found " + csrfPreventionSaltCache.asMap().get(salt).toString());
			 System.out.println("CSRF Token was found " + csrfPreventionSaltCache.asMap().get(X_CSRF_TOKEN).toString());
			 chain.doFilter(httpreq, httpres);			
		} else {
			throw new ServletException("Potential CSRF detected !! Inform a scary sysadmin ASAP.");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
