$(function () {
    const form = $('#forgotPasswordForm').dxForm({
        formData: {email: ''},
        colCount: 1,
        items: [{
            dataField: 'email',
            isRequired: true,
            label: {
                text: 'Email'
            },
            validationRules: [{
                type: 'required',
                message: 'Email est requis',
            }, {
                type: 'email',
                message: 'Email invalide',
            }]
        }, {
            itemType: 'button',
            horizontalAlignment: 'left',
            buttonOptions: {
                text: 'Confirmer',
                useSubmitBehavior: true,
            }
        }]
    }).dxForm('instance');

});

function doSubmit() {
    const form1 = $('#forgotPasswordForm').dxForm('instance');
    if (form1) {
        let validateResult = form1.validate();
        if (validateResult.isValid) {
            let data = form1.option('formData');
            sendForgotPasswordRequest(data);
            let popupnstance = $('#infopopup').dxPopup({
                width: 500,
                height: 300,
                // container: '#container-fluid',
                // title: 'Nouveau porteur',
                contentTemplate: 'Un mail va vous être envoyé',
                visible: true,
                hideOnOutsideClick: true,
                showCloseButton: true,
                position: {
                    at: 'center',
                    my: 'center',
                    collision: 'fit',
                },
                toolbarItems: [{
                    widget: 'dxButton',
                    toolbar: 'bottom',
                    location: 'before',
                    options: {
                        icon: 'check',
                        text: 'Valider',
                        onClick() {
                            popupnstance.hide();
                            location.href = location.origin + '/EspaceClient/login/login.jsp';
                        },
                    },
                }]
            }).dxPopup('instance');
        }
    }
}

function sendForgotPasswordRequest(userData) {
    $.ajax({
        url: apiURL + '/users/password',
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        cache: false,
        data: JSON.stringify(userData),
        success: function (data, response) {
        }, error: function (e, response, error) {
            let errorResponse = e.responseJSON;
            notifyError(errorResponse.message);
        }
    });
}