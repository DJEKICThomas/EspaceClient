<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/request/impromentRequestDetailList.css"/>
    <jsp:include page="../template.jsp"/>
    <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_GESTION" />
    </jsp:include>
    <title>Gestion des demandes</title></head>
<body class="dx-viewport">
<h4>Gestion des demandes</h4>
<div class="container-fluid">
    <div id="formContainer"></div>
    <div id="gridContainer"></div>
</div>
<script type="text/javascript" src="impromentRequestDetailList.js"></script>
</body>
</html>