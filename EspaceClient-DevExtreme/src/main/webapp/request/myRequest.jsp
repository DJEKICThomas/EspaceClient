<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/request/myRequest.css"/>
    <jsp:include page="../template.jsp"/>
        <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="" />
    </jsp:include>
    <title>Suivi des demandes</title>
</head>
<body class="dx-viewport">
<h4>Suivi de mes demandes</h4>
<div class="container-fluid">
    <div id="gridContainer"></div>
    <div id="contact">
    </div>

</div>
<script type="text/javascript" src="myRequest.js"></script>
</body>
</html>