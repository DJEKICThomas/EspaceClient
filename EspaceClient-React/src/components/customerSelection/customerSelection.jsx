import React, { useState, useEffect } from 'react';
import Header from '../widgets/header/header';
import { useTranslation} from "react-i18next";
import { TextBox } from 'devextreme-react/text-box';
import { Button } from 'devextreme-react/button';
import { List } from 'devextreme-react/list';
import { useTheme } from '../../context/ThemeContext.jsx';
import {Hammer} from "react-bootstrap-icons";

function CustomerSelection({onLogout, onGoToHome, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToSubscription, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToSettings, onGoToCustomerSelection}){

    const { t } = useTranslation();
    const { isDarkMode } = useTheme();
    const [customers, setCustomers] = useState([]);
    const [searchValue, setSearchValue] = useState('');
    const profile = localStorage.getItem('profile');
    const isAdmin = profile === 'ADMINISTRATEUR';

    useEffect(() => {
        if(!isAdmin){
            loadManagedCustomers();
        }
    }, []);

    const loadManagedCustomers = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try{
            const response = await fetch(`${process.env.REACT_APP_API_URL}/wm-customers?userId=${userId}`, {
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

    const searchCustomers = async () => {
        const token = localStorage.getItem('token');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/customers?filter=${searchValue}`, {
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

    const handleCustomerSelect = (customer) => {
        localStorage.setItem('selectedNcs', customer.ncs);
        localStorage.setItem('selectedCustomerName', customer.customerName);
        onGoToHome();
    };

    const cardBg = isDarkMode ? '#1e1e1e' : '#ffffff';
    const textColor = isDarkMode ? '#ffffff' : '#333';

    return(
        <div>
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
            />
            <div className="container mt-4">
                <h4 style={{ color: textColor}}>{t('select_customer')}</h4>

                {isAdmin && (
                    <div className="d-flex gap-2 mb-3">
                        <TextBox
                            placeholder={t('search_customer')}
                            value={searchValue}
                            onValueChanged={(e) => setSearchValue(e.value)}
                            onEnterKey={searchCustomers}
                            width="100%"
                            stylingMode="outlined"
                        />
                        <Button
                            text={t('search_button')}
                            type="default"
                            onClick={searchCustomers}
                        />
                    </div>
                )}

                <List
                    dataSource={customers}
                    height="auto"
                    itemRender={(item) => (
                        <div style={{ padding: '10px', cursor: 'pointer' }}>
                            <strong>{item.customerName}</strong>
                            <span style={{ marginLeft: '10px', color: '#888' }}>({item.ncs})</span>
                        </div>
                    )}
                    onItemClick={(e) => handleCustomerSelect(e.itemData)}
                    noDataText={isAdmin ? t('search_customer') : t('error_no_customers')}
                    style={{ backgroundColor: cardBg}}
                />
            </div>
        </div>
    )
}
export default CustomerSelection;