<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/password/resetPassword.css"/>
    <jsp:include page="../template.jsp"/>
    <title>Mot de passe oublié</title>
    <% String webUserId = StringUtils.defaultIfBlank(request.getParameter("userid"), ""); %>
    <script type="text/javascript">
        const webUserId = '<%= webUserId %>';
    </script>
</head>
<body class="dx-viewport">
<h4>Réinitialisation</h4>
<div class="container-fluid">
    <form name="resetForm" method="post" action="javascript:doReset()">
        <div id="resetPasswordForm"></div>
    </form>
    <div id="infopopup"></div>
</div>
<script type="text/javascript" src="resetPassword.js"></script>