const carrierStore = new DevExpress.data.CustomStore({
    key: 'carrierStore',
    load: function (loadOptions) {
        let ncsId = loadOptions.ncsId;
        let carrier = loadOptions.carrier;
        if (ncsId && carrier) {
            let d = $.Deferred();
            // $.getJSON(apiURL + '/porteurs/details?cusId=' + ncsId + '&porteur=' + carrier)
            //     .done(function (result) {
            //         d.resolve(result);
            //     });
            // return d.promise();
            $.ajax({
                url: apiURL + '/porteurs/details?cusId=' + ncsId + '&porteur=' + carrier,
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

$(async () => {
    DevExpress.localization.locale("fr");
    const formData = await carrierStore.load({carrier: recievedCarrier, ncsId: recievedNcsId});

    const form = $('#request_form').dxForm({
        formData: formData,
        labelMode: 'floating',
        items: [{
            itemType: 'group',
            colCount: 4,
            caption: '',
            items: [{
                colSpan: 1,
                itemType: 'simple',
                dataField: 'carrierName',
                label: {
                    text: 'Porteur',
                },
                editorOptions: {
                    readOnly: true,
                },
            }, {
                colSpan: 2,
                itemType: 'simple',
                dataField: 'site',
                label: {
                    text: 'Site',
                },
                editorOptions: {
                    readOnly: true,
                },
            }],
        }, {
            itemType: 'group',
            caption: 'Type de demande',
            items: [{
                itemType: 'tabbed',
                tabs: [{
                    colCount: 2,
                    title: 'Rentrée',
                    items: [{
                        colSpan: 1,
                        editorType: 'dxDateBox',
                        dataField: 'dateRentreeFrom',
                        label: {
                            text: 'À retirer à partir du',
                        },
                        editorOptions: {
                            value: '',
                            displayFormat: 'dd/MM/yyyy',
                            width: "20vw",
                            elementAttr: {
                                id: "dateRentreeFrom",
                            }
                        },
                    }, {
                        itemType: 'button',
                        buttonOptions: {
                            text: 'Valider',
                            useSubmitBehavior: true,
                            type: 'default',
                            onClick: function (e) {
                                saveRequest(e);
                            }
                        }
                    }, {
                        colSpan: 2,
                        editorType: 'dxDataGrid',
                        dataField: 'barcodeList',
                        editorOptions: {
                            elementAttr: {
                                id: "formDatagrid",
                            },
                            dataSource: formData.newRequestDetailDTOList,
                            selection: {
                                mode: 'multiple',
                            },
                            columns: [{
                                dataField: 'artDes',
                                caption: 'Tous les vêtements de ce porteur',
                                sortIndex: 1,
                                sortOrder: 'asc',
                                // allowEditing: false,
                            }, {
                                dataField: 'nature',
                                caption: 'Nature',
                                sortIndex: 0,
                                sortOrder: 'asc',
                                calculateDisplayValue: function (rowData) {
                                    if (rowData.nature === 'L') {
                                        return 'Location';
                                    } else {
                                        return 'Vente entretien';
                                    }
                                }
                                // allowEditing: false,
                            }, {
                                dataField: 'qty',
                                caption: 'Nombre de vêtements',
                                // allowEditing: false,
                            }, {
                                dataField: 'inputQty',
                                caption: 'Vêtements à rentrer',
                                // dataType: 'number',
                                cssClass: 'inputQtyStyle',
                                cellTemplate: function (container, options) {
                                    $('<div id="numberBox' + options.data.nominId + '">').dxNumberBox({
                                        name: 'inputQty',
                                        step: 0,
                                    }).appendTo(container);
                                }
                            }],
                            onSelectionChanged(e) {
                                let selectedArray = e.selectedRowsData;
                                let deselectedArray = e.currentDeselectedRowKeys;
                                for (let i = 0; i < selectedArray.length; i++) {
                                    let dxNumberBox = $('#numberBox' + selectedArray[i].nominId).dxNumberBox('instance');
                                    if (dxNumberBox) dxNumberBox.option('value', selectedArray[i].qty);
                                }
                                for (let i = 0; i < deselectedArray.length; i++) {
                                    let dxNumberBox = $('#numberBox' + deselectedArray[i].nominId).dxNumberBox('instance');
                                    if (dxNumberBox) dxNumberBox.option('value', 0);
                                }
                            },
                        },
                    }, {
                        colSpan: 2,
                        template: function () {
                            return $("<div>").append(
                                $("<i>").text("Les vêtements vente entretien seront d'office remarqués en vêtements réserve sauf avis contraire à indiquer dans le commentaire ci-dessous"),
                                $("<div id='returnText'>").dxTextArea({
                                    dataField: 'returnText',
                                    value: '',
                                    height: '20vh',
                                    label: 'Commentaire',
                                })
                            );
                        }
                    }],
                }, {
                    title: 'Vêtement Manquant',
                    items: [{
                        itemType: 'empty',
                        colCount: 1,
                    }, {
                        itemType: 'button',
                        buttonOptions: {
                            text: 'Valider',
                            useSubmitBehavior: true,
                            type: 'default',
                            onClick: function (e) {
                                saveRequest(e);
                            }
                        }
                    }, {
                        editorType: 'dxDataGrid',
                        dataField: 'missArtList',
                        editorOptions: {
                            elementAttr: {
                                id: "missArtList",
                            },
                            dataSource: formData.newRequestDetailDTOList,
                            selection: {
                                mode: 'multiple',
                                allowSelectAll: false
                            },
                            columns: [{
                                dataField: 'artDes',
                                caption: 'Vêtements de ce porteur',
                                sortIndex: 1,
                                sortOrder: 'asc',
                            }, {
                                dataField: 'nature',
                                caption: 'Nature',
                                sortIndex: 0,
                                sortOrder: 'asc',
                                calculateDisplayValue: function (rowData) {
                                    if (rowData.nature === 'L') {
                                        return 'Location';
                                    } else {
                                        return 'Vente entretien';
                                    }
                                }
                            }, {
                                dataField: 'qty',
                                caption: 'Nombre de vêtements en circulation pour ce porteur',
                                width: '30%',
                            }],
                        },
                    }, {
                        template: function () {
                            return $("<div>").append(
                                $("<div id='missText'>").dxTextArea({
                                    dataField: 'missText',
                                    value: '',
                                    height: '20vh',
                                    label: 'Commentaire',
                                })
                            );
                        }
                    }],
                }, {
                    title: 'Réparation',
                    items: [{
                        itemType: 'empty',
                        colCount: 1,
                    }, {
                        itemType: 'button',
                        buttonOptions: {
                            text: 'Valider',
                            useSubmitBehavior: true,
                            type: 'default',
                            onClick: function (e) {
                                saveRequest(e);
                            }
                        }
                    }, {
                        editorType: 'dxDataGrid',
                        dataField: 'repairArtList',
                        editorOptions: {
                            elementAttr: {
                                id: "repairArtList",
                            },
                            dataSource: formData.newRequestDetailDTOList,
                            selection: {
                                mode: 'multiple',
                                allowSelectAll: false
                            },
                            columns: [{
                                dataField: 'artDes',
                                caption: 'Vêtements de ce porteur',
                                sortIndex: 1,
                                sortOrder: 'asc',
                            }, {
                                dataField: 'nature',
                                caption: 'Nature',
                                sortIndex: 0,
                                sortOrder: 'asc',
                                calculateDisplayValue: function (rowData) {
                                    if (rowData.nature === 'L') {
                                        return 'Location';
                                    } else {
                                        return 'Vente entretien';
                                    }
                                }
                            }, {
                                dataField: 'qty',
                                caption: 'Nombre de vêtements en circulation pour ce porteur',
                                width: '30%',
                            }],
                        },
                    }, {
                        template: function () {
                            return $("<div>").append(
                                $("<div id='repairText'>").dxTextArea({
                                    dataField: 'repairText',
                                    value: '',
                                    height: '20vh',
                                    label: 'Commentaire',
                                })
                            );
                        }
                    }],
                }, {
                    title: 'Modification',
                    items: [{
                        itemType: 'empty',
                        colCount: 1,
                    }, {
                        itemType: 'button',
                        buttonOptions: {
                            text: 'Valider',
                            useSubmitBehavior: true,
                            type: 'default',
                            onClick: function (e) {
                                saveRequest(e);
                            }
                        }
                    }, {
                        editorType: 'dxDataGrid',
                        dataField: 'modifyArtList',
                        editorOptions: {
                            elementAttr: {
                                id: "modifyArtList",
                            },
                            dataSource: formData.newRequestDetailDTOList,
                            columns: [{
                                dataField: 'artDes',
                                caption: 'Vêtements de ce porteur',
                                sortIndex: 1,
                                sortOrder: 'asc',
                                // allowEditing: false,
                            }, {
                                dataField: 'nature',
                                caption: 'Nature',
                                sortIndex: 0,
                                sortOrder: 'asc',
                                calculateDisplayValue: function (rowData) {
                                    if (rowData.nature === 'L') {
                                        return 'Location';
                                    } else {
                                        return 'Vente entretien';
                                    }
                                }
                            }, {
                                dataField: 'qty',
                                caption: 'Nombre de vêtements en circulation pour ce porteur',
                                width: '30%',
                            }, {
                                dataField: 'modifyComment',
                                caption: 'Commentaire',
                                cssClass: 'inputQtyStyle',
                                cellTemplate: function (container, options) {
                                    $('<div id="modifyTextBox' + options.data.nominId + '">').dxTextBox({
                                        name: 'modifyComment',
                                    }).appendTo(container);
                                }
                            }],
                        },
                    }],
                }, {
                    title: 'Changement taille',
                    items: [{
                        itemType: 'empty',
                        colCount: 1,
                    }, {
                        itemType: 'button',
                        buttonOptions: {
                            text: 'Valider',
                            useSubmitBehavior: true,
                            type: 'default',
                            onClick: function (e) {
                                saveRequest(e);
                            }
                        }
                    }, {
                        editorType: 'dxDataGrid',
                        dataField: 'changeSizeArtList',
                        editorOptions: {
                            elementAttr: {
                                id: 'changeSizeArtList',
                            },
                            dataSource: formData.newRequestDetailDTOList,
                            columns: [{
                                dataField: 'artDes',
                                caption: 'Vêtements de ce porteur',
                                sortIndex: 1,
                                sortOrder: 'asc',
                                // allowEditing: false,
                            }, {
                                dataField: 'nature',
                                caption: 'Nature',
                                sortIndex: 0,
                                sortOrder: 'asc',
                                calculateDisplayValue: function (rowData) {
                                    if (rowData.nature === 'L') {
                                        return 'Location';
                                    } else {
                                        return 'Vente entretien';
                                    }
                                }
                            }, {
                                dataField: 'qty',
                                caption: 'Nombre de vêtements en circulation pour ce porteur',
                                width: '30%',
                            }, {
                                dataField: 'changeSizeComment',
                                caption: 'Taille et/ou commentaire',
                                cssClass: 'inputQtyStyle',
                                cellTemplate: function (container, options) {
                                    $('<div id="changeSizeTextBox' + options.data.nominId + '">').dxTextBox({
                                        name: 'changeSizeComment',
                                    }).appendTo(container);
                                }
                            }],
                        }
                    }],
                }, {
                    title: 'Finition & Propreté',
                    items: [{
                        itemType: 'empty',
                        colCount: 1,
                    }, {
                        itemType: 'button',
                        buttonOptions: {
                            text: 'Valider',
                            useSubmitBehavior: true,
                            type: 'default',
                            onClick: function (e) {
                                saveRequest(e);
                            }
                        }
                    }, {
                        editorType: 'dxDataGrid',
                        dataField: 'finishCleanArtList',
                        editorOptions: {
                            elementAttr: {
                                id: "finishCleanArtList",
                            },
                            dataSource: formData.newRequestDetailDTOList,
                            columns: [{
                                dataField: 'artDes',
                                caption: 'Vêtements de ce porteur',
                                sortIndex: 1,
                                sortOrder: 'asc',
                                // allowEditing: false,
                            }, {
                                dataField: 'nature',
                                caption: 'Nature',
                                sortIndex: 0,
                                sortOrder: 'asc',
                                calculateDisplayValue: function (rowData) {
                                    if (rowData.nature === 'L') {
                                        return 'Location';
                                    } else {
                                        return 'Vente entretien';
                                    }
                                }
                            }, {
                                dataField: 'qty',
                                caption: 'Nombre de vêtements en circulation pour ce porteur',
                                width: '30%',
                            }, {
                                dataField: 'finishCleanComment',
                                caption: 'Commentaire',
                                cssClass: 'inputQtyStyle',
                                cellTemplate: function (container, options) {
                                    $('<div id="finishCleanTextBox' + options.data.nominId + '">').dxTextBox({
                                        name: 'finishCleanComment',
                                    }).appendTo(container);
                                }
                            }],
                        },
                    }],
                }, {
                    title: 'Autre',
                    items: [{
                        itemType: 'empty',
                        colCount: 1,
                    }, {
                        itemType: 'button',
                        buttonOptions: {
                            text: 'Valider',
                            useSubmitBehavior: true,
                            type: 'default',
                            onClick: function (e) {
                                saveRequest(e);
                            }
                        }
                    }, {
                        editorType: 'dxTextArea',
                        dataField: 'otherText',
                        label: {
                            text: 'Commentaire',
                        },
                        editorOptions: {
                            value: '',
                            height: '20vh',
                        },
                    }],
                }],
            }],
        }, /*{
            itemType: 'button',
            buttonOptions: {
                useSubmitBehavior: true,
                text: 'Valider',
                type: 'default',
                elementAttr: {
                    id: "saveRequestButton",
                },
                onClick: function (e) {
                    saveRequest(e);
                }
            }
        }*/],
    }).dxForm('instance');

})

function updateValue(form, key, value) {
    if (form) {
        form.updateData(key, value);
    }
}

function saveRequest(e) {
    const dxForm = $('#request_form').dxForm('instance');
    if (dxForm) {
        let validResult = dxForm.validate();
        if (validResult.isValid) {
            const formData = dxForm.option('formData');
            let artListDTO = [];
            let missListDTO = [];
            let repairListDTO = [];
            let modifyArtListDTO = [];
            let changeSizeArtListDTO = [];
            let finishCleanArtListDTO = [];
            for (let i = 0; i < formData.newRequestDetailDTOList.length; i++) {
                const element = formData.newRequestDetailDTOList[i];
                let barcodeListSize = element.qty;
                let actualReturnNumberBox = $('#numberBox' + element.nominId).dxNumberBox('instance');
                let actualModifyTextBox = $('#modifyTextBox' + element.nominId).dxTextBox('instance');
                let actulChangeSizeTextBox = $('#changeSizeTextBox' + element.nominId).dxTextBox('instance');
                let actualFinishCleanTextBox = $('#finishCleanTextBox' + element.nominId).dxTextBox('instance');
                let returnQtyInput = actualReturnNumberBox ? actualReturnNumberBox.option('value') : '';
                let modifyComment = actualModifyTextBox ? actualModifyTextBox.option('value') : '';
                let changeSizeComment = actulChangeSizeTextBox ? actulChangeSizeTextBox.option('value') : '';
                let finishCleanComment = actualFinishCleanTextBox ? actualFinishCleanTextBox.option('value') : '';
                if (returnQtyInput > barcodeListSize) {
                    notifyError('La quantité encodée est supérieure au nombre de vêtements !')
                    return;
                }
                if (returnQtyInput > 0) {
                    artListDTO.push(returnQtyInput + ' ' + element.artDes + ' encodé(e)(s) pour rentrée ');
                }

                if (modifyComment) {
                    modifyArtListDTO.push(element.artDes + ' : ' + modifyComment);
                }

                if (changeSizeComment) {
                    changeSizeArtListDTO.push(element.artDes + ' : ' + changeSizeComment);
                }

                if (finishCleanComment) {
                    finishCleanArtListDTO.push(element.artDes + ' : ' + finishCleanComment);
                }

            }
            let dateRentreeFromDateBox = $('#dateRentreeFrom').dxDateBox('instance');
            let dateRentree;
            if (dateRentreeFromDateBox) {
                const dateValue = dateRentreeFromDateBox.option('value');
                dateRentree = new Date(dateValue)/*.toLocaleDateString("en-GB")*/;
                if (!isValidDate(dateRentree) || !dateValue) {
                    dateRentree = undefined;
                }
            }
            let returnTextTextArea = $('#returnText').dxTextArea('instance');
            let returnText = '';
            if (returnTextTextArea) {
                returnText = 'Commentaire utilisateur : \n'.concat(returnTextTextArea.option('value'));
            }

            let missDatagrid = $('#missArtList').dxDataGrid('instance');
            if (missDatagrid) {
                missDatagrid.getSelectedRowsData().forEach(item => missListDTO.push(item.artDes));
            }
            let missTextArea = $('#missText').dxTextArea('instance');
            let missTextValue = '';
            if (missTextArea) {
                missTextValue = missTextArea.option('value');
            }

            let repairDatagrid = $('#repairArtList').dxDataGrid('instance');
            if (repairDatagrid) {
                repairDatagrid.getSelectedRowsData().forEach(item => repairListDTO.push(item.artDes));
            }
            let repairTextArea = $('#repairText').dxTextArea('instance');
            let repairTextValue = '';
            if (repairTextArea) {
                repairTextValue = repairTextArea.option('value');
            }

            if (((!dateRentree && artListDTO.length > 0) || (dateRentree && artListDTO.length === 0))) {
                notifyError('Veuillez compléter la zone Rentée');
            } else if ((!missTextValue && missListDTO.length > 0) || (missTextValue && missListDTO.length === 0)) {
                notifyError('Veuillez compléter la zone Vêtement Manquant');
            } else if ((!repairTextValue && repairListDTO.length > 0) || (repairTextValue && repairListDTO.length === 0)) {
                notifyError('Veuillez compléter la zone Réparation');
            } else {
                let json = JSON.stringify({
                    cupboard: formData.cupboard,
                    cassette: formData.cassette,
                    carrier: formData.carrier,
                    site: formData.site,
                    customerDTO: {
                        id: formData.ncsId,
                    },
                    user: localStorage.getItem('username'),
                    requestDetailDTO: [{
                        returnDate: dateRentree,
                        text: dateRentree ? ('A retirer à partir du ' + dateRentree.toLocaleDateString("en-GB") + '\n' + artListDTO.join('\n') + '\n' + returnText) : '',
                        // barcodeList: barcodeListDTO,
                        typ: 'RETURN'
                    }, {
                        text: missListDTO.join('\n') + '\n' + missTextValue,
                        typ: 'MISS'
                    }, {
                        text: repairListDTO.join('\n') + '\n' + repairTextValue,
                        typ: 'REPAIR'
                    }, {
                        text: modifyArtListDTO.join('\n'),
                        typ: 'MODIFY'
                    }, {
                        text: changeSizeArtListDTO.join('\n'),
                        typ: 'CHANGESIZE'
                    }, {
                        text: finishCleanArtListDTO.join('\n'),
                        typ: 'FINISHANDCLEAN'
                    }, {
                        text: formData.otherText,
                        typ: 'OTHER'
                    }],
                });
                $.ajax({
                    url: apiURL + '/requests',
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    cache: false,
                    data: json,
                    success: function (data, response) {
                        notifySuccess('Requête envoyée');
                        dxForm.resetValues();
                        let formDatagrid = $('#formDatagrid').dxDataGrid('instance');
                        if (formDatagrid) formDatagrid.deselectAll();
                        location.replace('../porteur/porteur.jsp');
                    }, error: function (e, response, error) {
                        let errorResponse = e.responseJSON;
                        notifyError(errorResponse.message);
                    }, beforeSend: function (xhr) {
                        xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
                    }
                })
            }
        }
    }
}