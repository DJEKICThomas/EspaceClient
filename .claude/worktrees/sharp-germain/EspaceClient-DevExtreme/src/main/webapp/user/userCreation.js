const userSource = new DevExpress.data.CustomStore({
    key: 'id',
    insert: function (values) {
        console.log(values);
        $.ajax({
            url: apiURL + '/users/create',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(values),
            success: function (data, response, e) {
                // notifySuccess('Utilisateur créé');
                location.href = encodeURI(location.origin + '/EspaceClient/login/login.jsp?notify=Votre demande a bien été prise en compte');
            }, error: function (e, response, error) {
                let errorResponse = e.responseJSON;
                notifyError(errorResponse.message);
            }
        })
    }
});

$(async function () {
    const titleArray = [
        {title: 'Monsieur', value: 'MR'},
        {title: 'Madame', value: 'MAD'},
        {title: 'Mademoiselle', value: 'MLLE'},
    ];
    const form = $('#form').dxForm({
        id: 'form',
        formData: {
            email: '',
            confirmEmail: '',
            password: '',
            confirmPassword: '',
            title: '',
            function: '',
            lastname: '',
            firstname: '',
            telephone: '',
            mobile: '',
            fax: '',
            socityName: '',
            vat: '',
            addess: '',
            postalCode: '',
            country: '',
            locality: '',
            activitySector: '',
            generalFax: '',
            generalTelephone: '',
            socityMail: '',
            employeesNumber: '0',
            additionalNotes: '',
        },
        labelLocation: 'top',
        showColonAfterLabel: false,
        colCount: 1,
        caption: 'Compte utilisateur',
        items:
            [{
                itemType: 'group',
                colCount: 2,
                caption: 'Vos données',
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
                    dataField: 'confirmEmail',
                    isRequired: true,
                    label: {
                        text: 'Confirmation de l\'email'
                    },
                    validationRules: [{
                        type: 'required',
                        message: 'Email de confirmation est requis',
                    }, {
                        type: 'email',
                        message: 'Email de confirmation invalide',
                    }, {
                        type: 'custom',
                        reevaluate: true, // Si on ne met pas ça, de temps en temps la validation est fausse même si les champs sont identiques
                        validationCallback: (options) => {
                            const formData = $('#form').dxForm('instance').option('formData');
                            return formData.email === options.value;
                        },
                        message: 'Les emails ne sont pas identiques'
                    }]
                }, {
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
                    validationRules: [{
                        type: 'custom',
                        reevaluate: true, // Si on ne met pas ça, de temps en temps la validation est fausse même si les champs sont identiques
                        validationCallback: (options) => {
                            const formData = $('#form').dxForm('instance').option('formData');
                            return formData.password === options.value;
                        },
                        message: 'Les mots de passe ne sont pas identiques'
                    }]
                }, {
                    dataField: 'title',
                    isRequired: true,
                    label: {
                        text: 'Titre'
                    },
                    editorType: 'dxSelectBox',
                    editorOptions: {
                        dataSource: titleArray,
                        displayExpr: 'title',
                        valueExpr: 'value',
                    }
                }, {
                    dataField: 'function',
                    isRequired: false,
                    label: {
                        text: 'Fonction dans l\'entreprise'
                    }
                }, {
                    dataField: 'lastname',
                    isRequired: true,
                    label: {
                        text: 'Nom'
                    }
                }, {
                    dataField: 'firstname',
                    isRequired: true,
                    label: {
                        text: 'Prénom'
                    }
                }, {
                    dataField: 'telephone',
                    label: {
                        text: 'Téléphone'
                    }
                }, {
                    dataField: 'mobile',
                    label: {
                        text: 'GSM'
                    }
                }, {
                    dataField: 'fax',
                    label: {
                        text: 'Fax'
                    }
                }]
            }, {
                itemType: 'group',
                colCount: 2,
                caption: 'Votre société',
                items: [{
                    dataField: 'socityName',
                    label: {
                        text: 'Nom société'
                    }
                }, {
                    dataField: 'vat',
                    label: {
                        text: 'TVA'
                    }
                }, {
                    dataField: 'addess',
                    label: {
                        text: 'Adresse'
                    }
                }, {
                    dataField: 'postalCode',
                    label: {
                        text: 'Code postal'
                    }
                }, {
                    dataField: 'country',
                    label: {
                        text: 'Pays'
                    }
                }, {
                    dataField: 'locality',
                    label: {
                        text: 'Localité'
                    }

                }, {
                    dataField: 'activitySector',
                    label: {
                        text: 'Secteur d\'activité'
                    }
                }, {
                    dataField: 'generalFax',
                    label: {
                        text: 'N° de fax général'
                    }
                }, {
                    dataField: 'generalTelephone',
                    label: {
                        text: 'N° de téléphone général'
                    }
                }, {
                    dataField: 'socityMail',
                    label: {
                        text: 'Adresse e-mail de l\'entreprise'
                    }
                }, {
                    dataField: 'employeesNumber',
                    label: {
                        text: 'Nombre de personnes employées'
                    }
                }, {
                    dataField: 'additionalNotes',
                    label: {
                        text: 'Note complémentaire'
                    }
                }]
            }, {
                itemType: 'button',
                horizontalAlignment: 'left',
                buttonOptions: {
                    text: 'Confirmer',
                    useSubmitBehavior: true,
                    elementAttr: {
                        id: "creationButton",
                    }
                }
            }
            ]
    }).dxForm('instance');


    $('.dx-button-submit-input').click(function (e) {
        if (form) {
            let validateResult = form.validate();
            if (validateResult.isValid) {
                let data = form.option('formData');
                userSource.insert(data);
            }
        }
    });
});
