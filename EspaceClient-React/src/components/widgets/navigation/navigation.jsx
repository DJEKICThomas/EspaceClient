import React from 'react';

function Navigation({ activeTab, onGoToHome, onGoToInvoices, onGoToOrders, onGoToHistory, onGoToOffers }) {
    const tabs = ['Accueil', 'Factures', 'Commandes', 'Offres', 'Historique'];

    const handleTabClick = (tab) => {
        if (tab === activeTab) return;

        if (tab === 'Accueil' && onGoToHome) onGoToHome();
        if (tab === 'Factures' && onGoToInvoices) onGoToInvoices();
        if (tab === 'Commandes' && onGoToOrders) onGoToOrders();
        if (tab === 'Offres' && onGoToOffers) onGoToOffers();
        if (tab === 'Historique' && onGoToHistory) onGoToHistory();
    };

    return (
        <div className="d-flex w-100 bg-white shadow-sm rounded" style={{ overflow: 'hidden', border: '1px solid #eee' }}>
            {tabs.map((tab, index) => (
                <div
                    key={tab}
                    onClick={() => handleTabClick(tab)}
                    style={{
                        flex: '1',
                        textAlign: 'center',
                        padding: '12px 5px',
                        cursor: 'pointer',
                        transition: 'all 0.3s ease',
                        borderBottom: activeTab === tab ? '4px solid #ff9800' : '4px solid transparent',
                        color: activeTab === tab ? '#ff9800' : '#6c757d',
                        fontWeight: activeTab === tab ? 'bold' : 'normal',
                        borderRight: index < tabs.length - 1 ? '1px solid #eee' : 'none'
                    }}
                >
                    <small style={{ display: 'block', fontSize: '0.75rem', whiteSpace: 'nowrap' }}>
                        {tab}
                    </small>
                </div>
            ))}
        </div>
    );
}

export default Navigation;