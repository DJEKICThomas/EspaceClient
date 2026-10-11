<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/password/forgotPassword.css"/>
    <jsp:include page="../template.jsp"/>
    <title>Mot de passe oublié</title>
</head>
<body class="dx-viewport">
<h4>Mot de passe oublié</h4>
<div class="container-fluid">
    <form method="post" action="javascript:doSubmit()">
        <div id="forgotPasswordForm"></div>
    </form>
    <div id="infopopup"></div>
</div>
<script type="text/javascript" src="forgotPassword.js"></script>