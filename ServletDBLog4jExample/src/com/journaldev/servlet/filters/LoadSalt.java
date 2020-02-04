package com.journaldev.servlet.filters;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
	
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

import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
/**
 * Servlet Filter implementation class LoadSalt
 */
@WebFilter("/LoadSalt")
public class LoadSalt implements Filter {

    /**
     * Default constructor. 
     */
    public LoadSalt() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
    @Override   
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String X_Frame_Options = res.getHeader("X-Frame-Options");
	    String X_XSS_Protection = res.getHeader("X-XSS-Protection");
	    String X_Content_Type_Options = req.getHeader("X-Content-Type-Options");
        if ( X_Frame_Options == null ) {
        	res.addHeader("X-Frame-Options", "ALLOW-FROM http://localhost:8181");
        }
        if ( X_XSS_Protection == null ) {
        	res.addHeader("X-XSS-Protection", "1; mode=block");
        }
        if ( X_XSS_Protection == null ) {
        	res.addHeader("X-Content-Type-Options", "nosniff");
        }
    
		Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) 
				req.getSession().getAttribute("csrfPreventionSaltCache");
		if ( csrfPreventionSaltCache == null ) {
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
			req.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
			System.out.println("Yes we were called to create a Salt Cache");
		}

		String salt = RandomStringUtils.random(40,0,0,true, true, null, new SecureRandom());
		csrfPreventionSaltCache.asMap().put(salt,Boolean.TRUE);
		req.setAttribute("csrfPreventionSalt", salt);
	
		res.addHeader("X-CSRF-TOKEN", salt);
		System.out.println("LoadSalt was called");
		// pass the request along the filter chain
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
