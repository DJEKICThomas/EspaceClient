<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/request/newRequest.css"/>
    <jsp:include page="../template.jsp"/>
    <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value=""/>
    </jsp:include>
    <title>Nouvelle Demande</title>
    <%
        String carrier = StringUtils.defaultIfBlank(request.getParameter("carrier"), "");
        String ncsId = StringUtils.defaultIfBlank(request.getParameter("ncsId"), "");
    %>
    <script type="text/javascript">
        let recievedCarrier = "<%=carrier%>";
        let recievedNcsId = <%=ncsId%>;
    </script>
</head>
<body class="dx-viewport">
<h4>Nouvelle Demande</h4>
<div class="container-fluid">
    <div id="request_form"></div>
</div>
<script type="text/javascript" src="newRequest.js?v=2"></script>
</body>
</html>