import React, { useState, useEffect } from 'react';
import Header from '../widgets/header/header';
import { useTranslation } from "react-i18next";
import { List } from 'devextreme-react/list';
import { useTheme } from '../../context/ThemeContext.jsx';

function CompanySelection({ onLogout, onGoToHome, onGoToCustomerSelection, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToSubscription, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToSettings }) {

    const { t } = useTranslation();
    const { isDarkMode } = useTheme();
    const [companies, setCompanies] = useState([]);

    useEffect(() => {
        const stored = localStorage.getItem('companies');
        if (stored) {
            setCompanies(JSON.parse(stored));
        }
    }, []);

    const handleCompanySelect = (company) => {
        localStorage.setItem('selectedCompany', company.usercmp);
        localStorage.setItem('selectedCompanyName', company.companyName);
        localStorage.setItem('eGlobalUserId', company.eGlobalUserId);
        onGoToCustomerSelection();
    };

    const cardBg = isDarkMode ? '#1e1e1e' : '#ffffff';
    const textColor = isDarkMode ? '#ffffff' : '#333';

    return (
        <div className="container-fluid dx-viewport dx-theme-background-color"
             style={{
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
             }}>
            <div style={{ flexShrink: 0 }}>
                <Header
                    activeTab="Company"
                    onLogout={onLogout}
                    onGoToHome={onGoToHome}
                    onGoToInvoices={onGoToInvoices}
                    onGoToOrders={onGoToOrders}
                    onGoToOffers={onGoToOffers}
                    onGoToSubscription={onGoToSubscription}
                    onGoToHistoric={onGoToHistoric}
                    onGoToAdmin={onGoToAdmin}
                    onGoToDocument={onGoToDocument}
                    onGoToSettings={onGoToSettings}
                    onGoToCustomerSelection={onGoToCustomerSelection}
                />
            </div>
            <div className="container mt-4">
                <h4 style={{ color: textColor }}>{t('select_company')}</h4>
                <List
                    dataSource={companies}
                    height="auto"
                    itemRender={(item) => (
                        <div style={{ padding: '10px', cursor: 'pointer' }}>
                            <strong>{item.companyName}</strong>
                            <span style={{ marginLeft: '10px', color: '#888' }}>({item.usercmp})</span>
                        </div>
                    )}
                    onItemClick={(e) => handleCompanySelect(e.itemData)}
                    noDataText={t('error_no_companies')}
                    style={{ backgroundColor: cardBg }}
                />
            </div>
        </div>
    );
}

export default CompanySelection;
