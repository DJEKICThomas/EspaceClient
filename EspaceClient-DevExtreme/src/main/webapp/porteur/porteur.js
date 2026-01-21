const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + '/porteurs?customer=' + getSelectedCustomerNcs())
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + '/porteurs?customer=' + getSelectedCustomerNcs(),
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

// PDF
window.jsPDF = window.jspdf.jsPDF;

$(() => {
    const dataGrid = $('#gridContainer').dxDataGrid({
        dataSource: source,
        columns: [
            {
                caption: 'Demande',
                type: 'buttons',
                allowFixing: false,
                fixed: true,
                fixedPosition: 'left',
                buttons: [{
                    hint: 'Demande',
                    icon: 'fa fa-address-card',
                    onClick(e) {
                        // showRequestPopup(e.row.data);
                        location.replace('../request/newRequest.jsp?carrier=' + e.row.data.carrier
                            + '&ncsId=' + e.row.data.ncsId);
                    },
                }],
            },
            {
                visible: false,
                allowExporting: false,
                dataField: 'id',
                caption: 'ID',
                dataType: 'number'
            },
            {
                dataField: 'site',
                caption: 'Site',
                dataType: 'string'
            },
            {
                dataField: 'name',
                caption: 'Porteur',
                dataType: 'string'
            },
            {
                dataField: 'nature',
                caption: 'Nature',
                dataType: 'string'
            },
            {
                dataField: 'quantity',
                caption: 'Quantité',
                dataType: 'number'
            },
            {
                dataField: 'artDes',
                caption: 'Article',
                dataType: 'string'
            },
            {
                dataField: 'cupboard',
                caption: 'Armoire',
                dataType: 'number'
            },
            {
                dataField: 'cassette',
                caption: 'Cassette',
                dataType: 'number'
            },

            {
                visible: false,
                allowExporting: false,
                dataField: 'ncsId',
                dataType: 'number'
            }

        ],
        noDataText: 'Pas de données',
        columnChooser: {
            enabled: false,
            mode: "dragAndDrop",
            emptyPanelText: 'Déplacer les colonnes ici pour les cacher'
        },
        height: '75vh',
        rowAlternationEnabled: true,
        showBorders: true,
        filterRow: {
            visible: false,
            applyFilter: 'auto',
        },
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
        export: {
            enabled: true,
            formats: ['xlsx', 'pdf'],
            texts: {
                exportAll: 'Export {0}'
            }
        },
        allowColumnReordering: true,
        allowColumnResizing: true,
        columnAutoWidth: true,
        columnFixing: {
            enabled: true,
        },
        grouping: {
            autoExpandAll: true
        },
        stateStoring: {
            enabled: true,
            type: 'localStorage',
            storageKey: 'porteurStorage',
        },
        onExporting(e) {
            if (e.format === 'xlsx') {
                const workbook = new ExcelJS.Workbook();
                const worksheet = workbook.addWorksheet('Porteurs');
                DevExpress.excelExporter.exportDataGrid({
                    component: e.component,
                    worksheet,
                    autoFilterEnabled: true,
                }).then(() => {
                    workbook.xlsx.writeBuffer().then((buffer) => {
                        saveAs(new Blob([buffer],
                            {type: 'application/octet-stream'}), 'Porteurs.xlsx');
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
                    doc.save('Porteurs.pdf');
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

            const newPorteurButtonId = 'newporteur-button-id';
            if ($('#' + newPorteurButtonId).length === 0) {
                let div = $('<div class="dx-item dx-toolbar-item dx-toolbar-button dx-toolbar-item-auto-hide">')
                    .append($('<div id="' + newPorteurButtonId + '" class="dx-item-content dx-toolbar-item-content rounded-pill" style="color: white; background-color: #d90008;">').dxButton({
                        icon: 'fa fa-male',
                        style: 'color: white',
                        hint: 'Nouveau Porteur',
                        text: 'Nouveau Porteur',
                        onClick() {
                            dxPopup.show();
                        }
                    }))
                dxEvent.element.find('.dx-toolbar-after').prepend(div);
            }

        }
    }).dxDataGrid('instance');

    $('#state-reset-link').on('click', () => {
        dataGrid.state(null);
    });

    $('#autoExpand').dxCheckBox({
        value: true,
        text: 'Ouvrir les groupes',
        onValueChanged(data) {
            dataGrid.option('grouping.autoExpandAll', data.value);
        },
    });

    let textArea = null;
    const dxPopup = $('#porteur_popup').dxPopup({
        width: 1000,
        height: 500,
        container: '.dx-viewport',
        title: 'Nouveau porteur',
        visible: false,
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
                icon: 'email',
                text: 'Envoyer',
                onClick() {
                    createRequest();
                },
            },
        }],
        onShown: function (e) {
            textArea = $('#request_message').dxTextArea({
                value: '',
                height: '50%',
            }).dxTextArea('instance');
        },
    }).dxPopup('instance');

    function createRequest() { // NEWCARRIER
        if (textArea) {
            let value = textArea.option('value');
            let json = JSON.stringify({
                customerDTO: {cusNcs: getSelectedCustomerNcs()},
                user: localStorage.getItem('username'),
                requestDetailDTO: [
                    {text: value, typ: 'NEWCARRIER'}
                ],
            });
            $.ajax({
                url: apiURL + '/requests',
                type: 'POST',
                contentType: 'application/json',
                dataType: "json",
                cache: false,
                data: json,
                success: function (data, response) {
                    notifySuccess('Requête envoyée');
                    // userConnectAndNotify(data.gestionnaire);
                    textArea.reset();
                    dxPopup.hide();
                }, error: function (e, response, error) {
                    let errorResponse = e.responseJSON;
                    notifyError(errorResponse.message);
                }, beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
                }
            });

        }
    }

    /*    function notifyGestionnaire(gestionnaire) {
            $.ajax({
                url: apiURL + '/notification/test',
                type: 'GET',
                contentType: 'application/json',
                dataType: "json",
                cache: false,
                data: gestionnaire,
                success: function (data, response) {
                    notifySuccess('Requête envoyée');
                }, error: function (e, response, error) {
                    console.log(e)
                    console.log(response)
                    console.log(error)
                    let errorResponse = e.responseJSON;
                    notifyError(errorResponse.message);
                }
            });
        }*/

});