const items = [{typ: '3', des: 'Vente'}, {typ: '2', des: 'Entretien'}];

const sourceHeader = (nature) => new DevExpress.data.CustomStore({
    key: ['artId', 'ncsId'],
    load: function () {
        let d = $.Deferred();
        $.ajax({
            url: apiURL + "/statistics?customer=" + getSelectedCustomerNcs() + "&nature=" + nature,
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

const sourceDetail = new DevExpress.data.CustomStore({
    key: 'id',
    load: function (loadOptions) {
        if (loadOptions) {
            let d = $.Deferred();
            $.ajax({
                url: apiURL + "/statistics/details?cusId=" + loadOptions.ncsId + '&artId=' + loadOptions.artId,
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
    }
});
// PDF
window.jsPDF = window.jspdf.jsPDF;
$(() => {
    const date = new Date();
    const year3 = date.getFullYear();
    const year2 = (date.getFullYear() - 1);
    const year1 = (date.getFullYear() - 2);

    const natureSelector = $('#natureSelector').dxSelectBox({
        items: items,
        value: items[0].typ,
        width: '20vw',
        displayExpr: 'des',
        valueExpr: 'typ',
        onValueChanged(data) {
            dataGrid.option('dataSource', sourceHeader(data.value));
        },
    });

    const dataGrid = $('#gridContainer').dxDataGrid({
        dataSource: sourceHeader(items[0].typ),
        // keyExpr: 'artId',
        columns: [{
            dataField: 'site',
            caption: 'Site',
            dataType: 'string'
        }, {
            dataField: 'artDes',
            caption: 'Article',
            dataType: 'string'
        }, {
            dataField: 'sumYear1',
            caption: year1,
            dataType: 'number',
            width: '25%'
        }, {
            dataField: 'sumYear2',
            caption: year2,
            dataType: 'number',
            width: '25%'
        }, {
            dataField: 'sumYear3',
            caption: year3,
            dataType: 'number',
            width: '25%'
        }],
        noDataText: 'Pas de données',
        masterDetail: {
            enabled: true,
            async template(container, options) {
                // const statDetail = options.data.detail;
                $('<div>')
                    .addClass('master-detail-caption')
                    .text(`Détail :`)
                    .appendTo(container);

                $('<div>')
                    .dxDataGrid({
                        // dataSource: statDetail.detailListYear,
                        dataSource: await sourceDetail.load(options.data),
                        columnAutoWidth: true,
                        grouping: {
                            autoExpandAll: true
                        },
                        showBorders: true,
                        columns: [
                            {
                                dataField: 'month',
                                caption: 'Mois',
                                dataType: 'number',
                            },
                            {
                                dataField: 'year1Count',
                                caption: year1,
                                dataType: 'number',
                            },
                            {
                                dataField: 'year2Count',
                                caption: year2,
                                dataType: 'number',
                            },
                            {
                                dataField: 'year3Count',
                                caption: year3,
                                dataType: 'number',
                            }],
                    }).appendTo(container);
            },
        },
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
            mode: 'infinite'
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
            storageKey: 'statistiqueStorage',
        },
        onExporting(e) {
            if (e.format === 'xlsx') {
                const workbook = new ExcelJS.Workbook();
                const worksheet = workbook.addWorksheet('Statistiques');
                DevExpress.excelExporter.exportDataGrid({
                    component: e.component,
                    worksheet,
                    autoFilterEnabled: true,
                }).then(() => {
                    workbook.xlsx.writeBuffer().then((buffer) => {
                        saveAs(new Blob([buffer],
                            {type: 'application/octet-stream'}), 'Statistiques.xlsx');
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
                    doc.save('Statistiques.pdf');
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

    /* $('#state-reset-link').on('click', () => {
         dataGrid.state(null);
     });*/

    // $('#autoExpand').dxCheckBox({
    //     value: true,
    //     text: 'Ouvrir les groupes',
    //     onValueChanged(data) {
    //         dataGrid.option('grouping.autoExpandAll', data.value);
    //     },
    // });

});