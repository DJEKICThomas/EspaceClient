<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/statistique/statistique.css"/>
    <jsp:include page="../template.jsp"/>
    <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_STATISTIC"/>
    </jsp:include>
    <title>Statistiques</title></head>
<body class="dx-viewport">
<h4>Statistiques</h4>
<div class="container-fluid">
    <div id="natureSelector"></div>
    <div id="gridContainer"></div>
</div>
<script type="text/javascript" src="statistique.js"></script>