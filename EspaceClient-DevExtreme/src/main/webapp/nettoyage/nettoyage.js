let isExport = false;
const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + '/nettoyages?customer=' + getSelectedCustomerNcs())
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        return $.ajax({
            url: apiURL + '/nettoyages?customer=' + getSelectedCustomerNcs(),
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
        // return d.promise();
    },
    // load: function (loadOptions) {
    //     // ...
    //     let d = $.Deferred();
    //     $.ajax({
    //         url: apiURL + '/nettoyages?customer=' + getSelectedCustomerNcs(),
    //         type: 'POST',
    //         contentType: 'application/json',
    //         dataType: "json",
    //         cache: false,
    //         data: JSON.stringify({
    //             take: loadOptions.take,
    //             skip: loadOptions.skip,
    //             group: loadOptions.group ? JSON.stringify(loadOptions.group) : '',
    //             filter: loadOptions.filter ? JSON.stringify(loadOptions.filter) : '',
    //             totalSummary: loadOptions.totalSummary ? JSON.stringify(loadOptions.totalSummary) : '',
    //             groupSummary: loadOptions.groupSummary ? JSON.stringify(loadOptions.groupSummary) : '',
    //             sort: loadOptions.sort ? JSON.stringify(loadOptions.sort) : '',
    //             isExport: isExport,
    //         }),
    //         success: function (data, response) {
    //             if ('data' in data) {
    //                 d.resolve({
    //                     data: data.data,
    //                     totalCount: data.totalCount,
    //                     key: data.key,
    //                 });
    //             } else {
    //                 d.resolve(result);
    //             }
    //         }, error: function (e, response, error) {
    //             let errorResponse = e.responseJSON;
    //             notifyError(errorResponse.message);
    //         }
    //     });
    //     return d.promise();
    // }
});
// PDF
window.jsPDF = window.jspdf.jsPDF;
$(() => {
    const dataGrid = $('#gridContainer').dxDataGrid({
        // remoteOperations: true,
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
                dataField: 'barcode',
                caption: 'Code barre',
                dataType: 'number'
            },
            {
                dataField: 'artDes',
                caption: 'Article',
                dataType: 'string'
            },
            {
                dataField: 'nature',
                caption: 'Nature',
                dataType: 'string'
            },
            {
                dataField: 'date',
                caption: 'Date de nettoyage',
                dataType: 'date',
                format: 'dd/MM/yyyy'
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
            pageSize: 1000,
        },
        scrolling: {
            mode: 'virtual',
            // rowRenderingMode: 'virtual',
            // columnRenderingMode: 'virtual',
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
        columnAutoWidth: false,
        columnResizingMode: 'widget',
        hoverStateEnabled: true,
        columnMinWidth: 100,
        columnFixing: {
            enabled: true,
        },
        grouping: {
            autoExpandAll: true
        },
        stateStoring: {
            enabled: true,
            type: 'localStorage',
            storageKey: 'nettoyageStorage',
        },
        onExporting(e) {
            isExport = true;
            if (e.format === 'xlsx') {
                const workbook = new ExcelJS.Workbook();
                const worksheet = workbook.addWorksheet('Nettoyages');
                DevExpress.excelExporter.exportDataGrid({
                    component: e.component,
                    worksheet,
                    autoFilterEnabled: true,
                }).then(() => {
                    isExport = false;
                    workbook.xlsx.writeBuffer().then((buffer) => {
                        saveAs(new Blob([buffer],
                            {type: 'application/octet-stream'}), 'Nettoyages.xlsx');
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
                    isExport = false;
                    doc.save('Nettoyages.pdf');
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
        }
    }).dxDataGrid('instance');

    $('#state-reset-link').on('click', () => {
        dataGrid.state(null);
    });

    // $('#autoExpand').dxCheckBox({
    //     value: true,
    //     text: 'Ouvrir les groupes',
    //     onValueChanged(data) {
    //         dataGrid.option('grouping.autoExpandAll', data.value);
    //     },
    // });

});