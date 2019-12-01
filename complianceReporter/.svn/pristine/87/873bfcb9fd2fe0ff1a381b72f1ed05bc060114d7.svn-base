<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <title>Compliance Report</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href="<c:url value="/bootstrap/css/bootstrap-responsive.min.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/bootstrap/css/cerulean.bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/css/stickyfooter.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/select2/select2.css" />" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/css/custom.css" />" rel="stylesheet"  type="text/css" />

    <script type="text/javascript" src="<c:url value="/js/json2.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-1.9.0.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/select2/select2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap.min.js" />"></script>


    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="<c:url value="/js/html5shiv.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/html5shiv-printshiv.js"/>"></script>
    <![endif]-->

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
    </div><br/><br/>

    <!-- CONTAINER -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <!-- HEADER -->
                <header>
                    <div class="page-header">
                        <h1><spring:message code="welcome.header"/></h1>
                        <p><spring:message code="welcome.paragraph"/></p>
                    </div>
                </header>
                <!-- BODY -->
                <div id="content">
                    <form:form id="requestForm" method="post" modelAttribute="formBean" action="/saverequest" cssClass="form-vertical">
                        <fieldset>
                            <legend>Request Details</legend>
                            <c:if test="${not empty success}">
                                <div  class="row-fluid">
                                    <div class="span6">
                                        <div id="success" class="alert alert-success alert-block">
                                            <a class="close" data-dismiss="alert">&times;</a>
                                            <h4 class="alert-heading">Success!</h4>${success}
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <spring:bind path="*">
                                <c:if test="${status.error}">
                                    <div  class="row-fluid">
                                        <div class="span6">
                                            <div id="errors" class="alert alert-error alert-block">
                                                <a class="close" data-dismiss="alert">&times;</a>
                                                <h4 class="alert-heading"><span class="label label-important">Error(s):</span></h4>
                                                <form:errors path="*"/>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </spring:bind>
                            <c:choose>
                            <c:when test="${not empty user_credential && user_credential.groupid == '@Capital comprepadmin'}">
                            <div  class="row-fluid">
                                <div class="span6">
                                    <div id="message1" class="alert alert-info alert-block">
                                        <h4 class="alert-heading"><span class="label label-info">Available Options:</span></h4>
                                        You have 2 options, you can choose to generate a report for all databases selecting
                                        the checkbox below or you can use the search field to search for one or more database.
                                    </div>
                                </div>
                            </div>
                            </c:when>
                            <c:otherwise>
                                <div  class="row-fluid">
                                    <div class="span5">
                                        <div id="message2" class="alert alert-info alert-block">
                                            <h4 class="alert-heading"><span class="label label-info">Available Options:</span></h4>
                                            You can use the search field to search for one or more database.
                                        </div>
                                    </div>
                                </div>
                            </c:otherwise>
                            </c:choose>
                            <c:if test="${not empty user_credential && user_credential.groupid == '@Capital comprepadmin'}">
                            <div class="row-fluid">
                                <div class="span11">
                                    <div class="control-group">
                                        <div class="controls">
                                            <label class="checkbox">
                                                <form:checkbox id="bAllDatabases" path="bAllDatabases"/>
                                                Report for All databases
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </c:if>
                            <br/>
                            <div id="searchBlock" class="row-fluid">
                                <div class="span4">
                                    <div class="control-group">
                                        <div class="controls">
                                            <form:label path="name">Database Search (<a id="clear_search" href="#">Clear Search</a>)</form:label>
                                            <form:input id="name" class="input-xxlarge bigdrop" path="name"/>
                                            <form:errors path="name" cssClass="error" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="row-fluid">

                                <div class="span3">
                                    <div class="control-group">
                                        <div class="controls">
                                            <form:label class="control-label" path="reportType">Report Type</form:label>
                                                <form:select id="reportType" class="input-xlarge" required="true" path="reportType">
                                                    <form:option value="" selected="selected">---- Please Select ----</form:option>
                                                    <c:forEach var="type" items="${formBean.reportTypes}">
                                                        <form:option value="${type.value}">${type.label}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="reportType" cssClass="error" />

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <div class="well well-small">
                            <form:button type="submit" id="requestSubmitBtn" class="btn btn-primary" data-loading-text="Processing please wait...">Process Request</form:button>&nbsp;&nbsp;&nbsp;
                            <a id="resetBtn" class="btn" href="<c:url value="/requestform"/>">Reset</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <div id="push"></div>

</div>
<div>
    <!-- FOOTER -->
    <footer id="footer">
        <p class="text-center"><small>Copyright &copy; 2012 <a href="http://www.gecapital.com/en/">GE Capital</a> All rights reserve.</small></p>
    </footer>
</div>


<script type="text/javascript" src="<c:url value="/js/custom.js" />"></script>

<script type="text/javascript">
    var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>



<script>
    $(document).ready(function(){
        initialize();
    });
</script>

<script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap-button.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap-tooltips.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap-modal.min.js" />"></script>




</body>
</html>



