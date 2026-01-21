const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        // let username = localStorage.getItem('username');
        let d = $.Deferred();
        // return $.getJSON(apiURL + '/requests/improvement?rep=')
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + '/requests/improvement?rep=',
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
        return d.promise();
    },
});

const detailSource = new DevExpress.data.CustomStore({
    key: 'detailSource',
    load: function (id) {
        let d = $.Deferred();
        // return $.getJSON(apiURL + "/requests/" + id)
        //     .done(function (result) {
        //         console.log(result);
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + "/requests/" + id,
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
});

function customLoad(id) {
    let d = $.Deferred();
    // return $.getJSON(apiURL + "/requests/" + id)
    //     .done(function (result) {
    //         console.log(result);
    //         // d.resolve(result);
    //         return result;
    //     })
    //     .fail(function () {
    //         throw "Error";
    //     });
    $.ajax({
        url: apiURL + "/requests/" + id,
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
}

function saveDetailRequest(id, val) {
    $.ajax({
        url: apiURL + '/requests/comment',
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        cache: false,
        data: JSON.stringify({id: id, message: val}),
        success: function (data, response) {
            if (val === "OK") {
                notifySuccess('Demande acceptée avec succès');
            } else {
                notifySuccess('Demande Refusée avec succès');
            }
            location.replace('../request/impromentRequestDetailList.jsp');
        }, error: function (e, response, error) {
            let errorResponse = e.responseJSON;
            notifyError(errorResponse.message);
        }, beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
        }
    })
}

updateNotify();

// PDF
window.jsPDF = window.jspdf.jsPDF;

function createTemplate(requestDetail) {
    return function (itemData, itemIndex, container) {
        $('<div>').dxTextArea({
            value: requestDetail.texte,
            height: '80%',
            readOnly: true
        }).appendTo(container);
    };
}

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
            dataField: 'rep',
            caption: 'Gestionnaire',
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
        }, {
            dataField: 'statusDes',
            caption: 'Statut',
        }],
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
            storageKey: 'improvementRequestDetailStorage',
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
                        editorOptions: {
                            readOnly: true,
                            height: '20vh',
                            value: requestDetail.texte,
                        }
                    }, {
                        dataField: 'Commentaire',
                        editorType: 'dxTextArea',
                        editorOptions: {
                            height: '15vh',
                            readOnly: true,
                            value: requestDetail.textGes
                        }
                    }, {
                        itemType: 'button',
                        horizontalAlignment: 'left',
                        buttonOptions: {
                            useSubmitBehavior: true,
                            text: "Traitée",
                            type: "success",
                            icon: "chevronnext",
                            hint: 'Traitée',

                            onClick: function (e) {
                                sendData(requestDetail.id, "TRUE");
                            }
                        }
                    }],
                }).appendTo(container);
            },
        },
    }).dxDataGrid('instance');


    function sendData(id, val) {
        saveDetailRequest(id, val);
    }
});