import React, { useState, useEffect, useRef } from 'react';
import Header from '../widgets/header/header';
import { useTranslation } from "react-i18next";
import { List } from 'devextreme-react/list';
import { useTheme } from '../../context/ThemeContext.jsx';

function CustomerSelection({onLogout, onGoToHome, onGoToCompanySelection, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToSubscription, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToSettings, onGoToCustomerSelection}){

    const { t } = useTranslation();
    const { isDarkMode } = useTheme();
    const [customers, setCustomers] = useState([]);
    const [searchValue, setSearchValue] = useState('');
    const debounceRef = useRef(null);
    const profile = localStorage.getItem('profile');
    const isAdmin = profile === 'ADMINISTRATEUR';
    //const companies = JSON.parse(localStorage.getItem('companies') || '[]');
    //const showGreeting = companies.length <= 1;

    useEffect(() => {
        if (!isAdmin) {
            loadManagedCustomers();
        }
    }, [isAdmin]);

    const loadManagedCustomers = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        const company = localStorage.getItem('selectedCompany');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/wm-customers?userId=${userId}&company=${company}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (response.ok) {
                const data = await response.json();
                setCustomers(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    const handleSearchChange = (e) => {
        const value = e.target.value;
        setSearchValue(value);

        clearTimeout(debounceRef.current);
        if (value.length >= 2) {
            debounceRef.current = setTimeout(async () => {
                const token = localStorage.getItem('token');
                const company = localStorage.getItem('selectedCompany');
                try {
                    const response = await fetch(`${process.env.REACT_APP_API_URL}/customers?filter=${value}&company=${company}`, {
                        headers: { 'Authorization': `Bearer ${token}` }
                    });
                    if (response.ok) {
                        const data = await response.json();
                        setCustomers(data);
                    }
                } catch (error) {
                    console.error("Erreur réseau : ", error);
                }
            }, 300);
        } else {
            setCustomers([]);
        }
    };

    const handleCustomerSelect = (customer) => {
        localStorage.setItem('selectedNcs', customer.ncs);
        localStorage.setItem('selectedCustomerName', customer.customerName);
        onGoToHome();
    };

    const cardBg = isDarkMode ? '#1e1e1e' : '#ffffff';
    const textColor = isDarkMode ? '#ffffff' : '#333';

    return(
        <div className="container-fluid dx-viewport dx-theme-background-color"
             style={{
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
             }}>
            <div style={{ flexShrink: 0 }}>
                <Header
                    activeTab="Client"
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
                    onGoToCompanySelection={onGoToCompanySelection}
                />
            </div>
            <div className="container mt-4">
                {/*{showGreeting && (
                    <h3 style={{ color: textColor, textAlign: 'center', marginBottom: '1.5rem' }}>
                        {t('hello')} {localStorage.getItem('username')}
                    </h3>
                )}*/}
                <h4 style={{ color: textColor }}>{t('select_customer')}</h4>

                {isAdmin && (
                    <div className="mb-3">
                        <input
                            type="text"
                            className="form-control"
                            placeholder={t('search_customer')}
                            value={searchValue}
                            onChange={handleSearchChange}
                            onKeyDown={(e) => {
                                if (e.key === 'Enter' && customers.length === 1) {
                                    handleCustomerSelect(customers[0]);
                                }
                            }}
                            style={{
                                borderRadius: 8,
                                padding: '10px 16px',
                                fontSize: 14
                            }}
                        />
                    </div>
                )}
                {(!isAdmin || searchValue.length >= 2) && (
                    <List
                        dataSource={customers}
                        height="auto"
                        itemRender={(item) => (
                            <div style={{ padding: '10px', cursor: 'pointer' }}>
                                <strong>{item.customerName}</strong>
                                <span style={{ marginLeft: '10px', color: '#9e9e9e' }}>({item.ncs})</span>
                            </div>
                        )}
                        onItemClick={(e) => handleCustomerSelect(e.itemData)}
                        noDataText={t('error_no_customers')}
                        style={{ backgroundColor: cardBg }}
                    />
                )}
            </div>
        </div>
    );
}

export default CustomerSelection;