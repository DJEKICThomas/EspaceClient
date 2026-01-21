<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/request/improvementRequest.css"/>
    <jsp:include page="../template.jsp"/>
        <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="" />
    </jsp:include>
    <title>Améliorations</title></head>
<body class="dx-viewport">
<div class="container">
    <h4 id="title">Amélioration</h4>
    <p>Dans un souci d'évolution constante vous pouvez utiliser cet écran pour nous faire part de vos idées d'amélioration.</p>
    <p>En vous remerciant,</p>
    <p><i><strong>L'équipe Depairon</strong></i></p>
    <div id="contact">
        <div id="deTextArea"></div>
        <div id="deButton"></div>
    </div>
</div>
<script type="text/javascript" src="improvementRequest.js"></script>
</body>
</html>