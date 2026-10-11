const userPermissionsSource = new DevExpress.data.CustomStore({
    key: 'code',
    load: function () {
        let d = $.Deferred();
        $.ajax({
            url: apiURL + '/permission/list?mail=' + selectedUserEmail,
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

    update: function (key, data) {
        const dto = {role: key, hasAccess: data.hasAccess};
        $.ajax({
            url: apiURL + '/permission?mail=' + selectedUserEmail,
            type: 'PUT',
            contentType: 'application/json',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(dto),
            success: function (data, response, e) {
                window.location.reload();
            }, error: function (e, response, error) {
                let errorResponse = e.responseJSON;
                notifyError(errorResponse.message);
            }, beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
            }
        });
    }
});

$(() => {
    const permissionList = $('#permissionList').dxDataGrid({
        dataSource: userPermissionsSource,
        showBorders: true,
        height: '75vh',
        rowAlternationEnabled: true,
        columns: [{
            dataField: 'code',
            caption: 'Code',
            dataType: 'string',
            allowEditing: false
        }, {
            dataField: 'role',
            caption: 'Description',
            dataType: 'string',
            allowEditing: false
        }, {
            dataField: 'hasAccess',
            caption: 'Accès',
            dataType: 'boolean'
        }],
        wordWrapEnabled: true,
        paging: {
            pageSize: 100,
        },
        scrolling: {
            mode: 'standard'
        },
        editing: {
            mode: 'batch',
            allowUpdating: true,
            startEditAction: 'click',
        },
    }).dxDataGrid('instance');
});