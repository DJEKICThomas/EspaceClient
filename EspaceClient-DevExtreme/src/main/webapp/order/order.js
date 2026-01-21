const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + '/orders?customer=' + getSelectedCustomerNcs())
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + '/orders?customer=' + getSelectedCustomerNcs(),
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
$(() => {
    const dataGrid = $('#gridContainer').dxDataGrid({
        dataSource: source,
        columns: [
            {
                visible: false,
                allowExporting: false,
                dataField: 'id',
                caption: 'ID',
                dataType: 'number'
            },
            {
                dataField: 'customer',
                caption: 'Site',
                dataType: 'string'
            },
            {
                visible: false,
                dataField: 'ncs',
                caption: 'NCS',
                dataType: 'string'
            },
            {
                visible: false,
                dataField: 'nda',
                caption: 'NDA',
                dataType: 'number'
            },
            {
                dataField: 'carrier',
                caption: 'Porteur',
                dataType: 'string'
            },
            {
                dataField: 'type',
                caption: 'Type',
                dataType: 'string'
            },
            {
                dataField: 'date',
                caption: 'Date',
                dataType: 'date',
                format: 'dd/MM/yyyy'
            },
            {
                dataField: 'quantity',
                caption: 'Quantité',
                dataType: 'number'
            },
            {
                dataField: 'article',
                caption: 'Article',
                dataType: 'string'
            },
            {
                dataField: 'code',
                caption: 'Numéro de commande',
                dataType: 'string'
            },
            {
                dataField: 'reference',
                caption: 'Référence',
                dataType: 'string'
            },
            {
                dataField: 'delivery',
                caption: 'Livraison',
                dataType: 'string'
            },
            {
                dataField: 'from',
                caption: 'Origine',
                dataType: 'string'
            }
        ],
        noDataText: 'Pas de données',
        height: '75vh',
        width: '90wh',
        columnChooser: {
            enabled: false,
            mode: "dragAndDrop",
            emptyPanelText: 'Déplacer les colonnes ici pour les cacher'
        },
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
            visible: true,
            emptyPanelText: 'Déplacer les colonnes ici pour regrouper les données',
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
        onExporting(e) {
            if (e.format === 'xlsx') {
                const workbook = new ExcelJS.Workbook();
                const worksheet = workbook.addWorksheet('Commandes');
                DevExpress.excelExporter.exportDataGrid({
                    component: e.component,
                    worksheet,
                    autoFilterEnabled: true,
                }).then(() => {
                    workbook.xlsx.writeBuffer().then((buffer) => {
                        saveAs(new Blob([buffer], {type: 'application/octet-stream'}), 'Commandes.xlsx');
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
                    doc.save('Commandes.pdf');
                });
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
            storageKey: 'orderStorage',
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

});