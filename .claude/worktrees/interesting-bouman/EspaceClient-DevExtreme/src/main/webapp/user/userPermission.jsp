<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/user/userPermission.css"/>
    <jsp:include page="../template.jsp"/>
    <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_USERMANAGEMENT" />
    </jsp:include>
    <title>Permissions utilisateur</title>
</head>
<%
    String userEmail = request.getParameter("userEmail");
%>

<script type="text/javascript">
    let selectedUserEmail = '<%= userEmail %>';
</script>
<body class="dx-viewport">
<div class="container-fluid">
    <h4>Permissions utilisateur (<%= userEmail %>)</h4>
    <div id="permissionList"></div>
</div>
<script type="text/javascript" src="userPermission.js"></script>
</body>
</html>