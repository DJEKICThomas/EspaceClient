$(document).ready(function () {
    $("#loginForm").submit(function (e) {
        e.preventDefault();
        let $username = $('#username').val();
        let $password = $('#password').val();
        if ($username !== '' && $password !== '') {
            let json = JSON.stringify({
                username: $username,
                password: $password
            });
            $.ajax({
                url: apiURL + '/login',
                type: 'POST',
                contentType: 'application/json',
                dataType: "json",
                cache: false,
                data: json,
                success: function (data) {
                    let isLoginOk = false;
                    if (data !== null) {
                        isLoginOk = data.loginOk;
                        if (isLoginOk) {
                            let customers = data.customers;
                            let profile = data.profile;
                            let userId = data.userId;
                            let eGlobalUserId = data.eGlobalUserId;
                            let token = data.token;
                            localStorage.setItem('userId', userId);
                            localStorage.setItem('username', $username);
                            localStorage.setItem('profile', profile);
                            localStorage.setItem('eGlobalUserId', eGlobalUserId);
                            localStorage.setItem('eGlobalToken', 'Bearer ' + token);
                            if (profile === 'GESTIONNAIRE' || profile === 'ADMINISTRATEUR' || profile === 'DEPAIRON') {
                                localStorage.setItem('selectedCustomer', '');
                                location.href = '/EspaceClient/customer/customer.jsp';
                            } else {
                                let customer = customers[0];
                                localStorage.setItem('selectedCustomer',
                                    customer.customerName + ' (' + customer.ncs + ')');
                                location.href = '/EspaceClient/porteur/porteur.jsp';
                            }
                        } else {
                            DevExpress.ui.dialog.alert("Nom d'utilisateur et/ou mot de passe incorrects", "Connexion");
                        }
                    }
                },
                error: function (e) {
                    console.log(e)
                }
            });
        }
    });

    let doNotShowAgain = localStorage.getItem('doNotShowAgain');
    let popupnstance = $('#infopopup').dxPopup({
        width: 1000,
        height: 770,
        contentTemplate: '<b id="popupTitle"><i><u>Le nouvel espace client est en ligne !</u></i></b>' +
            '<p>Cliquez sur le lien pour avoir un aperçu des différentes fonctionnalités</p>' +
            '<p>N\'hésitez pas à contacter Véronique Lorenzen pour toute question par téléphone au 087/32.29.66 ou par mail à  veronique.lorenzen@depairon.be</p>' +
            '<p>Bonne découverte</p>' +
            '<p><i>L\'équipe Depairon</i></p>' +
            '<video id="depaironVideo" width="640" height="480" controls> <source src="../video/Capsule_Espace_Client_Depairon_V2_23-05-23.mp4" type="video/mp4"> <source src="../video/Capsule_Espace_Client_Depairon_V2_23-05-23.mp4" type="video/ogg"> Votre navigateur ne supporte pas les vidéos </video>',
        visible: !doNotShowAgain || doNotShowAgain !== '1',
        hideOnOutsideClick: true,
        showCloseButton: true,
        showTitle: false,
        position: {
            at: 'center',
            my: 'center',
            collision: 'fit',
        },
        onHiding() {
            $('#depaironVideo')[0].pause();
        },
        toolbarItems: [{
            widget: 'dxButton',
            toolbar: 'bottom',
            location: 'before',
            options: {
                icon: 'info',
                text: 'Ne plus afficher',
                onClick() {
                    localStorage.setItem('doNotShowAgain', '1');
                    popupnstance.hide();
                },
            },
        }]
    }).dxPopup('instance');

});