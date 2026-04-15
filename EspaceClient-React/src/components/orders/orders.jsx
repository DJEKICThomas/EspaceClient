import React, { useState, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";
import { useTranslation } from "react-i18next";

function Orders({ onLogout, onGoToCustomerSelection, onGoToCompanySelection, onGoToInvoices, onGoToOffers, onGoToHome, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToSubscription, onGoToSettings, searchFilter, clearSearchFilter }) {
    const { t, i18n } = useTranslation();
    const [ordersData, setOrdersData] = useState([]);

    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';
    const totalOrders = ordersData.length;
    const latestOrder = ordersData.length > 0 ? ordersData[0] : null;

    const loadData = async () => {
        const ncs = localStorage.getItem('selectedNcs');
        const company = localStorage.getItem('selectedCompany');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/orders/user?userId=${userId}&ncs=${ncs}&company=${company}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setOrdersData(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    const orderDetailConfig = {
        endpoint: "orders/details",
        idField: "document",
        title: t('order_details'),
        columns: [
            { dataField: 'article', caption: t('article') },
            { dataField: 'articleLab', caption: t('designation') },
            { dataField: 'priceUnit', caption: t('unit_price') },
            { dataField: 'quantity', caption: t('quantity') }
        ]
    };

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        { dataField: 'document', caption: t('col_order_num'), alignment: 'center', width: 240 },
        {
            dataField: 'docCreation',
            caption: t('date'),
            width: 150,
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            sortOrder: 'desc',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.docCreation)) {
                    return new Date(rowData.docCreation[0], rowData.docCreation[1] - 1, rowData.docCreation[2]);
                }
                return rowData.docCreation;
            }
        },
        { dataField: 'docDes', caption: t('label'), alignment:'left', width: 250},
        { dataField: 'reference', caption: t('reference'), alignment:'left',},
        { dataField: 'fournisseur', caption: t('client'), alignment:'left', width: 350 },
        {
            dataField: 'montantTotal',
            caption: t('amount'),
            width: 150,
            alignment: 'center',
            cellRender: (cellData) => {
                return cellData.value !== undefined && cellData.value !== null
                    ? cellData.value.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                    : (0).toLocaleString(locale, { style: 'currency', currency: 'EUR' });
            }
        },
        {
            dataField: 'statut',
            caption: t('status'),
            width: 200,
            alignment: 'center',
            cellRender: (cellData) => {
                const keys = cellData.value ? cellData.value.split(' / ') : [];
                const translated = keys.map(k => t(k)).join(' / ');
                return (
                    <span style={{
                        color: '#2c3e50',
                        fontWeight: 'bold',
                        padding: '4px 8px',
                        borderRadius: '4px',
                        background: '#f8e4b4',
                        display: 'inline-block',
                        minWidth: '80px'
                    }}>
                        {translated || '-'}
                    </span>
                );
            }
        }
    ];

    const [gridFilter, setGridFilter] = useState(null);

    useEffect(() => {
        if (searchFilter) {
            setGridFilter([searchFilter.field, '=', searchFilter.value]);
        }
    }, [searchFilter]);

    useEffect(() => {
        return () => {
            if (clearSearchFilter) clearSearchFilter();
        };
    }, [clearSearchFilter]);

    return (
        <div className="container-fluid dx-viewport dx-theme-background-color"
             style={{
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
                 overflow: 'hidden'
             }}>
            <div style={{ flexShrink: 0 }}>
                <Header
                    onLogout={onLogout}
                    activeTab="Commandes"
                    onGoToHome={onGoToHome}
                    onGoToInvoices={onGoToInvoices}
                    onGoToOffers={onGoToOffers}
                    onGoToHistoric={onGoToHistoric}
                    onGoToOrders={() => {}}
                    onGoToAdmin={onGoToAdmin}
                    onGoToDocument={onGoToDocument}
                    onGoToSubscription={onGoToSubscription}
                    onGoToSettings={onGoToSettings}
                    onGoToCustomerSelection={onGoToCustomerSelection}
                    onGoToCompanySelection={onGoToCompanySelection}
                />

                <div className="row g-3 px-3 mt-1 mb-2">
                    <div className="col-12 col-md-6 d-flex">
                        <SummaryCard
                            title={t('my_orders')}
                            value={totalOrders > 0 ? `${totalOrders} ` + t('orders_count') : t('no_orders')}
                            subValue={totalOrders > 0 ? t('track_current_orders') : t('place_first_order')}
                        />
                    </div>
                    <div className="col-12 col-md-6 d-flex">
                        <SummaryCard
                            title={t('last_order')}
                            value={latestOrder ? latestOrder.montantTotal.toLocaleString(locale, { style: 'currency', currency: 'EUR' }) : "-"}
                            details={[
                                {
                                    label: t('placed_on'),
                                    value: latestOrder && Array.isArray(latestOrder.docCreation)
                                        ? new Date(latestOrder.docCreation[0], latestOrder.docCreation[1] - 1, latestOrder.docCreation[2]).toLocaleDateString(locale)
                                        : "-"
                                },
                                { label: t('reference'), value: latestOrder ? latestOrder.document : "-" }
                            ]}
                        />
                    </div>
                </div>
            </div>

            <div className="mx-3 mb-3" style={{ flex: 1, minHeight: 0, display: 'flex', flexDirection: 'column' }}>
                <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden" style={{ height: '100%' }}>
                    <CustomGrid
                        dataSource={ordersData}
                        onRefresh={() => {
                            setGridFilter(null);
                            if (clearSearchFilter) clearSearchFilter();
                            loadData();
                        }}
                        columns={columnConfig}
                        keyExpr="document"
                        height="100%"
                        showSearch={true}
                        defaultFilter={gridFilter}
                        masterDetail={{
                            enabled: true,
                            template: (container, options) => {
                                const root = createRoot(container);
                                root.render(<GenericDetail data={options.data} {...orderDetailConfig} />);
                            }
                        }}
                    />
                </div>
            </div>
        </div>
    );
}

export default Orders;