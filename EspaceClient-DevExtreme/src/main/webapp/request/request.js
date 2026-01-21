const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + "/requests")
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + "/requests",
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                d.resolve(result)
            },
            error: function (e) {
                d.resolve([]);
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        });
        return d.promise();
    },
});

function saveDetailRequest(id, form) {
    let msg = form['Commentaire'];
    $.ajax({
        url: apiURL + '/requests/comment',
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        cache: false,
        data: JSON.stringify({id: id, message: msg ? msg : ''}),
        success: function (data, response) {
            notifySuccess('Merci pour votre commentaire');
            $('#gridContainer').dxDataGrid('instance').refresh();
            if (data.rep) userConnectAndNotify(data.rep, '../request/request.jsp');
            // else location.replace('../request/request.jsp');
        }, error: function (e, response, error) {
            let errorResponse = e.responseJSON;
            notifyError(errorResponse.message);
        }, beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
        }
    })
}

// PDF
window.jsPDF = window.jspdf.jsPDF;

$(() => {
    const dataGrid = $('#gridContainer').dxDataGrid({
        dataSource: source,
        showBorders: true,
        height: '75vh',
        rowAlternationEnabled: true,
        filterRow: {
            visible: false,
            applyFilter: 'auto',
        },
        columns: [{
            dataField: 'carrier',
            caption: 'Porteur',
        }, {
            dataField: 'demandeur',
            caption: 'Demandeur',
        }, {
            dataField: 'cusNcs',
            caption: 'Client',
        }, {
            dataField: 'cusNda',
            caption: 'Adresse',
        }, {
            dataField: 'site',
            caption: 'Site',
        }, {
            dataField: 'cupboard',
            caption: 'Armoire',
        }, {
            dataField: 'cassette',
            caption: 'Cassette',
        }, {
            dataField: 'date',
            caption: 'Date de demande',
            dataType: 'date',
            format: 'dd/MM/yyyy'
        }, {
            dataField: 'typDes',
            caption: 'Type de demande',
        }, {
            dataField: 'ticket',
            caption: 'Numéro du ticket',
        }],
        noDataText: 'Pas de données',
        searchPanel: {
            visible: false,
            width: 250,
            placeholder: 'Rechercher...',
        },
        groupPanel: {
            emptyPanelText: 'Déplacer les colonnes ici pour regrouper les données',
            visible: true,
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
        columnAutoWidth: true,
        columnFixing: {
            enabled: true,
        },
        stateStoring: {
            enabled: true,
            type: 'localStorage',
            storageKey: 'adminFiltrageStorage',
        },
        masterDetail: {
            enabled: true,
            template(container, options) {
                const requestDetail = options.data;

                $('<div>').dxForm({
                    elementAttr: {
                        id: "elementId",
                    },
                    items: [{
                        dataField: 'Demande du client',
                        editorType: 'dxTextArea',
                        id: 'idComment',
                        name: 'comment',
                        editorOptions: {
                            height: '20vh',
                            readOnly: true,
                            value: requestDetail.texte,
                        }
                    }, {
                        dataField: 'Commentaire',
                        editorType: 'dxTextArea',
                        id: 'idComment',
                        name: 'comment',
                        editorOptions: {
                            height: '15vh',
                        }
                    }, {
                        itemType: 'button',
                        horizontalAlignment: 'left',
                        buttonOptions: {
                            useSubmitBehavior: true,
                            text: "Valider",
                            type: "success",
                            icon: "chevronnext",
                            hint: 'Valider infos',

                            onClick: function (e) {
                                sendData(requestDetail.id);
                            }
                        }
                    }
                    ],
                }).appendTo(container)
            },
        },
    }).dxDataGrid('instance');

    function sendData(id) {
        let formData = $('#elementId').dxForm('instance').option("formData");
        if (formData != null) {
            saveDetailRequest(id, formData);
        } else {
            notifyError('Le message est vide');
        }
    }

});