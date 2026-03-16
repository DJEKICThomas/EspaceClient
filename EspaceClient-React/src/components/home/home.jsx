import React, { useEffect, useState, useMemo } from 'react';
import SummaryCard from '../widgets/summaryCard/summaryCard';
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";
import DashboardCharts from '../widgets/dashboardCharts/DashboardCharts';

function Home({ onLogout, onGoToInvoices, onGoToSubscription, onGoToOrders, onGoToHome, onGoToOffers, onGoToAdmin, onGoToHistoric, onGoToDocument }) {
    const { t } = useTranslation();

    // États pour stocker les données réelles
    const [invoices, setInvoices] = useState([]);
    const [subscriptions, setSubscriptions] = useState([]);
    const [offers, setOffers] = useState([]);
    const [orders, setOrders] = useState([]); // Si tu as déjà l'endpoint orders

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };

        try {
            // 1. Récupération des Factures
            const invRes = await fetch(`${process.env.REACT_APP_API_URL}/invoices/user?userId=${userId}`, { headers });
            if (invRes.ok) setInvoices(await invRes.ok ? await invRes.json() : []);

            // 2. Récupération des Abonnements
            const subRes = await fetch(`${process.env.REACT_APP_API_URL}/subscriptions/user?userId=${userId}`, { headers });
            if (subRes.ok) setSubscriptions(await subRes.json());

            // 3. Récupération des Offres
            const offRes = await fetch(`${process.env.REACT_APP_API_URL}/offers/user?userId=${userId}`, { headers });
            if (offRes.ok) setOffers(await offRes.json());

            // 4. Récupération des Commandes (Orders)
            const ordRes = await fetch(`${process.env.REACT_APP_API_URL}/orders/user?userId=${userId}`, { headers });
            if (ordRes.ok) setOrders(await ordRes.json());

        } catch (error) {
            console.error("Erreur lors du chargement du Dashboard :", error);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const subStats = useMemo(() => {
        const today = new Date().setHours(0, 0, 0, 0);
        const in30Days = today + (30 * 24 * 60 * 60 * 1000);

        let active = 0, soon = 0, expired = 0;

        subscriptions.forEach(sub => {
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
    }, [subscriptions]);


    // Factures
    const unpaidInvoices = invoices.filter(inv => !inv.paid).length;

    // Abonnements
    const totalSubs = subscriptions.length;

    // Offres
    const totalOffers = offers.length;

    return (
        <div className="container-fluid dx-viewport" style={{
            backgroundColor: '#f3f3f7',
            height: '100vh',
            display: 'flex',
            flexDirection: 'column',
            overflow: 'hidden'
        }}>
            <Header
                onLogout={onLogout} activeTab="Accueil" onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices} onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers} onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onGoToAdmin} onGoToDocument={onGoToDocument}
                onGoToSubscription={onGoToSubscription}
            />

            {/* Summary Cards - fixed height */}
            <div className="row g-2 px-3 pt-2" style={{ flexShrink: 0 }}>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        title={t('invoices')}
                        onClick={onGoToInvoices}
                        value={unpaidInvoices.toString()}
                        subValue={t('invoices_to_settle')}
                        details={[
                            { label: t('total'), value: invoices.length }
                        ]}
                        status={{
                            label: unpaidInvoices > 0 ? t('to_settle') : t('up_to_date'),
                            color: unpaidInvoices > 0 ? '#e74c3c' : '#2ecc71',
                            bgColor: unpaidInvoices > 0 ? '#fdedec' : '#eafaf1'
                        }}
                    />
                </div>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        title={t('orders')}
                        onClick={onGoToOrders}
                        value={orders.length.toString()}
                        subValue={t('current_orders')}
                        details={[
                            { label: t('total'), value: orders.length }
                        ]}
                    />
                </div>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        title={t('offers')}
                        onClick={onGoToOffers}
                        value={totalOffers.toString()}
                        subValue={t('proposals_received')}
                        details={[
                            { label: t('total'), value: totalOffers }
                        ]}
                    />
                </div>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        title={t('my_subscriptions').toUpperCase()}
                        value={totalSubs.toString()}
                        onClick={onGoToSubscription}
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

            {/* Charts - fill remaining space */}
            <div className="px-3 pb-2 pt-2" style={{ flex: 1, minHeight: 0 }}>
                <DashboardCharts invoices={invoices} subscriptions={subscriptions} offers={offers} />
            </div>
        </div>
    );
}

export default Home;