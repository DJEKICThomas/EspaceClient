const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + "/requests/customer?ncs=" + getSelectedCustomerNcs())
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + "/requests/customer?ncs=" + getSelectedCustomerNcs(),
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


$(() => {
    const dataGrid = $('#gridContainer').dxDataGrid({
        dataSource: source,
        columns: [{
            visible: false,
            allowExporting: false,
            dataField: 'id',
            caption: 'ID',
            dataType: 'number'
        }, {
            dataField: 'site',
            caption: 'Site',
            dataType: 'String'
        }, {
            dataField: 'barcode',
            caption: 'Code barre',
            dataType: 'number'
        }, {
            dataField: 'article',
            caption: 'Article',
            dataType: 'string'
        }, {
            dataField: 'porteur',
            caption: 'Porteur',
            dataType: 'string'
        }, {
            dataField: 'rem',
            caption: 'Stop',
            dataType: 'string'
        }, {
            dataField: 'dtb',
            caption: 'Date début',
            dataType: 'date',
            format: 'dd/MM/yyyy'
        }/*, {
            dataField: 'dtf',
            caption: 'Date fin',
            dataType: 'date',
            format: 'dd/MM/yyyy'
        }*/, {
            dataField: 'dtr',
            caption: 'Date de réalisation',
            dataType: 'date',
            format: 'dd/MM/yyyy'
        }/*,
            {
                dataField: 'usc',
                caption: 'Utilisateur',
                dataType: 'string'
            },{
                dataField: 'stop',
                caption: 'Stop',
                dataType: 'string'
            },*/
        ],
        noDataText: 'Pas de données',
        height: '75vh',
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
            operationDescriptions: {
                between: "Entre",
                contains: "Contient",
                endsWith: "Termine par",
                startsWith: "Commence avec",
                equal: "Est égal",
                notEqual: "Pas égal",
                greaterThan: "Strictement supérieur",
                greaterThanOrEqual: "Supérieur ou égal",
                lessThan: "Strictement inférieur",
                lessThanOrEqual: "Inférieur ou égal",
                notContains: "Ne contient pas",
            },
            resetOperationText: 'Réinitialiser',
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
            allowExportSelectedData: true,
            formats: ['xlsx', 'pdf'],
            texts: {
                exportAll: 'Export {0}'
            }
        },
        onExporting(e) {
            if (e.format === 'xlsx') {
                const workbook = new ExcelJS.Workbook();
                const worksheet = workbook.addWorksheet('SuiviDemandesClients');
                DevExpress.excelExporter.exportDataGrid({
                    component: e.component,
                    worksheet,
                    autoFilterEnabled: true,
                }).then(() => {
                    workbook.xlsx.writeBuffer().then((buffer) => {
                        saveAs(new Blob([buffer], {type: 'application/octet-stream'}), 'SuiviDemandesClients.xlsx');
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
                    doc.save('SuiviDemandesClients.pdf');
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
            storageKey: 'customerRequestStorage',
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