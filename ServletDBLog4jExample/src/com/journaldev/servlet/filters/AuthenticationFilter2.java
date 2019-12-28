package com.journaldev.servlet.filters;

import java.io.IOException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.io.IOException;
import java.security.SecureRandom;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class AuthenticationFilter2
 */
@WebFilter("/AuthenticationFilter2")
public class AuthenticationFilter2 implements Filter {

	private Logger logger = Logger.getLogger(AuthenticationFilter2.class);
	

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
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) req.getSession().getAttribute("csrfPreventionSaltCache");
        String uri = req.getRequestURI();
        logger.info("Requested resource::" + uri);
        HttpSession session = req.getSession(false);
        if ( session == null && !(uri.endsWith("html") || uri.endsWith("Login") || uri.endsWith("Register"))) {
        	logger.error("Unauthorized access request");
        	res.sendRedirect("login.html");
        }else if (csrfPreventionSaltCache == null ) {
        	csrfPreventionSaltCache = CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(20,TimeUnit.MINUTES).build(null);
        	req.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
        	String salt = RandomStringUtils.random(20,0,0,true,true,null, new SecureRandom());
        	csrfPreventionSaltCache.asMap().put("csrfPreventionSaltCache", Boolean.TRUE);
        	req.setAttribute("csrfPreventionSaltCache", salt);
        	chain.doFilter(request, response);
	    } else {
    		// pass the request along the filter chain
    		chain.doFilter(request, response);
        	
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		logger.info("AuthenticationFilter2 initialized");
	}

}
