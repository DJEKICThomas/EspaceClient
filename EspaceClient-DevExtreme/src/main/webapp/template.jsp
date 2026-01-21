<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/devextreme/dx.common.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/devextreme/dx.material.DEPAIRON.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme.css"/>
<link rel="icon" type="image/vnd.microsoft.icon" href="static/images/favicon.ico">
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/devextreme/dx.material.css"/>--%>
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/mdb/mdb.min.css"/>--%>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>

<script src="<%=request.getContextPath()%>/js/jquery-3.6.2.min.js"></script>
<script src="<%=request.getContextPath()%>/js/devextreme/dx.all.js"></script>
<script src="<%=request.getContextPath()%>/js/devextreme/dx.aspnet.data.js"></script>
<script src="<%=request.getContextPath()%>/js/devextreme/polyfill.min.js"></script>
<script src="<%=request.getContextPath()%>/js/devextreme/jspdf.umd.min.js"></script>
<script src="<%=request.getContextPath()%>/js/devextreme/olderexceljs.min.js"></script>
<script src="<%=request.getContextPath()%>/js/devextreme/FileSaver.min.js"></script>
<script>
    function getSelectedCustomerNcs() {
        return localStorage.getItem("selectedCustomer").replace(/.*\(|\).*/g, '');
    }

    function notifyError(error) {
        DevExpress.ui.notify(
            {message: error, type: 'error',},
            {position: 'top center', direction: 'down-stack'});
    }

    function notifySuccess(message) {
        DevExpress.ui.notify(
            {message: message, type: 'success'},
            {position: 'top center', direction: 'down-stack'});
    }

    //TEST A SUPPRIMER
    const apiURL = 'http://localhost:8080/EspaceClient-api';

    // PROD
    // const apiURL = window.location.origin + '/EspaceClient-api';

</script>
<script src="<%=request.getContextPath()%>/security/security.js"></script>
<!-- MDB -->
<%--<script--%>
<%--        type="text/javascript"--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.1/mdb.min.js"--%>
<%--></script>--%>


