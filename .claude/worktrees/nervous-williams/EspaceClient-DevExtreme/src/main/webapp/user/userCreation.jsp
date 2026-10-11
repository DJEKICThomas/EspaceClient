<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/user/userCreation.css"/>
    <jsp:include page="../template.jsp"/>
    <title>Création compte</title>
</head>
<body class="dx-viewport">
<div class="container">
    <h4>Nouveau compte utilisateur</h4>
    <div id="form"></div>
</div>
<script type="text/javascript" src="userCreation.js"></script>
</body>
</html>