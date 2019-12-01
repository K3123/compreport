<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Compliance Report - Request Statuses</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href="<c:url value="/bootstrap/css/bootstrap-responsive.min.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/bootstrap/css/cerulean.bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/css/stickyfooter.css" />" rel="stylesheet"  type="text/css" />

    <script type="text/javascript" src="<c:url value="/js/json2.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-1.9.0.min.js" />"></script>
</head>

<body>
<div id="wrap">
    <!-- NAVBAR -->
    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <a class="brand" href="<c:url value="/"/>">Compliance Report Requestor</a>
                <ul class="nav">
                    <li><a href="<c:url value="/"/>" title="forms"><i class="icon-chevron-right"></i>Home</a></li>
                    <li class="divider"></li>
                    <li><a href="<c:url value="/requeststatus"/>" title="statuses"><i class="icon-chevron-right"></i>Request Status</a></li>
                    <li class="divider"></li>
                    <li><a href="<c:url value="/logout"/>" title="statuses"><i class="icon-chevron-right"></i>logout</a></li>
                </ul>
            </div>
        </div>
    </div><br/><br/><br/>

    <!-- CONTAINER -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <header>
                    <div class="page-header">
                        <h1><spring:message code="welcome.header"/></h1>
                        <p><spring:message code="welcome.paragraph"/></p>
                    </div>
                </header>
                <!-- BODY -->
                <div id="content">
                    <div class="span6">
                        <div id="message" class="alert alert-block alert-error">
                            <h4 class="alert-heading"><span class="label label-important">Authentication Related Error:</span></h4>
                            If you are seeing this message it means your session timed out or you don't have access to this application.
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="push"></div>
</div>

<div class="row-fluid">
    <!-- FOOTER -->
    <footer id="footer">
        <p class="text-center"><small>Copyright &copy; 2012 <a href="http://www.gecapital.com/en/">GE Capital</a> All rights reserve.</small></p>
    </footer>
</div>

</body>
</html>