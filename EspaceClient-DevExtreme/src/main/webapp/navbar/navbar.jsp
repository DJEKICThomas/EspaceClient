<%@ page contentType="text/html;charset=ISO-8859-1" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/navbar/navbar.css"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<div id="header">
    <nav class="navbar navbar-expand-lg">
        <div class="collapse navbar-collapse d-flex justify-content-evenly">
            <div class="row align-items-center">
                <button class="navbar-toggler"
                        type="button"
                        data-mdb-toggle="collapse"
                        data-mdb-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent"
                        aria-expanded="false"
                        aria-label="Toggle navigation">
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse justify-content-center">
                    <a class="navbar-brand mt-2 mt-lg-0" href="#">
                        <img
                                src="../static/images/depairon-logo.jpg"
                                height="50"
                                width="150"
                                alt="Depairon"
                                loading="lazy"
                        />
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-12">
                        <li class="nav-item dropdown" id="admin">
                            <%--<div class="msgscrol">
                                <span>Nouvelle Demande</span>
                            </div>--%>
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownAdmin" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Admin
                            </a><span id="badge" class="badge">!</span>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownAdmin">
                                <li id="listeutilisateurs">
                                    <a class="dropdown-item" href="<%=request.getContextPath()%>/user/userList.jsp">Liste
                                        des utilisateurs</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="navbar-nav" <%--style="margin-right: 10px;"--%>>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fa fa-user-circle" aria-hidden="true"></i>

                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/user/user.jsp">Mon
                                    compte</a>
                                </li>
                                <li><a class="dropdown-item" onclick="logout();">Déconnexion</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<%=request.getContextPath()%>/js/webjars/sockjs.min.js"></script>
<script src="<%=request.getContextPath()%>/js/webjars/stomp.min.js"></script>
<script>
    window.addEventListener('unload', function (event) {
        console.log(stompClient);
        disconnect();
    });

    function logout() {
        localStorage.setItem('username', '');
        localStorage.setItem('profile', '');
        localStorage.setItem('selectedCustomer', '');
        localStorage.setItem('eGlobalUserId', '');
        localStorage.setItem('eGlobalToken', '');
        localStorage.setItem('userId', '');
        disconnect();
        location.href = location.origin + '/EspaceClient/login/login.jsp';
    }

    let eGlobalUserId = localStorage.getItem('eGlobalUserId');
    let profile = localStorage.getItem('profile');
    // if (profile === 'ADMINISTRATEUR') {
    //     $('#admin').show();
    // } else {
    //     $('#admin').hide();
    // }
    // if (profile === 'UTILISATEUR') {
    //     $('#customer').hide();
    // } else {
    //     $('#customer').show();
    // }

    getPermission(localStorage.getItem('username'));

    $('#improvementButton').dxButton({
        icon: 'fa fa-lightbulb',
        hint: 'Idée d\'amélioration',
        text: 'Idée d\'amélioration',
        onClick() {
            location.href = '<%=request.getContextPath()%>/request/improvementRequest.jsp';
        }
    });
    // <video width="320" height="240" controls>
    //     <source src="movie.mp4" type="video/mp4">
    //         <source src="movie.ogg" type="video/ogg">
    //             Your browser does not support the video tag.
    // </video>
    $('#helpButton').dxButton({
        icon: 'fa fa-question',
        hint: 'Besoin d\'aide ?',
        onClick() {
            // location.href = 'https://www.youtube.com/watch?v=D5gnApJnVL0';
            // window.open("https://www.youtube.com/watch?v=D5gnApJnVL0", "_blank");
            const popup = $('#helpPopupVideo').dxPopup({
                contentTemplate: '<video id="depaironVideo" width="640" height="480" controls> <source src="../video/Capsule_Espace_Client_Depairon_V2_23-05-23.mp4" type="video/mp4"> <source src="../video/Capsule_Espace_Client_Depairon_V2_23-05-23.mp4" type="video/ogg"> Votre navigateur ne supporte pas les vidéos </video>',
                // contentTemplate: 'hello',
                width: 690,
                height: 530,
                // container: 'body',
                showTitle: false,
                visible: true,
                dragEnabled: false,
                hideOnOutsideClick: true,
                showCloseButton: true,
                position: {
                    at: 'center',
                    my: 'center',
                    collision: 'fit',
                },
                onHiding() {
                    $('#depaironVideo')[0].pause();
                },
                onShown() {
                    $('#depaironVideo')[0].play();
                }
            }).dxPopup('instance');
        }
    });

    //TODO Modifier cette condidition avec NHO (UNIQUEMENT ADMINISTRATEUR ET DEPAIRON ?????)
    let stompClient;
    if (/*profile === 'GESTIONNAIRE' || */profile === 'ADMINISTRATEUR' || profile === 'DEPAIRON'/* || profile === 'UTILISATEUR'*/) {
        // const eglobalUserId = localStorage.getItem('eGlobalUserId');
        if (!stompClient) {
            connect();
        }
    }

    function connect() {
        let socket = new SockJS(apiURL + '/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.debug = null;
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/notification/listen', function (data) {
                const gestData = JSON.parse(data.body);
                if (gestData.gestionnaire === eGlobalUserId) {
                    showNotify()
                }
            });
        });
    }

    function showNotify() {
        const badge = document.getElementById('badge');
        if (badge) badge.style.visibility = 'visible';
    }

    function hideNotify() {
        const badge = document.getElementById('badge');
        if (badge) badge.style.visibility = 'hidden';
    }

    function userConnectAndNotify(gestionnaire, redirectTo) {
        let socket = new SockJS(apiURL + '/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.debug = null;
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/notification/listen', function (data) {
                const gestData = JSON.parse(data.body);
                if (gestData.gestionnaire === eGlobalUserId) {
                    showNotify();
                }
            });
            stompClient.send("/notification/test", {}, gestionnaire);
            disconnect();
            if (redirectTo) location.replace(redirectTo);
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            try {
                // User try catch because if user is not authorized to a page it disconnect it before connecting to websocket => error
                stompClient.disconnect();
            } catch (e) {
                console.log(e);
            }
        }
        console.log('disconnected')
    }

    // TODO faire
    $(async () => {
        const eglobalUserId = localStorage.getItem('eGlobalUserId');
        $.ajax({
            url: apiURL + '/notification?gestionnaire=' + eglobalUserId,
            type: 'GET',
            contentType: 'application/json',
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.isNew === '1') {
                    showNotify();
                } else {
                    hideNotify();
                }
            },
            error: function (e) {
                console.log(e)
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        });
    })


    function updateNotify() {
        $.ajax({
            url: apiURL + '/notification',
            type: 'POST',
            contentType: 'application/json',
            dataType: "json",
            cache: false,
            data: JSON.stringify({gestionnaire: eGlobalUserId}),
            success: function (data, response) {
                hideNotify();
            }, error: function (e, response, error) {
                // let errorResponse = e.responseJSON;
                // notifyError(errorResponse.message);
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        });
    }

    function isValidDate(d) {
        return d instanceof Date && !isNaN(d);
    }

    function getPermission(mail) {
        let currentPageCode = '<%= request.getParameter("code") %>';
        $.ajax({
            url: apiURL + '/permission?mail=' + mail,
            type: 'GET',
            contentType: 'application/json',
            dataType: "json",
            cache: false,
            success: function (data, response) {
                for (let i = 0; i < data.length; i++) {
                    const code = data[i].code;
                    const hasAccess = data[i].hasAccess;
                    if (code === 'ACCESS_NOMINATIVEMANAGEMENT') if (!hasAccess) $('#infoporteur').remove();
                    if (code === 'ACCESS_ENTRYCONTROL') if (!hasAccess) $('#infonettoyage').remove();
                    if (code === 'ACCESS_DOTATION') if (!hasAccess) $('#infodotation').remove();
                    if (code === 'ACCESS_SPECIFICREQUEST') if (!hasAccess) $('#customerRequest').remove();
                    if (code === 'ACCESS_ORDER') if (!hasAccess) $('#infocommandes').remove();
                    if (code === 'ACCESS_STATISTIC') if (!hasAccess) $('#statistics').remove();
                    if (code === 'ACCESS_ADMIN') if (!hasAccess) $('#admin').remove();
                    if (code === 'ACCESS_FILTRAGE') if (!hasAccess) $('#filtragedemandes').remove();
                    if (code === 'ACCESS_GESTION') if (!hasAccess) $('#gestiondemandes').remove();
                    if (code === 'ACCESS_USERLIST') if (!hasAccess) $('#listeutilisateurs').remove();
                    if (code === 'ACCESS_CUSSELECTION') if (!hasAccess) $('#customer').remove();

                    if (currentPageCode === code && !hasAccess) logout();
                }
            }, error: function (e, response, error) { // e.status
                // notifyError(e.responseJSON.message);
                if (e.status === 403) {
                    logout();
                }
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        });
    }

</script>
<jsp:include page="../customer/active/customer-active.jsp"></jsp:include>