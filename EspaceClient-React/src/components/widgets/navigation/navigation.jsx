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
        const actions = {
            'Accueil': onGoToHome,
            'Factures': onGoToInvoices,
            'Commandes': onGoToOrders,
            'Offres': onGoToOffers,
            'Historique': onGoToHistoric,
            'Admin': onGoToAdmin };
        if (actions[tabId]) actions[tabId]();
    };

    return (
        <div className="dx-tabs dx-widget shadow-sm rounded mb-3" style={{ overflow: 'hidden' }}>
            <div className="dx-tabs-wrapper d-flex w-100 bg-white" style={{ overflowX: 'auto', whiteSpace: 'nowrap' }}>
                {tabs.map((tab) => {
                    const isSelected = activeTab === tab.id;
                    return (
                        <div key={tab.id} onClick={() => handleTabClick(tab.id)}
                             className={`dx-item dx-tab dx-widget flex-grow-1 d-flex align-items-center justify-content-center ${isSelected ? 'dx-tab-selected dx-state-active' : ''}`}
                             style={{ cursor: 'pointer', padding: '15px 10px', minWidth: '100px', borderBottomWidth: '4px' }}>
                            <div className="dx-item-content dx-tab-content">
                                <span className="dx-tab-text" style={{ fontSize: '0.85rem', textTransform: 'uppercase', fontWeight: isSelected ? 'bold' : '500' }}>
                                    {t(tab.key)}
                                </span>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}
export default Navigation;