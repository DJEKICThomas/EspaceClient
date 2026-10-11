<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/customer/active/customer-active.css"/>
<div class="container">
    <div class="row">
        <h1 id="activeCustomer">Pas de client sélectionné</h1>
    </div>
</div>
<script>
    let activeCustomer = localStorage.getItem("selectedCustomer");
    let userProfile = localStorage.getItem("profile");
    if (userProfile !== '' && userProfile !== 'UTILISATEUR') {
        document.getElementById('activeCustomer').innerText = activeCustomer;
    } else {
        document.getElementById('activeCustomer').innerText = '';
    }
</script>