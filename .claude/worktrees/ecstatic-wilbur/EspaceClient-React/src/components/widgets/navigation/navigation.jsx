import React from 'react';
import { useTranslation } from "react-i18next";

function Navigation({ activeTab, onGoToHome, onGoToInvoices, onGoToSubscription, onGoToOrders, onGoToHistoric, onGoToOffers, onGoToAdmin, isMobile, closeMenu, onGoToDocument, isDarkMode }) {
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
            if (isMobile) closeMenu();
        }
    };

    // Couleurs conditionnelles selon le thème
    const navBg = isDarkMode ? '#000000' : 'transparent';
    const textColor = isDarkMode ? '#ffffff' : '#333';
    const selectedMobileBg = isDarkMode ? 'rgba(243,156,18,0.15)' : '#fff9f0';

    return (
        <div className={`dx-tabs dx-widget ${isMobile ? 'h-auto w-100' : 'h-100 w-100'}`}
             style={{ border: 'none', backgroundColor: navBg }}>
            <div className={`dx-tabs-wrapper d-flex ${isMobile ? 'flex-column' : 'h-100'}`} style={{ border: 'none' }}>
                {tabs.map((tab) => {
                    const isSelected = activeTab === tab.id;
                    return (
                        <div key={tab.id} onClick={() => handleTabClick(tab.id)}
                             className={`dx-item dx-tab dx-widget d-flex align-items-center ${isSelected ? 'dx-tab-selected dx-state-active' : ''} ${isMobile ? 'ps-4' : 'justify-content-center'}`}
                             style={{
                                 cursor: 'pointer',
                                 width: isMobile ? '100%' : '12.5%',
                                 minWidth: isMobile ? '100%' : '12.5%',
                                 height: isMobile ? '50px' : '100%',
                                 flex: 'none',
                                 borderLeft: (isMobile && isSelected) ? '5px solid #f39c12' : 'none',
                                 borderBottom: (!isMobile && isSelected) ? '6px solid #f39c12' : 'none',
                                 transition: 'background-color 0.2s',
                                 backgroundColor: isMobile && isSelected ? selectedMobileBg : 'transparent',
                                 boxSizing: 'border-box'
                             }}>
                            <div className="dx-item-content dx-tab-content">
                                <span className="dx-tab-text" style={{
                                    fontSize: '0.8rem',
                                    textTransform: 'uppercase',
                                    fontWeight: isSelected ? 'bold' : '500',
                                    color: isSelected ? '#f39c12' : textColor,
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
