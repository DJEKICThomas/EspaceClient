const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        const userMail = localStorage.getItem('username');
        // return $.getJSON(apiURL + "/requests/user?userMail=" + (userMail != null ? userMail : ''))
        //     .done(function (result) {
        //         d.resolve(result);
        //     })
        //     .fail(function (e) {
        //         notifyError(e.responseJSON.message)
        //     });
        $.ajax({
            url: apiURL + "/requests/user?userMail=" + (userMail != null ? userMail : ''),
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                d.resolve(result)
            },
            error: function (e) {
                notifyError(e.responseJSON.message);
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
            dataField: 'site',
            caption: 'Site',
        }, {
            dataField: 'date',
            caption: 'Date de demande',
            dataType: 'date',
            format: 'dd/MM/yyyy'
        }, {
            dataField: 'typDes',
            caption: 'Type de demande',
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
        columnFixing: {
            enabled: true,
        },
        stateStoring: {
            enabled: true,
            type: 'localStorage',
            storageKey: 'myRequestStorage',
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
        masterDetail: {
            enabled: true,
            async template(container, options) {
                const requestDetail = options.data;
                $('<div>').dxForm({
                    elementAttr: {
                        id: "elementId",
                    },
                    items: [{
                        dataField: 'Texte',
                        editorType: 'dxTextArea',
                        id: 'idComment',
                        name: 'comment',
                        editorOptions: {
                            height: '25vh',
                            readOnly: true,
                            value: requestDetail.texte,
                        }
                    }],
                }).appendTo(container)
            },
        },
    }).dxDataGrid('instance');

});