const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + '/dotations?customer=' + getSelectedCustomerNcs())
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + '/dotations?customer=' + getSelectedCustomerNcs(),
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
    }
});
// PDF
window.jsPDF = window.jspdf.jsPDF;
$(async () => {
    let data = await source.load();
    let display = false;
    if (data.displayDate === 'Y') {
        display = true;
    }
    const dataGrid = $('#gridContainer').dxDataGrid({
            dataSource: data.list,
            columns: [
                {
                    visible: false,
                    allowExporting: false,
                    dataField: 'id',
                    caption: 'ID',
                    dataType: 'number'
                },
                {
                    visible: false,
                    dataField: 'client',
                    caption: 'Client',
                    dataType: 'string'
                },
                {
                    visible: false,
                    dataField: 'adresse',
                    caption: 'Adresse',
                    dataType: 'number'
                },
                {
                    dataField: 'nomClient',
                    caption: 'Site',
                    dataType: 'string'
                },
                {
                    dataField: 'barcode',
                    caption: 'Code barre',
                    dataType: 'number'
                },
                {
                    dataField: 'porteur',
                    caption: 'Porteur',
                    dataType: 'string'
                },
                {
                    dataField: 'armoire',
                    caption: 'Armoire',
                    dataType: 'number'
                },
                {
                    dataField: 'cassette',
                    caption: 'Cassette',
                    dataType: 'number'
                },
                {
                    dataField: 'nature',
                    caption: 'Nature',
                    dataType: 'string'
                },
                {
                    dataField: 'article',
                    caption: 'Article',
                    dataType: 'string'
                },
                {
                    dataField: 'description',
                    caption: 'Description',
                    dataType: 'string'
                },
                {
                    dataField: 'miseService',
                    caption: 'Mise en service',
                    dataType: 'date',
                    format: 'dd/MM/yyyy',
                    visible: display
                },
                {
                    dataField: 'dateDernierPassage',
                    caption: 'Dernier passage',
                    dataType: 'datetime',
                    format: 'dd/MM/yyyy HH:mm',
                    visible: false,
                },
                {
                    dataField: 'stop',
                    caption: 'Demandes en cours',
                    dataType: 'string'
                },
                {
                    dataField: 'dateIn',
                    caption: 'Dernier scannage entrée',
                    dataType: 'date',
                    format: 'dd/MM/yyyy'
                },
                {
                    dataField: 'dateOUT',
                    caption: 'Dernier scannage sortie',
                    dataType: 'date',
                    format: 'dd/MM/yyyy'
                },
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
                storageKey: 'dotationStorage',
            },
            onExporting(e) {
                if (e.format === 'xlsx') {
                    const workbook = new ExcelJS.Workbook();
                    const worksheet = workbook.addWorksheet('Dotations');
                    DevExpress.excelExporter.exportDataGrid({
                        component: e.component,
                        worksheet,
                        autoFilterEnabled: true,
                    }).then(() => {
                        workbook.xlsx.writeBuffer().then((buffer) => {
                            saveAs(new Blob([buffer],
                                {type: 'application/octet-stream'}), 'Dotations.xlsx');
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
                        doc.save('Dotations.pdf');
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
            },

        }
    ).dxDataGrid('instance');

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

});