import React, { useState, useEffect } from 'react';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";
import {createRoot} from "react-dom/client";
import GenericDetail from "../widgets/genericDetail/genericDetail";

function Subscriptions({ onLogout, onGoToInvoices, onGoToOffers, onGoToHome, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToOrders }) {
    const { t, i18n } = useTranslation();
    const [subscriptionsData, setSubscriptionsData] = useState([]);

    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    // Calculs pour les SummaryCards
    const totalSubs = subscriptionsData.length;
    const activeSubsCount = subscriptionsData.filter(s => s.active).length;

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/subscriptions/user?userId=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setSubscriptionsData(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    const subscriptionDetailConfig = {
        endpoint: "subscription/details",
        idField: "document",
        title: t('subscription_articles'),
        columns: [
        ]
    };

    const handleViewPdf = (docNumber) => {
        const testPdfUrl = "/pdf/DescriptionStage2025_EspaceClient.pdf";
        const width = window.screen.availWidth;
        const height = window.screen.availHeight;

        const windowFeatures = `
        width=${width},
        height=${height},
        top=0,
        left=0,
        popup=yes,
        resizable=yes,
        scrollbars=yes
    `;

        window.open(testPdfUrl, '_blank', windowFeatures);

        console.log("Visualisation du document : " + docNumber);
    };

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        {
            datafield: 'action',
            caption: t('col_inv_action'),
            alignment: 'center',
            cellRender: (cellData) => {
                return (
                    <i
                        className="fi fi-ts-file-pdf pdf-icon-click"
                        onClick={() => handleViewPdf(cellData.data.num)}
                    />
                );
            }
        },
        { dataField: 'reference', caption: t('reference'), alignment: 'left', width: '300' },
        {
            dataField: 'startDate',
            caption: t('start_date'),
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            // Gestion du format LocalDate renvoyé par le backend
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.startDate)) {
                    return new Date(rowData.startDate[0], rowData.startDate[1] - 1, rowData.startDate[2]);
                }
                return rowData.startDate;
            }
        },
        {
            dataField: 'endDate',
            caption: t('end_date'),
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.endDate)) {
                    return new Date(rowData.endDate[0], rowData.endDate[1] - 1, rowData.endDate[2]);
                }
                return rowData.dateFin;
            }
        },
        { dataField: 'description', caption: t('label'), alignment: 'left' },
        { dataField: 'recur', caption: t('frequency'), alignment: 'center', width: '200' },
        {
            dataField: 'active',
            caption: t('status'),
            alignment: 'center',
            cellRender: (cellData) => {
                const isActive = cellData.value;
                return (
                    <span className={`badge ${isActive ? 'bg-success' : 'bg-danger'}`}>
                        {isActive ? t('active') : t('inactive')}
                    </span>
                );
            }
        }
    ];

    return (
        <div className="container-fluid dx-viewport" style={{ minHeight: '100vh', backgroundColor: '#f3f3f7' }}>
            <Header
                onLogout={onLogout}
                activeTab="Abonnements"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToOrders={onGoToOrders}
                onGoToAdmin={onGoToAdmin}
                onGoToDocument={onGoToDocument}
                onGoToSubscriptions={() => {}}
            />

            <div className="row g-3 mb-3">
                <div className="col-12 col-md-6 d-flex">
                    <SummaryCard
                        title={t('my_subscriptions')}
                        value={totalSubs > 0 ? `${totalSubs} ` + t('subscriptions') : t('no_subscriptions')}
                        details={[
                            {
                                label: t('active_now'),
                                value: <span style={{ color: '#198754', fontWeight: 'bold' }}>{activeSubsCount}</span>
                            },
                            {
                                label: t('expired'),
                                value: <span style={{ color: '#e74c3c', fontWeight: 'bold' }}>{totalSubs - activeSubsCount}</span>
                            }
                        ]}
                    />
                </div>
            </div>

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden">
                <CustomGrid
                    dataSource={subscriptionsData}
                    columns={columnConfig}
                    keyExpr="id"
                    height="65vh"
                    showSearch={true}
                    masterDetail={{
                        enabled: true,
                        template: (container, options) => {
                            const root = createRoot(container);
                            root.render(<GenericDetail data={options.data} {...subscriptionDetailConfig} />);
                        }
                    }}
                />
            </div>
        </div>
    );
}

export default Subscriptions;