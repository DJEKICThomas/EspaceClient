import React, { useEffect, useState, useMemo, useCallback } from 'react';
import SummaryCard from '../widgets/summaryCard/summaryCard';
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";
import DashboardCharts from '../widgets/dashboardCharts/DashboardCharts';
import DateBox from 'devextreme-react/date-box';
import AlertsPanel from '../widgets/alertsPanel/AlertsPanel.jsx';
import GlobalSearch from '../widgets/globalSearch/globalSearch';

function Home({ onLogout, onGoToCustomerSelection, onGoToCompanySelection, onGoToInvoices, onGoToSubscription, onGoToOrders, onGoToHome, onGoToOffers, onGoToAdmin, onGoToHistoric, onGoToDocument, onGoToSettings, onSearchNavigate }) {
    const { t,i18n } = useTranslation();
    const currentLocale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    // États pour stocker les données réelles
    const [invoices, setInvoices] = useState([]);
    const [subscriptions, setSubscriptions] = useState([]);
    const [offers, setOffers] = useState([]);
    const [orders, setOrders] = useState([]);
    const [monthlyTotals, setMonthlyTotals] = useState([]);

    // Plage de dates par défaut : 1er janvier de l'année en cours → aujourd'hui
    const [dateFrom, setDateFrom] = useState(() => new Date(new Date().getFullYear(), 0, 1));
    const [dateTo, setDateTo] = useState(() => new Date());

    const loadData = async () => {
        const ncs = localStorage.getItem('selectedNcs');
        const company = localStorage.getItem('selectedCompany');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };

        try {
            // 1. Récupération des Factures
            const invRes = await fetch(`${process.env.REACT_APP_API_URL}/invoices/user?userId=${userId}&ncs=${ncs}&company=${company}`, { headers });
            if (invRes.ok) setInvoices(await invRes.ok ? await invRes.json() : []);

            // 2. Récupération des Abonnements
            const subRes = await fetch(`${process.env.REACT_APP_API_URL}/subscriptions/user?userId=${userId}&ncs=${ncs}&company=${company}`, { headers });
            if (subRes.ok) setSubscriptions(await subRes.json());

            // 3. Récupération des Offres
            const offRes = await fetch(`${process.env.REACT_APP_API_URL}/offers/user?userId=${userId}&ncs=${ncs}&company=${company}`, { headers });
            if (offRes.ok) setOffers(await offRes.json());

            // 4. Récupération des Commandes (Orders)
            const ordRes = await fetch(`${process.env.REACT_APP_API_URL}/orders/user?userId=${userId}&ncs=${ncs}&company=${company}`, { headers });
            if (ordRes.ok) setOrders(await ordRes.json());

        } catch (error) {
            console.error("Erreur lors du chargement du Dashboard :", error);
        }
    };

    // Convertit une Date JS en entier CCYYMMDD pour l'API
    const toIntDate = (date) => {
        const y = date.getFullYear();
        const m = String(date.getMonth() + 1).padStart(2, '0');
        const d = String(date.getDate()).padStart(2, '0');
        return parseInt(`${y}${m}${d}`, 10);
    };

    const loadAnalytics = useCallback(async (from, to) => {
        const ncs = localStorage.getItem('selectedNcs');
        const company = localStorage.getItem('selectedCompany');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const res = await fetch(
                `${process.env.REACT_APP_API_URL}/analytics/monthly-totals?userId=${userId}&from=${toIntDate(from)}&to=${toIntDate(to)}&ncs=${ncs}&company=${company}`,
                { headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` } }
            );
            if (res.ok) setMonthlyTotals(await res.json());
        } catch (error) {
            console.error("Erreur lors du chargement analytics :", error);
        }
    }, []);

    useEffect(() => {
        loadData();
    }, []);

    // Charger les analytics quand les dates changent
    useEffect(() => {
        if (dateFrom && dateTo) {
            loadAnalytics(dateFrom, dateTo);
        }
    }, [dateFrom, dateTo, loadAnalytics]);

    const subStats = useMemo(() => {
        const today = new Date().setHours(0, 0, 0, 0);
        const in30Days = today + (30 * 24 * 60 * 60 * 1000);

        let active = 0, soon = 0;

        subscriptions.forEach(sub => {
            // Pas de date de fin → considéré comme actif (pas comptabilisé en "expire bientôt")
            if (!sub.endDate) {
                active++;
                return;
            }
            const end = Array.isArray(sub.endDate)
                ? new Date(sub.endDate[0], sub.endDate[1] - 1, sub.endDate[2]).getTime()
                : new Date(sub.endDate).getTime();

            if (end <= in30Days) {
                soon++;
            } else {
                active++;
            }
        });

        return { active, soon };
    }, [subscriptions]);


    // Factures
    const unpaidInvoices = invoices.filter(inv => !inv.paid).length;

    const totalUnpaid = invoices.filter(inv => !inv.paid).reduce((sum, inv) => sum + (inv.valCurD || 0), 0);

    // Abonnements
    const totalSubs = subscriptions.length;

    // Offres
    const totalOffers = offers.length;

    return (
        <div className="container-fluid dx-viewport dx-theme-background-color" style={{
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
                onGoToSettings={onGoToSettings}
                onGoToCustomerSelection={onGoToCustomerSelection}
                onGoToCompanySelection={onGoToCompanySelection}
            />

            <div className="px-3 pt-3" style={{ flexShrink: 0 }}>
                <GlobalSearch
                    invoices={invoices}
                    orders={orders}
                    offers={offers}
                    subscriptions={subscriptions}
                    onNavigate={onSearchNavigate}
                />
            </div>

            {/* Summary Cards - fixed height */}
            <div className="row g-2 px-3 pt-2" style={{ flexShrink: 0 }}>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        compact
                        icon="fi fi-rs-receipt"
                        title={t('invoices')}
                        onClick={onGoToInvoices}
                        value={totalUnpaid.toLocaleString(currentLocale, { style: 'currency', currency: 'EUR' })}
                        status={{
                            label: unpaidInvoices > 0 ? t('to_settle') : t('up_to_date'),
                            color: unpaidInvoices > 0 ? '#e74c3c' : '#2ecc71',
                            bgColor: unpaidInvoices > 0 ? '#fdedec' : '#eafaf1'
                        }}
                    />
                </div>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        compact
                        icon="fi fi-rs-box-open"
                        title={t('orders')}
                        onClick={onGoToOrders}
                        value={orders.length.toString()}
                    />
                </div>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        compact
                        icon="fi fi-rs-document-signed"
                        title={t('offers')}
                        onClick={onGoToOffers}
                        value={totalOffers.toString()}
                    />
                </div>
                <div className="col-12 col-md-6 col-lg-3 d-flex">
                    <SummaryCard
                        compact
                        icon="fi fi-rs-refresh"
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
                                value: <span style={{ color: '#ff9800', fontWeight: 'bold' }}>{subStats.soon}</span>
                            },
                        ]}
                    />
                </div>
            </div>

            {/* Alerts Panel */}
            <div className="px-3 pt-2" style={{ flexShrink: 0 }}>
                <AlertsPanel
                    invoices={invoices}
                    subscriptions={subscriptions}
                    onGoToInvoices={(field, value) => {
                        if (field && value) {
                            onSearchNavigate('invoices', field, value);
                        } else {
                            onGoToInvoices();
                        }
                    }}
                    onGoToSubscription={(field, value) => {
                        if (field && value) {
                            onSearchNavigate('subscriptions', field, value);
                        } else {
                            onGoToSubscription();
                        }
                    }}
                />
            </div>

            {/* Date Range Selector */}
            <div className="row g-2 px-3 pt-2" style={{ flexShrink: 0 }}>
                <div className="col-auto d-flex align-items-center gap-2">
                    <label className="text-muted small fw-bold mb-0">{t('date_from')}</label>
                    <DateBox
                        value={dateFrom}
                        type="date"
                        displayFormat="dd/MM/yyyy"
                        width={160}
                        max={dateTo}
                        onValueChanged={(e) => setDateFrom(e.value)}
                    />
                    <label className="text-muted small fw-bold mb-0">{t('date_to')}</label>
                    <DateBox
                        value={dateTo}
                        type="date"
                        displayFormat="dd/MM/yyyy"
                        width={160}
                        min={dateFrom}
                        max={new Date()}
                        onValueChanged={(e) => setDateTo(e.value)}
                    />
                </div>
            </div>

            {/* Charts - fill remaining space */}
            <div className="px-3 pb-2 pt-2" style={{ flex: 1, minHeight: 0 }}>
                <DashboardCharts invoices={invoices} subscriptions={subscriptions} offers={offers} monthlyTotals={monthlyTotals} />
            </div>
        </div>
    );
}

export default Home;