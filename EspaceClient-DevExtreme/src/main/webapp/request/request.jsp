<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/request/request.css"/>
    <jsp:include page="../template.jsp"/>
        <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_FILTRAGE" />
    </jsp:include>
    <title>Filtrage des demandes</title></head>
<body class="dx-viewport">
<h4>Filtrage des demandes</h4>
<div class="container-fluid">
    <div id="gridContainer"></div>
    <div id="contact">
    </div>

</div>
<script type="text/javascript" src="request.js"></script>
</body>
</html>