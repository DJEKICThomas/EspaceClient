import React, { useState, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import 'devextreme/dist/css/dx.material.orange.light.css';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";
import { useTranslation } from "react-i18next"; // 1. Import du hook

function Orders({ onLogout, onGoToInvoices, onGoToOffers, onGoToHome, onGoToHistoric, onGoToAdmin }) {
    const { t, i18n } = useTranslation(); // 2. Initialisation
    const [ordersData, setOrdersData] = useState([]);

    // Format local dynamique
    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    const totalOrders = ordersData.length;
    const latestOrder = ordersData.length > 0 ? ordersData[0] : null;

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/orders/user?userId=${userId}`, {
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
        { dataField: 'document', caption: t('col_order_num'), alignment: 'center' },
        {
            dataField: 'docCreation',
            caption: t('date'),
            dataType: 'date',
            alignment: 'left',
            format: 'dd/MM/yyyy',
            sortOrder: 'desc',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.docCreation)) {
                    return new Date(rowData.docCreation[0], rowData.docCreation[1] - 1, rowData.docCreation[2]);
                }
                return rowData.docCreation;
            }
        },
        { dataField: 'docDes', caption: t('label'), alignment:'left', },
        { dataField: 'reference', caption: t('reference'), alignment:'left' },
        { dataField: 'fournisseur', caption: t('supplier'), alignment:'left' },
        {
            dataField: 'montantTotal',
            caption: t('amount'),
            alignment: 'center',
            cellRender: (cellData) => {
                return cellData.value !== undefined && cellData.value !== null
                    ? cellData.value.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                    : (0).toLocaleString(locale, { style: 'currency', currency: 'EUR' });
            }
        }
    ];

    return (
        <div className="orders-container container-fluid p-3" style={{ backgroundColor: '#f8f9fa', minHeight: '100vh', display: 'block' }}>
            <Header onLogout={onLogout} />
            <Navigation
                activeTab="Commandes"
                onLogout={onLogout}
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToOrders={() => {}}
                onGoToAdmin={onGoToAdmin}
            />

            <div className="row">
                <div className="col-12 col-md-6 mb-3">
                    <SummaryCard
                        title={t('my_orders')}
                        value={totalOrders > 0 ? `${totalOrders} ` + t('orders_count') : t('no_orders')}
                        subValue={totalOrders > 0 ? t('track_current_orders') : t('place_first_order')}
                        color="#ff9800"
                    />
                </div>

                <div className="col-12 col-md-6 mb-3">
                    <SummaryCard
                        title={t('last_order')}
                        value={latestOrder
                            ? latestOrder.montantTotal.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                            : "-"
                        }
                        details={[
                            {
                                label: t('placed_on'),
                                value: latestOrder && Array.isArray(latestOrder.docCreation)
                                    ? new Date(latestOrder.docCreation[0], latestOrder.docCreation[1] - 1, latestOrder.docCreation[2]).toLocaleDateString(locale)
                                    : "-"
                            },
                            { label: t('reference'), value: latestOrder ? latestOrder.document : "-" }
                        ]}
                        color="#2980b9"
                    />
                </div>
            </div>

            <div className="shadow-sm bg-white p-2 p-md-3 rounded overflow-auto mb-4">
                <CustomGrid
                    dataSource={ordersData}
                    columns={columnConfig}
                    keyExpr="document"
                    columnAutoWidth={true}
                    wordWrapEnabled={true}
                    masterDetail={{
                        enabled: true,
                        showExpandIcon: false,
                        template: (container, options) => {
                            const root = createRoot(container);
                            root.render(<GenericDetail data={options.data} {...orderDetailConfig} />, container);
                        }
                    }}
                />
            </div>
        </div>
    );
}

export default Orders;