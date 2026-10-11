// PDF
window.jsPDF = window.jspdf.jsPDF;

export function formItemsTemplate() {
    let profiles = ['ADMINISTRATEUR', 'GESTIONNAIRE', 'UTILISATEUR', 'COMMERCIAL'];
    const titleArray = [
        {title: 'Monsieur', value: 'MR'},
        {title: 'Madame', value: 'MAD'},
        {title: 'Mademoiselle', value: 'MLLE'},
    ];
    return [{
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
            },
            {
                dataField: 'password',
                editorOptions: {
                    mode: 'password'
                },
                isRequired: true,
                label: {
                    text: 'Mot de passe'
                }
            },
            {
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
                            const formData = $('#form').dxForm('instance').option('formData');
                            return formData.password === options.value;
                        },
                        message: 'Les mots de passe ne sont pas identiques'
                    }
                ]
            },
            {
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
            },
            {
                dataField: 'lastname',
                isRequired: true,
                label: {
                    text: 'Nom'
                }
            },
            {
                dataField: 'firstname',
                isRequired: true,
                label: {
                    text: 'Prénom'
                }
            },
            {
                dataField: 'profile',
                isRequired: true,
                label: {
                    text: 'Profil'
                },
                editorType: 'dxSelectBox',
                editorOptions: {
                    items: profiles,
                }
            },
            {
                dataField: 'function',
                isRequired: true,
                label: {
                    text: 'Fonction'
                }
            }
        ]
    },
        {
            itemType: 'group',
            items: [{
                itemType: 'group',
                items: [
                    {
                        dataField: 'telephone',
                        label: {
                            text: 'Téléphone'
                        }
                    },
                    {
                        dataField: 'mobile',
                        label: {
                            text: 'GSM'
                        }
                    },
                    {
                        dataField: 'fax',
                        label: {
                            text: 'Fax'
                        }
                    },
                    {
                        dataField: 'usrexpdate',
                        editorType: 'dxDateBox',
                        isRequired: true,
                        label: {
                            text: 'Date de validité du login'
                        },
                        editorOptions: {
                            displayFormat: 'dd/MM/yyyy'
                        },
                    }
                ]
            }]
        }, {
            itemType: 'button',
            horizontalAlignment: 'left',
            buttonOptions: {
                text: 'Confirmer',
                useSubmitBehavior: true,
            }
        }]
}