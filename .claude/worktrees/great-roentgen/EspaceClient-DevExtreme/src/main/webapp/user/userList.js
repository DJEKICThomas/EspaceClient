// PDF
window.jsPDF = window.jspdf.jsPDF;

const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + '/users')
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw 'Error';
        //     });
        const username = localStorage.getItem('username');
        if (username) {
            $.ajax({
                url: apiURL + '/users?userMail=' + username,
                type: 'GET',
                dataType: 'json',
                success: function (result) {
                    d.resolve(result)
                },
                error: function (e) {
                    // throw e;
                    d.resolve([]);
                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
                }
            });
        }
        return d.promise();
    },
    remove: function (key) {
        $.ajax({
            url: apiURL + '/users/' + key,
            type: 'DELETE',
            contentType: 'application/json',
            dataType: 'json',
            cache: false,
            success: function (data, response, e) {
                notifySuccess(data.message);
                /*DevExpress.ui.notify(
                    {message: data.message, type: 'success'},
                    {position: 'top center', direction: 'down-stack'});*/
            }, error: function (e, response, error) {
                let errorResponse = e.responseJSON;
                notifyError(errorResponse.message);
                /*DevExpress.ui.notify(
                    {message: errorResponse.message, type: 'error'},
                    {position: 'top center', direction: 'down-stack'});*/
            }, beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        })
    }
});

$(() => {
    const dataGrid = $('#user_list').dxDataGrid({
        dataSource: source,
        showBorders: true,
        height: '75vh',
        rowAlternationEnabled: true,
        filterRow: {
            visible: false,
            applyFilter: 'auto',
        },
        editing: {
            mode: 'row',
            allowUpdating: true,
            allowDeleting: true,
        },
        columns: [
            {
                type: 'buttons',
                fixed: true,
                allowFixing: false,
                fixedPosition: 'left',
                buttons: [{
                    icon: 'edit',
                    hint: 'Modifier',
                    onClick: (e) => {
                        let userId = e.row.key;
                        location.replace('user.jsp?userId=' + userId);
                    }
                }, {
                    icon: 'key',
                    hint: 'Accès aux droits',
                    onClick: (e) => { // e.row.data.email
                        let userEmail = e.row.data.email;
                        location.replace('userPermission.jsp?userEmail=' + userEmail);
                    }
                }, {
                    icon: 'trash',
                    hint: 'Supprimer',
                    onClick: (e) => { // e.row.data.email
                        let userId = e.row.data.id;
                        let result = DevExpress.ui.dialog.custom({
                            title: 'Attention',
                            messageHtml: '<i>Voulez-vous vraiment suprimmer ' + e.row.data.email + ' ?</i>',
                            buttons: [{
                                text: "Confirmer",
                                onClick: function () {
                                    source.remove(userId);
                                }
                            }, {
                                text: "Annuler",
                                onClick: function () {
                                    result.hide();
                                }
                            }]
                        });
                        result.show();
                    }
                }]
            }, {
                dataField: 'active',
                caption: 'Statut',
                width: '7%',
                dataType: 'boolean',
                headerFilter: {
                    dataSource: [{
                        text: "Actif",
                        value: 1
                    }, {
                        text: "En cours de traitement",
                        value: 0
                    }, {
                        text: "Supprimé",
                        value: ["active", "<", 0]
                    }]
                },
                cellTemplate(container, options) {
                    const active = options.data.active;
                    let cssClass = active === 1 ? 'userStatusOk' : active === 0 ? 'userStatusNeutral' : 'userStatusNotOk';
                    let title = active === 1 ? 'Actif' : active === 0 ? 'En cours de traitement' : 'Supprimé';
                    $('<i class="dx-icon-user ' + cssClass + '" title="' + title + '"></i>').appendTo(container);
                }
            }, {
                dataField: 'email',
                caption: 'Email',
                dataType: 'string'
            }, {
                dataField: 'lastname',
                caption: 'Nom',
                dataType: 'string'
            }, {
                dataField: 'firstname',
                caption: 'Prénom',
                dataType: 'string'
            }, {
                dataField: 'function',
                caption: 'Fonction',
                dataType: 'string'
            }, {
                dataField: 'profile',
                caption: 'Profil',
                dataType: 'string'
            }
        ],
        noDataText: 'Pas de données',
        searchPanel: {
            visible: true,
            width: 250,
            placeholder: 'Rechercher...',
        },
        groupPanel: {
            emptyPanelText: 'Déplacer les colonnes ici pour regrouper les données',
            visible: true,
        },
        columnChooser: {
            enabled: false,
            mode: "dragAndDrop",
            emptyPanelText: 'Déplacer les colonnes ici pour les cacher'
        },
        headerFilter: {
            visible: true,
            allowSearch: true,
        },
        wordWrapEnabled: true,
        paging: {
            pageSize: 100,
        },
        scrolling: {
            mode: 'virtual'
        },
        allowColumnReordering: true,
        allowColumnResizing: true,
        columnAutoWidth: false,
        columnMinWidth: 100,
        columnFixing: {
            enabled: true,
        },
        stateStoring: {
            enabled: true,
            type: 'localStorage',
            storageKey: 'userListStorage',
        },
        export: {
            enabled: true,
            formats: ['xlsx', 'pdf'],
            texts: {
                exportAll: 'Export {0}'
            }
        },
        onExporting(e) {
            if (e.format === 'xlsx') {
                const workbook = new ExcelJS.Workbook();
                const worksheet = workbook.addWorksheet('Utilisateurs');
                DevExpress.excelExporter.exportDataGrid({
                    component: e.component,
                    worksheet,
                    autoFilterEnabled: true,
                }).then(() => {
                    workbook.xlsx.writeBuffer().then((buffer) => {
                        saveAs(new Blob([buffer],
                            {type: 'application/octet-stream'}), 'Utilisateurs.xlsx');
                    });
                });
                e.cancel = true;
            } else if (e.format === 'pdf') {
                const doc = new jsPDF();
                DevExpress.pdfExporter.exportDataGrid({
                    jsPDFDocument: doc,
                    component: e.component,
                    indent: 5,
                }).then(() => {
                    doc.save('Utilisateurs.pdf');
                });
            }
        },
        onContentReady(dxEvent) {
            const id = 'state-reset-link';
            if ($('#' + id).length === 0) {
                let div = $('<div class="dx-item dx-toolbar-item dx-toolbar-button dx-toolbar-item-auto-hide">')
                    .append($('<div id="' + id + '" class="dx-item-content dx-toolbar-item-content">').dxButton({
                        icon: 'fa fa-filter',
                        hint: 'Reset filtres',
                        onClick() {
                            dxEvent.component.clearFilter();
                            dxEvent.component.clearSorting();
                        }
                    }))
                dxEvent.element.find('.dx-toolbar-after').prepend(div);
            }

            // const newuserButtonId = 'newuser-button-id';
            // if ($('#' + newuserButtonId).length === 0) {
            //     let div = $('<div class="dx-item dx-toolbar-item dx-toolbar-button dx-toolbar-item-auto-hide">')
            //         .append($('<div id="' + newuserButtonId
            //             + '" class="dx-item-content dx-toolbar-item-content rounded-pill" ' +
            //             'style="color: white; background-color: #d90008;">')
            //             .dxButton({
            //                 icon: 'fa fa-male',
            //                 style: 'color: white',
            //                 hint: 'Nouvel Utilisateur',
            //                 text: 'Nouvel Utilisateur',
            //                 onClick() {
            //                     location.replace('user.jsp?userId=0');
            //                 }
            //             }))
            //     dxEvent.element.find('.dx-toolbar-after').prepend(div);
            // }
        }
    }).dxDataGrid('instance');
});