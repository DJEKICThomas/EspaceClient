<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/user/user.css"/>
    <jsp:include page="../template.jsp"/>
        <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="" />
    </jsp:include>
    <title>Compte utilisateur</title>
</head>
<%
    String userId = request.getParameter("userId");
%>

<script type="text/javascript">
    let selectedUserId = <%=userId%>;
</script>
<body class="dx-viewport">
<div class="container">
    <h4>Compte utilisateur</h4>
    <div id="form"></div>
</div>
<script type="text/javascript" src="user.js"></script>
</body>
</html>