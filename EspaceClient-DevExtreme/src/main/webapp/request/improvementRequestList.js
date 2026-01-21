const source = new DevExpress.data.CustomStore({
    key: 'id',
    load: function () {
        let d = $.Deferred();
        // return $.getJSON(apiURL + "/requests/improvement")
        //     .done(function (result) {
        //         console.log(result)
        //         d.resolve(result);
        //     })
        //     .fail(function () {
        //         throw "Error";
        //     });
        $.ajax({
            url: apiURL + "/requests/improvement",
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
            dataField: 'user',
            caption: 'Utilisateur',
            dataType: 'string'
        }, {
            dataField: 'date',
            caption: 'Date',
            dataType: 'date',
            format: 'dd/MM/yyyy'
        }, {
            dataField: 'ncs',
            caption: 'NCS', // TODO : ajouter ncs dans dto => entité => bd
            dataType: 'string'
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
        columnAutoWidth: false,
        columnFixing: {
            enabled: true,
        },
        stateStoring: {
            enabled: true,
            type: 'localStorage',
            storageKey: 'improvementRequestListStorage',
        },
        masterDetail: {
            enabled: true,
            template(container, options) {
                const request = options.data;

                $('<div>')
                    .addClass('master-detail-caption')
                    .text('Message :')
                    .appendTo($('<strong>').appendTo(container));

                $('<div>').addClass('messageClass').text(request.message).appendTo(container);
            },
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
    }).dxDataGrid('instance');
});