package com.journaldev.servlet.filters;

import java.io.IOException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;



/**
 * Servlet Filter implementation class LoadSalt
 */
@WebFilter("/LoadSalt")
public class LoadSalt implements Filter {

	private Logger logger = Logger.getLogger(LoadSalt.class);
	
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
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) 
				httpReq.getSession().getAttribute("csrfPreventionSaltCache");
		if ( csrfPreventionSaltCache == null ) {
			csrfPreventionSaltCache = CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(20, TimeUnit.MINUTES).build(
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
			httpReq.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);	
		}
		String salt = RandomStringUtils.random(20,0,0,true, true, null, new SecureRandom());
		csrfPreventionSaltCache.asMap().put(salt,Boolean.TRUE);
		httpReq.setAttribute("csrfPreventionSalt", salt);
        String X_Frame_Options = httpRes.getHeader("X-Frame-Options");
        if ( X_Frame_Options == null ) {
        	httpRes.addHeader("X-Frame-Options", "ALLOW-FROM http://localhost:8080");
        }

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
