module ServletDBLog4jExample {
	exports com.journaldev.servlet.listeners;
	exports com.journaldev.servlet;
	exports com.journaldev.util;
	exports com.journaldev.servlet.errorhandler;
	exports com.journaldev.servlet.datasource;
	exports com.journaldev.servlet.filters;

	requires guava;
	requires java.naming;
	requires java.sql;
	requires java.xml;
	requires log4j;
	requires org.apache.commons.lang3;
	requires org.apache.tomcat.dbcp;
	requires servlet.api;
	requires jdk.compiler;
}