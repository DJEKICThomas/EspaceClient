import React, { useState, useEffect, useMemo } from 'react'; // Ajout de useMemo
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

    const subStats = useMemo(() => {
        const today = new Date().setHours(0, 0, 0, 0);
        const in30Days = today + (30 * 24 * 60 * 60 * 1000);

        let active = 0, soon = 0, expired = 0;

        subscriptionsData.forEach(sub => {
            const end = Array.isArray(sub.endDate)
                ? new Date(sub.endDate[0], sub.endDate[1] - 1, sub.endDate[2]).getTime()
                : new Date(sub.endDate).getTime();

            if (!sub.active || end < today) {
                expired++;
            } else if (end <= in30Days) {
                soon++;
            } else {
                active++;
            }
        });
        return { active, soon, expired };
    }, [subscriptionsData]);

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
        title: t('subscription_details'),
        columns: []
    };

    const handleViewPdf = (docNumber) => {
        const testPdfUrl = "/pdf/DescriptionStage2025_EspaceClient.pdf";
        const width = window.screen.availWidth;
        const height = window.screen.availHeight;
        const windowFeatures = `width=${width},height=${height},top=0,left=0,popup=yes,resizable=yes,scrollbars=yes`;
        window.open(testPdfUrl, '_blank', windowFeatures);
    };

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        {
            dataField: 'action',
            width: 100,
            caption: t('col_inv_action'),
            alignment: 'center',
            cellRender: (cellData) => (
                <i className="fi fi-ts-file-pdf pdf-icon-click" onClick={() => handleViewPdf(cellData.data.num)} />
            )
        },
        { dataField: 'reference', caption: t('reference'), alignment: 'center', width: 350},
        {
            dataField: 'startDate',
            width: 150,
            caption: t('start_date'),
            dataType: 'date',
            sortOrder: 'desc',
            alignment: 'right',
            format: 'dd/MM/yyyy',
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
            width: 150,
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.endDate)) {
                    return new Date(rowData.endDate[0], rowData.endDate[1] - 1, rowData.endDate[2]);
                }
                return rowData.endDate;
            }
        },
        { dataField: 'description', caption: t('label'), alignment: 'left',},
        { dataField: 'recur', caption: t('frequency'), alignment: 'center', width: 200 },
        {
            dataField: 'active',
            caption: t('status'),
            width: 200,
            alignment: 'center',
            headerFilter: {
                dataSource: [
                    {
                        text: t('active'),
                        value: true
                    },
                    {
                        text: t('inactive'),
                        value: false
                    },
                    {
                        text: t('expiring_soon'),
                        value: false
                    },
                ]
            },
            cellRender: (cellData) => {
                const sub = cellData.data;
                const today = new Date().setHours(0, 0, 0, 0);
                const in30Days = today + (30 * 24 * 60 * 60 * 1000);
                const end = Array.isArray(sub.endDate)
                    ? new Date(sub.endDate[0], sub.endDate[1] - 1, sub.endDate[2]).getTime()
                    : new Date(sub.endDate).getTime();

                // Logique des badges
                if (!sub.active || end < today) {
                    return <span className="badge bg-danger">{t('inactive')}</span>;
                } else if (end <= in30Days) {
                    return <span className="badge" style={{backgroundColor: '#f39c12'}}>{t('expiring_soon')}</span>;
                } else {
                    return <span className="badge bg-success">{t('active')}</span>;
                }
            }
        }
    ];

    return (
        <div className="container-fluid dx-viewport"
             style={{
                 height: '100vh',
                 backgroundColor: '#f3f3f7',
                 display: 'flex',
                 flexDirection: 'column',
                 overflow: 'hidden'
             }}>
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
                        value={subscriptionsData.length.toString()}
                        details={[
                            {
                                label: t('active_now'),
                                value: <span style={{ color: '#198754', fontWeight: 'bold' }}>{subStats.active}</span>
                            },
                            {
                                label: t('expiring_soon'),
                                value: <span style={{ color: '#f39c12', fontWeight: 'bold' }}>{subStats.soon}</span>
                            },
                            {
                                label: t('expired'),
                                value: <span style={{ color: '#e74c3c', fontWeight: 'bold' }}>{subStats.expired}</span>
                            }
                        ]}
                    />
                </div>
            </div>

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden"
                 style={{
                     flexGrow: 1,
                     minHeight: 0,
                     display: 'flex',
                     flexDirection: 'column'
                 }}>
                <CustomGrid
                    dataSource={subscriptionsData}
                    columns={columnConfig}
                    keyExpr="id"
                    height="100%"
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