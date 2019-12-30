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
		// pass the request along the filter chain
		String salt = (String) httpreq.getAttribute("csrfPreventionSalt");
		Cache<String,Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) httpreq.getSession().getAttribute("csrfPreventionSaltCache");
		if ( csrfPreventionSaltCache != null && salt != null && 
			  csrfPreventionSaltCache.asMap().get(salt) != null ) {
			  chain.doFilter(request, response);
			
		} else {
			throw new ServletException("Potential CSRF detected !! Inform a scary sysadmin ASAP.");
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
