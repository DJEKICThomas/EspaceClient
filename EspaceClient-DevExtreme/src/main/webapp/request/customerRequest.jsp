<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/request/request.css"/>
    <jsp:include page="../template.jsp"/>
        <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_SPECIFICREQUEST" />
    </jsp:include>
    <title>Demandes clients</title></head>
<body class="dx-viewport">
<h4>Situation des demandes</h4>
<div class="container-fluid">
    <div id="gridContainer"></div>
</div>
<script type="text/javascript" src="customerRequest.js"></script>
</body>
</html>