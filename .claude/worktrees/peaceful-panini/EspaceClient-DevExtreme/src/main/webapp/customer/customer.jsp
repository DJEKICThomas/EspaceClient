<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <jsp:include page="../template.jsp"/>
    <jsp:include page="../navbar/navbar.jsp">
        <jsp:param name="code" value="ACCESS_CUSSELECTION"/>
    </jsp:include>
    <title>Client sélectionné</title>
</head>
<%
    String selectedCustomer = StringUtils.defaultIfBlank(request.getParameter("selectedCustomer"), "");
%>
<body class="dx-viewport">
<div class="container-fluid">
    <div class="container">
        <div id="form">
            <div class="dx-fieldset">
                <div class="dx-field">
                    <div class="dx-field-label" style="font-size: 22px !important;">Sélectionner un client</div>
                    <div class="dx-field-value">
                        <div id="selectBox"></div>
                        <script>
                            if (profile !== '') {
                                if (profile === 'ADMINISTRATEUR' || profile === 'DEPAIRON') {
                                    const customerStore = new DevExpress.data.CustomStore({
                                        key: 'id',
                                        scrolling: {
                                            mode: 'virtual'
                                        },
                                        paging: {
                                            pageSize: 10,
                                        },
                                        load: function (loadOptions) {
                                            let searchValue = loadOptions.searchValue;
                                            if (searchValue !== null && searchValue !== '') {
                                                let d = $.Deferred();
                                                $.ajax({
                                                    url: apiURL + '/customers/search?filter=' + searchValue,
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
                                    $(() => {
                                        $('#selectBox').dxSelectBox({
                                            dataSource: customerStore,
                                            displayExpr: 'cusNm1',
                                            valueExpr: 'cusNm1',
                                            searchEnabled: true,
                                            searchExpr: ['cusNm1', 'cusNcs'],
                                            searchMode: 'startswith',
                                            label: 'Rechercher un client',
                                            placeholder: '',
                                            noDataText: 'Pas de données',
                                            onValueChanged(data) {
                                                let selectedData = data.value;
                                                localStorage.setItem("selectedCustomer", selectedData);
                                                document.getElementById('activeCustomer').innerText = selectedData;
                                            },
                                            onInitialized: function (e) {
                                                let selectedCustomer = '<%=selectedCustomer%>';
                                                const v = e.component.option("value");
                                                if (v === null && selectedCustomer !== '') {
                                                    localStorage.setItem("selectedCustomer", selectedCustomer);
                                                    document.getElementById('activeCustomer').innerText = selectedCustomer;
                                                }
                                            }
                                        });
                                    });
                                } else {
                                    let username = localStorage.getItem('username');
                                    if (username !== '') {
                                        const customerUserStore = new DevExpress.data.CustomStore({
                                            key: 'id',
                                            scrolling: {
                                                mode: 'virtual'
                                            },
                                            paging: {
                                                pageSize: 10,
                                            },
                                            load: function () {
                                                let d = $.Deferred();
                                                // $.getJSON(apiURL + '/wm-customers?userId=' + username)
                                                //     .done(function (result) {
                                                //         d.resolve(result);
                                                //     });
                                                $.ajax({
                                                    url: apiURL + '/wm-customers?userId=' + username,
                                                    type: 'GET',
                                                    dataType: 'json',
                                                    success: function (result) {
                                                        d.resolve(result);
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
                                        });
                                        $(() => {
                                            const selectBoxInstance = $('#selectBox').dxSelectBox({
                                                dataSource: customerUserStore,
                                                displayExpr: 'selectedCustomer',
                                                // valueExpr: 'customerId',
                                                valueExpr: 'selectedCustomer',
                                                onValueChanged(data) {
                                                    let selectedData = data.value;
                                                    localStorage.setItem("selectedCustomer", selectedData);
                                                    document.getElementById('activeCustomer').innerText = selectedData;
                                                }
                                            }).dxSelectBox('instance');
                                        });
                                    }
                                }
                            }

                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    /*$(document).ready(function() {
        let selectedCustomer = '<%--<%=selectedCustomer%>--%>';
        if(selectedCustomer !== null && selectedCustomer !== '') {
            localStorage.setItem("selectedCustomer", selectedCustomer);
        }
    })*/
</script>
</body>
<%--<script type="text/javascript" src="customer.js"></script>--%>

</html>