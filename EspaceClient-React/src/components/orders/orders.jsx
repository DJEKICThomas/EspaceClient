import React, { useState, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";
import { useTranslation } from "react-i18next";

function Orders({ onLogout, onGoToInvoices, onGoToOffers, onGoToHome, onGoToHistoric, onGoToAdmin, onGoToDocument }) {
    const { t, i18n } = useTranslation();
    const [ordersData, setOrdersData] = useState([]);

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
        { dataField: 'document', caption: t('col_order_num'), alignment: 'center', width:'300', },
        {
            dataField: 'docCreation',
            caption: t('date'),
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            sortOrder: 'desc',
            width:'300',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.docCreation)) {
                    return new Date(rowData.docCreation[0], rowData.docCreation[1] - 1, rowData.docCreation[2]);
                }
                return rowData.docCreation;
            }
        },
        { dataField: 'docDes', caption: t('label'), alignment:'left', width:'300', },
        { dataField: 'reference', caption: t('reference'), alignment:'left', width:'300', },
        { dataField: 'fournisseur', caption: t('supplier'), alignment:'left', width:'300', },
        {
            dataField: 'montantTotal',
            caption: t('amount'),
            alignment: 'center',
            width:'300',
            cellRender: (cellData) => {
                return cellData.value !== undefined && cellData.value !== null
                    ? cellData.value.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                    : (0).toLocaleString(locale, { style: 'currency', currency: 'EUR' });
            }
        }
    ];

    return (
        <div className="container-fluid dx-viewport" style={{ minHeight: '100vh', backgroundColor: '#f3f3f7' }}>
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
            />

            <div className="row g-3 mb-4">
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
                    />
                </div>
            </div>

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden">
                <CustomGrid
                    dataSource={ordersData}
                    columns={columnConfig}
                    keyExpr="document"
                    showSearch={true}
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
    );
}

export default Orders;