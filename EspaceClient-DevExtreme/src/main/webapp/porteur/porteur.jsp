<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/porteur/porteur.css"/>
    <jsp:include page="../template.jsp"/>
    <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_NOMINATIVEMANAGEMENT"/>
    </jsp:include>
    <title>Porteurs</title>
</head>
<body class="dx-viewport">
<h4>Porteurs</h4>
<div class="container-fluid">
    <div id="porteur_popup">
        <p>Nouvelle Demande</p>
        <div id="request_message"></div>
    </div>
    <div id="gridContainer"></div>
</div>
<script type="text/javascript" src="porteur.js"></script>