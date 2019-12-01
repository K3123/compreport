package com.clics.compliancereport.web.interceptor;

import com.clics.compliancereport.common.Constants;
import com.clics.compliancereport.domain.UserCredential;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);
    public static final String FIRST_NAME = "givenname";
    public static final String LAST_NAME = "sn";
    public static final String SSO_ID = "ssoid";
    public static final String EMAIL_ADDRESS = "mail";
    public static final String GROUP_ID = "groupid";
    private UserCredential credential;
    private boolean bFakeUserActivated;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws ServletException, IOException {

        // credentials need to be retrieved from request header unless the app is not protected from Siteminder
        //in which case a fake user credential is used
        if(!bFakeUserActivated) {
           setCredential(UserCredential.getBuilder(request.getHeader(SSO_ID), request.getHeader(EMAIL_ADDRESS))
                                       .name(formatFullName(request))
                                       .groupid(request.getHeader(GROUP_ID)).build());
        }else{
           setCredential(UserCredential.getBuilder(Constants.FAKE_SSO_ID, Constants.FAKE_EMAIL)
                                      .name(Constants.FAKE_FULLNAME)
                                      .groupid(Constants.ADMIN_USER_GROUP).build());
        }

        //pass credential to controllers that need it
        request.setAttribute(Constants.CREDENTIAL_KEY, credential);

        //check if user is authenticated
        boolean bAuthenticated = credential.isAutenticated();

        //check if user is authorized (belong to a group)
        boolean bAuthorized = credential.isAuthorized();

        LOG.info("<<<<< User Authenticated? : {}", bAuthenticated);
        LOG.info("<<<<< User Authorized?: {}", bAuthorized);
        LOG.debug("<<<<< Credential: {}", credential);

        //if user is not authenticated or authorized they will be sent to error page
        if (!(bAuthenticated && bAuthorized)) {
            LOG.debug("\n<<<<<<<<< USER IS NOT AUTHENTICATED, NO SSO CREDENTIALS FOUND...");
            return sendToErrorPage(request, response);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse,
                           Object handler, ModelAndView modelAndView)  throws ServletException, IOException  {
        if (modelAndView != null && modelAndView.getModelMap() != null) {
           modelAndView.getModelMap().addAttribute(Constants.CREDENTIAL_KEY, credential);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                Object handler, Exception e)  throws ServletException, IOException {

    }


    private String formatFullName(HttpServletRequest request) {
        StringBuilder fullname = new StringBuilder();
        if(StringUtils.isNotEmpty(request.getHeader(FIRST_NAME))) {
            fullname.append(request.getHeader(FIRST_NAME));
        }
        if(StringUtils.isNotEmpty(request.getHeader(LAST_NAME))) {
            fullname.append(" ").append(request.getHeader(LAST_NAME));
        }
        return fullname.toString();
    }


    private boolean sendToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/error");
        rd.forward(request, response);
        return false;
    }


    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }


    public void setbFakeUserActivated(boolean bFakeUserActivated) {
        this.bFakeUserActivated = bFakeUserActivated;
    }
}
