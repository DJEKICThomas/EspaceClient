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
            'Admin': onGoToAdmin
        };
        if (actions[tabId]) actions[tabId]();
    };

    return (
        <div className="dx-tabs dx-widget h-100" style={{ overflow: 'hidden', border: 'none', width: '100%' }}>
            <div className="dx-tabs-wrapper d-flex h-100" style={{ border: 'none' }}>
                {tabs.map((tab) => {
                    const isSelected = activeTab === tab.id;
                    return (
                        <div key={tab.id} onClick={() => handleTabClick(tab.id)}
                             className={`dx-item dx-tab dx-widget d-flex align-items-center justify-content-center ${isSelected ? 'dx-tab-selected dx-state-active' : ''}`}
                             style={{
                                 cursor: 'pointer',
                                 padding: '0 5px',
                                 width: '16.66%',
                                 minWidth: '16.66%',
                                 height: '100%',
                                 flex: 'none',
                                 borderBottom: isSelected ? '3px solid #f39c12' : 'none',
                                 transition: 'none',
                                 boxSizing: 'border-box',
                                 display: 'flex',
                                 alignItems: 'center',
                                 justifyContent: 'center'
                             }}>
                            <div className="dx-item-content dx-tab-content" style={{ textAlign: 'center' }}>
                                <span className="dx-tab-text" style={{
                                    fontSize: '0.8rem',
                                    textTransform: 'uppercase',
                                    fontWeight: isSelected ? 'bold' : '500',
                                    color: isSelected ? '#333' : '#333',
                                    display: 'block'
                                }}>
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