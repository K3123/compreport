package com.journaldev.servlet.listeners;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.journaldev.util.DBConnectionManager;
/**
 * Application Lifecycle Listener implementation class AppContextListener
 *
 */
@WebListener
public class AppContextListener implements ServletContextListener {

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	 Connection con = (Connection) sce.getServletContext().getAttribute("DBConnection");
    	 try {
    		  con.close();
    	 } catch ( SQLException e ) {
    		 e.printStackTrace();
    	 }
    	
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	ServletContext ctx = sce.getServletContext();
    	String dbURL = ctx.getInitParameter("dbURL");
    	String user = ctx.getInitParameter("dbUser");
    	String pwd = ctx.getInitParameter("dbPassword");
    	
    	try {
    		      DBConnectionManager connectionManager = new DBConnectionManager(dbURL, user, pwd);
    		      ctx.setAttribute("DBConnection", connectionManager.getConnection());
    		      System.out.println("DB Connection initialized successfully.");
    	} catch ( ClassNotFoundException e ) {
    		e.printStackTrace();
    		
    	} catch ( SQLException e ) {
    		e.printStackTrace();
    	}
    	
       String log4jConfig = ctx.getInitParameter("log4j-config");
       if ( log4jConfig == null ) {
    	   System.err.println("No log4j-config init param, initializing log4j with BasicConfigurator");
    	   BasicConfigurator.configure();
       } else {
    	   String webAppPath = ctx.getRealPath("/");
    	   String log4jProp = webAppPath + log4jConfig;
    	   File log4jConfigFile = new File(log4jProp);
    	   if ( log4jConfigFile.exists() ) {
    		   System.out.println("Initialzing log4j with: " + log4jProp );
    		   DOMConfigurator.configure(log4jProp);
    	   } else {
    		   System.err.print(log4jProp + " file not found, initializing log4j with BasicConfigurator");
    		   BasicConfigurator.configure();
    	   }
       }
    	System.out.println("log4j configured properly");
    }
	
}
