import React from 'react';
import { useTranslation } from "react-i18next";

function Navigation({ activeTab, onGoToHome, onGoToInvoices, onGoToSubscription, onGoToOrders, onGoToHistoric, onGoToOffers, onGoToAdmin, isMobile, closeMenu, onGoToDocument }) {
    const { t } = useTranslation();

    const tabs = [
        { id: 'Accueil', key: 'home' },
        { id: 'Factures', key: 'invoices' },
        { id: 'Commandes', key: 'orders' },
        { id: 'Offres', key: 'offers' },
        { id: 'Abonnements', key: 'subscriptions'},
        { id : 'Document', key: 'documents'},
        { id: 'Historique', key: 'historic' },
        { id: 'Admin', key: 'admin' },
    ];

    const handleTabClick = (tabId) => {
        if (tabId === activeTab) {
            if (isMobile) closeMenu();
            return;
        }

        const actions = {
            'Accueil': onGoToHome,
            'Factures': onGoToInvoices,
            'Commandes': onGoToOrders,
            'Offres': onGoToOffers,
            'Historique': onGoToHistoric,
            'Admin': onGoToAdmin,
            'Document': onGoToDocument,
            'Abonnements': onGoToSubscription
        };

        if (actions[tabId]) {
            actions[tabId]();
            // Ferme le menu burger après le clic sur mobile
            if (isMobile) closeMenu();
        }
    };

    return (
        <div className={`dx-tabs dx-widget ${isMobile ? 'h-auto w-100' : 'h-100 w-100'}`} style={{ border: 'none' }}>
            <div className={`dx-tabs-wrapper d-flex ${isMobile ? 'flex-column' : 'h-100'}`} style={{ border: 'none' }}>
                {tabs.map((tab) => {
                    const isSelected = activeTab === tab.id;
                    return (
                        <div key={tab.id} onClick={() => handleTabClick(tab.id)}
                             className={`dx-item dx-tab dx-widget d-flex align-items-center ${isSelected ? 'dx-tab-selected dx-state-active' : ''} ${isMobile ? 'ps-4' : 'justify-content-center'}`}
                             style={{
                                 cursor: 'pointer',
                                 // Horizontal sur PC (16.66%), Vertical sur Mobile (100%)
                                 width: isMobile ? '100%' : '12.5%',
                                 minWidth: isMobile ? '100%' : '12.5%',
                                 height: isMobile ? '50px' : '100%',
                                 flex: 'none',
                                 // Bordure à gauche en vertical, bordure en bas en horizontal
                                 borderLeft: (isMobile && isSelected) ? '5px solid #f39c12' : 'none',
                                 borderBottom: (!isMobile && isSelected) ? '6px solid #f39c12' : 'none',
                                 transition: 'background-color 0.2s',
                                 backgroundColor: isMobile && isSelected ? '#fff9f0' : 'transparent',
                                 boxSizing: 'border-box'
                             }}>
                            <div className="dx-item-content dx-tab-content">
                                <span className="dx-tab-text" style={{
                                    fontSize: '0.8rem',
                                    textTransform: 'uppercase',
                                    fontWeight: isSelected ? 'bold' : '500',
                                    color: isSelected ? '#f39c12' : '#333',
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