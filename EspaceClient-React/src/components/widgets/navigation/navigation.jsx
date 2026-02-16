import React from 'react';
import { useTranslation } from "react-i18next";

function Navigation({ activeTab, onGoToHome, onGoToInvoices, onGoToOrders, onGoToHistoric, onGoToOffers, onGoToAdmin }) {
    const { t } = useTranslation();
    const tabs = [
        { id: 'Accueil', key: 'home' },
        { id: 'Factures', key: 'invoices' },
        { id: 'Commandes', key: 'orders' },
        { id: 'Offres', key: 'offers' },
        { id: 'Historique', key: 'historic' },
        { id: 'Admin', key: 'admin' }
    ];

    const handleTabClick = (tabId) => {
        if (tabId === activeTab) return;

        if (tabId === 'Accueil' && onGoToHome) onGoToHome();
        if (tabId === 'Factures' && onGoToInvoices) onGoToInvoices();
        if (tabId === 'Commandes' && onGoToOrders) onGoToOrders();
        if (tabId === 'Offres' && onGoToOffers) onGoToOffers();
        if (tabId === 'Historique' && onGoToHistoric) onGoToHistoric();
        if (tabId === 'Admin' && onGoToAdmin) onGoToAdmin();
    };

    return (
        <div className="d-flex w-100 bg-white shadow-sm rounded" style={{ overflow: 'hidden', border: '1px solid #eee' }}>
            {tabs.map((tab, index) => (
                <div
                    key={tab.id}
                    onClick={() => handleTabClick(tab.id)}
                    style={{
                        flex: '1',
                        textAlign: 'center',
                        padding: '12px 5px',
                        cursor: 'pointer',
                        transition: 'all 0.3s ease',
                        borderBottom: activeTab === tab.id ? '4px solid #ff9800' : '4px solid transparent',
                        color: activeTab === tab.id ? '#ff9800' : '#6c757d',
                        fontWeight: activeTab === tab.id ? 'bold' : 'normal',
                        borderRight: index < tabs.length - 1 ? '1px solid #eee' : 'none'
                    }}
                >
                    <small style={{ display: 'block', fontSize: '0.75rem', whiteSpace: 'nowrap' }}>
                        {t(tab.key)}
                    </small>
                </div>
            ))}
        </div>
    );
}

export default Navigation;