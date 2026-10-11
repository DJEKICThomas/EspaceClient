<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!doctype html>
<html lang="en">
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/login/login.css"/>
    <jsp:include page="../template.jsp"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Connexion</title>
</head>
<body>
<div class="container-fluid">
    <div id="infopopup"></div>
    <div>
        <div class="login-form">
            <div>
                <img id="logo" src="../static/images/depairon-logo.jpg"/>
            </div>
            <div class="login-form-content">
                <h3>Espace client</h3>
                <form id="loginForm">
                    <div class="form-group">
                        <label>Nom d'utilisateur
                            <input id="username" type="text" class="form-control" placeholder="" required>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Mot de passe
                            <input id="password" type="password" class="form-control" placeholder="" required>
                        </label>
                    </div>
                    <button type="submit" class="btn btn-success">Connexion</button>
                    <button onclick="location.href = location.origin + '/EspaceClient/user/userCreation.jsp';"
                            class="btn btn-secondary">S'inscrire
                    </button>
                </form>
                <a href="../password/forgotPassword.jsp">Mot de passe oublié<br> </a>
            </div>
        </div>
    </div>
</div>
<script>
    $(() => {
        const notify = '<%= StringUtils.trim(StringUtils.defaultIfBlank(request.getParameter("notify"),"")) %>';
        if (notify) {
            notifySuccess(notify);
        }
    });
</script>
<script src="login.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
