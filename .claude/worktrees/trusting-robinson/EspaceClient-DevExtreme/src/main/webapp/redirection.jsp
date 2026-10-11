<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!doctype html>
<html lang="fr">
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <jsp:include page="template.jsp"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Redirection</title>
</head>
<script>
    $(() => {
        let username = "<%=StringUtils.defaultIfBlank(request.getParameter("username"),"")%>";
        if (username) localStorage.setItem('username', username);
        let userId = "<%=StringUtils.defaultIfBlank(request.getParameter("userId"),"")%>";
        if (userId) localStorage.setItem('userId', userId);
        let profile = "<%=StringUtils.defaultIfBlank(request.getParameter("profile"),"")%>";
        if (profile) localStorage.setItem('profile', profile);
        let selectedCustomer = "<%=StringUtils.defaultIfBlank(request.getParameter("selectedCustomer"),"")%>";
        if (selectedCustomer) localStorage.setItem("selectedCustomer", selectedCustomer);
        location.href = location.origin + "/EspaceClient/customer/customer.jsp";
    });
</script>
<body>
</body>
</html>
