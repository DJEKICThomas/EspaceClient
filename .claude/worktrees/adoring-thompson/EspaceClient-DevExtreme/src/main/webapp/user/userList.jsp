<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/user/userList.css"/>
    <jsp:include page="../template.jsp"/>
    <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_USERLIST"/>
    </jsp:include>
    <title>Utilisateurs</title>
</head>
<body class="dx-viewport">
<div class="container-fluid">
    <h4>Utilisateurs</h4>
    <div id="user_list"></div>
</div>
<script type="text/javascript" src="userList.js"></script>
</body>
</html>