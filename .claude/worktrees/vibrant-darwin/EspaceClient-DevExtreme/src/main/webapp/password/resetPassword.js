$(function () {
    const form = $('#resetPasswordForm').dxForm({
        formData: {webUserId: webUserId, password: '', confirmPassword: ''},
        colCount: 1,
        items: [{
            dataField: 'password',
            editorOptions: {
                mode: 'password'
            },
            isRequired: true,
            label: {
                text: 'Mot de passe'
            }
        }, {
            dataField: 'confirmPassword',
            editorOptions: {
                mode: 'password'
            },
            isRequired: true,
            label: {
                text: 'Confirmation de mot de passe'
            },
            validationRules: [
                {
                    type: 'custom',
                    reevaluate: true, // Si on ne met pas ça, de temps en temps la validation est fausse même si les champs sont identiques
                    validationCallback: (options) => {
                        const formData = form.option('formData');
                        return formData.password === options.value;
                    },
                    message: 'Les mots de passe ne sont pas identiques'
                }
            ]
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

function doReset() {
    const form = $('#resetPasswordForm').dxForm('instance');
    if (form) {
        let validateResult = form.validate();
        if (validateResult.isValid) {
            let data = form.option('formData');
            sendResetPassword(data);
        }
    }
}


function sendResetPassword(formData) {
    $.ajax({
        url: apiURL + '/users/password/reset',
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        cache: false,
        data: JSON.stringify(formData),
        success: function (data, response) {
            let popupnstance = $('#infopopup').dxPopup({
                width: 500,
                height: 300,
                contentTemplate: data.message,
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
        }, error: function (e, response, error) {
            let errorResponse = e.responseJSON;
            notifyError(errorResponse.message);
            throw e;
        }
    });
}