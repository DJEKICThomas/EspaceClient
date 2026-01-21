<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dotation/dotation.css"/>
    <jsp:include page="../template.jsp"/>
        <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_DOTATION" />
    </jsp:include>
    <title>Dotations</title></head>
<body class="dx-viewport">
<h4>Traçabilité</h4>
<div class="container-fluid">
    <div id="gridContainer"></div>
</div>
<script type="text/javascript" src="dotation.js"></script>
