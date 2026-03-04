import React, { useState, useEffect } from 'react';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";

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
            // Appel à l'URL définie dans ton Controller : /api/subscriptions/user
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

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        { dataField: 'reference', caption: t('reference'), alignment: 'left', width: '200' },
        { dataField: 'description', caption: t('label'), alignment: 'left' },
        {
            dataField: 'dateDebut',
            caption: t('start_date'),
            dataType: 'date',
            alignment: 'center',
            format: 'dd/MM/yyyy',
            width: '180',
            // Gestion du format LocalDate renvoyé par le backend
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.dateDebut)) {
                    return new Date(rowData.dateDebut[0], rowData.dateDebut[1] - 1, rowData.dateDebut[2]);
                }
                return rowData.dateDebut;
            }
        },
        {
            dataField: 'dateFin',
            caption: t('end_date'),
            dataType: 'date',
            alignment: 'center',
            format: 'dd/MM/yyyy',
            width: '180',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.dateFin)) {
                    return new Date(rowData.dateFin[0], rowData.dateFin[1] - 1, rowData.dateFin[2]);
                }
                return rowData.dateFin;
            }
        },
        {
            dataField: 'active',
            caption: t('status'),
            alignment: 'center',
            width: '150',
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
                        value={totalSubs > 0 ? `${totalSubs} ` + t('subscriptions_count') : t('no_subscriptions')}
                        subValue={t('track_active_services')}
                    />
                </div>

                <div className="col-12 col-md-6 d-flex">
                    <SummaryCard
                        title={t('subscription_status')}
                        value={activeSubsCount}
                        subValue={t('active_contracts')}
                        details={[
                            {
                                label: t('active_now'),
                                value: activeSubsCount
                            },
                            {
                                label: t('expired'),
                                value: totalSubs - activeSubsCount
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
                    height="70vh"
                    showSearch={true}
                />
            </div>
        </div>
    );
}

export default Subscriptions;