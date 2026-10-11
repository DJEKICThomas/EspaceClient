// import {formItemsTemplate} from '../template.js';

const userSource = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        let userId;
        if (selectedUserId !== null) {
            userId = selectedUserId;
        } else {
            userId = localStorage.getItem('userId');
        }
        // return $.getJSON(apiURL + '/users/' + userId)
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw 'Error';
        //     });
        $.ajax({
            url: apiURL + '/users/' + userId,
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                d.resolve(result)
            },
            error: function (e) {
                throw e;
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        });
        return d.promise();
    },

    insert: function (values) {
        values.active = values.active ? 1 : -1;
        $.ajax({
            url: apiURL + '/users',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(values),
            success: function (data, response, e) {
                notifySuccess('Utilisateur créé/modifié');
            }, error: function (e, response, error) {
                let errorResponse = e.responseJSON;
                notifyError(errorResponse.message);
            }, beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        })
    }
});

$(async function () {
    let profiles = ['ADMINISTRATEUR', 'GESTIONNAIRE', 'UTILISATEUR', 'COMMERCIAL', 'DEPAIRON'];
    const titleArray = [
        {title: 'Monsieur', value: 'MR'},
        {title: 'Madame', value: 'MAD'},
        {title: 'Mademoiselle', value: 'MLLE'},
    ];
    let isProfileReadOnly = function () {
        return selectedUserId !== 0;
    };
    let userStore = await userSource.load();
    const form = $('#form').dxForm({
        id: 'form',
        formData: userStore,
        labelLocation: 'top',
        showColonAfterLabel: false,
        colCount: 2,
        caption: 'Compte utilisateur',
        items:
            [{
                itemType: 'group',
                items: [
                    {
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
                        }, validationRules: [
                            {
                                type: 'custom',
                                reevaluate: true, // Si on ne met pas ça, de temps en temps la validation est fausse même si les champs sont identiques
                                validationCallback: (options) => {
                                    const formData = $('#form').dxForm('instance').option('formData');
                                    return formData.password === options.value;
                                },
                                message: 'Les mots de passe ne sont pas identiques'
                            }
                        ]
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
                        dataField: 'profile',
                        isRequired: true,
                        label: {
                            text: 'Profil'
                        },
                        editorType: 'dxSelectBox',
                        visible: profile === 'ADMINISTRATEUR',
                        editorOptions: {
                            items: profiles,
                            // readOnly: isProfileReadOnly()
                        }
                    }
                ]
            }, {
                itemType: 'group',
                items: [{
                    itemType: 'group',
                    items: [
                        {
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
                        }, {
                            dataField: 'usrexpdate',
                            editorType: 'dxDateBox',
                            // isRequired: true,
                            visible: profile === 'ADMINISTRATEUR',
                            label: {
                                text: 'Date de validité du login'
                            },
                            editorOptions: {
                                displayFormat: 'dd/MM/yyyy'
                            },
                        }, {
                            dataField: 'function',
                            isRequired: false,
                            label: {
                                text: 'Fonction dans l\'entreprise'
                            }
                        }, {
                            dataField: 'active',
                            visible: profile === 'ADMINISTRATEUR',
                            label: {
                                text: 'Compte actif'
                            },
                            editorType: 'dxSwitch',
                            editorOptions: {
                                value: userStore.active === 1
                            }
                        }
                    ]
                }]
            }, {
                itemType: 'button',
                horizontalAlignment: 'left',
                buttonOptions: {
                    text: 'Confirmer',
                    useSubmitBehavior: true,
                    elementAttr: {
                        id: 'updateUserButton',
                        class: "userButton"
                    }
                }
            }, {
                itemType: 'button',
                horizontalAlignment: 'left',
                buttonOptions: {
                    text: 'Réinitialiser les filtres',
                    useSubmitBehavior: false,
                    onClick: function () {
                        localStorage.removeItem('myRequestStorage');
                        localStorage.removeItem('orderStorage');
                        localStorage.removeItem('porteurStorage');
                        localStorage.removeItem('adminFiltrageStorage');
                        localStorage.removeItem('statistiqueStorage');
                        localStorage.removeItem('nettoyageStorage');
                        localStorage.removeItem('userListStorage');
                        localStorage.removeItem('improvementRequestDetailStorage');
                        localStorage.removeItem('dotationStorage');
                        localStorage.removeItem('customerRequestStorage');
                        notifySuccess('Tous les filtres ont été réinitialisés')
                    },
                    elementAttr: {
                        id: 'resetFilterButton',
                        class: "userButton"
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

