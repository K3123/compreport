<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://github.com/dandelion/datatables" prefix="datatables" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Compliance Report - Request Statuses</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href="<c:url value="/bootstrap/css/bootstrap-responsive.min.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/bootstrap/css/cerulean.bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/css/stickyfooter.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/css/jquery.dataTables.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/css/custom.css" />" rel="stylesheet"  type="text/css" />

    <script type="text/javascript" src="<c:url value="/js/json2.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-1.9.0.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
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
                <!-- HEADER
                <header>
                    <div class="page-header">
                        <h1><spring:message code="welcome.header"/></h1>
                        <p><spring:message code="welcome.paragraph"/></p>
                    </div>
                </header> -->
                <!-- BODY -->
                <div id="content">
                    <!-- if the list is empty display a message but not the table -->
                    <c:if test="${empty requestList}">
                        <div class="alert">
                            <strong>No results found!</strong>
                        </div>
                    </c:if>

                    <!-- if list is not empty display the table -->
                    <c:if test="${not empty requestList}">

                        <datatables:table id="myTableId" data="${requestList}" row="req" theme="bootstrap2"
                            lengthChange="true" paginationType="bootstrap" autoWidth="false" export="pdf,xls,csv"
                            exportLinks="top_right" cssClass="table table-striped table-bordered table-condensed">
                            <datatables:column title="Id" property="id"/>
                            <datatables:column title="Name" property="databaseName" sortInit="asc"  filterable="true"/>
                            <datatables:column title="Status" property="requestStatus"/>
                            <datatables:column title="Report Type" property="comReportType"/>
                            <datatables:column title="Usage" property="usage"/>
                            <datatables:column title="Report Date" property="reportDateStr" />
                            <datatables:column title="SSO ID" property="ssoId"/>
                            <datatables:column title="Mail">
                                <a href="mailto:${req.emailAddress}">${req.emailAddress}</a>
                            </datatables:column>
                            <datatables:column title="Virtual SQL Instance" property="virtualSqlInstance" />
                            <datatables:export type="xls" includeHeader="true" fileName="xsl-export" cssClass="btn-mini btn-info" label="XLS"/>
                            <datatables:export type="pdf" includeHeader="true" fileName="pdf-export" cssClass="btn-mini btn-success" label="PDF"/>
                            <datatables:export type="csv" includeHeader="true" fileName="csv-export" cssClass="btn-mini btn-warning" label="CSV"/>
                        </datatables:table>


                    </c:if>
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









