import React, { useState, useEffect } from 'react';
import 'devextreme/dist/css/dx.material.orange.light.css';
import CustomGrid from "../widgets/grid/customGrid";
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next"; // Import

function Admin({ onLogout, onGoToProfile, onGoToPermissions, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToHome, onGoToHistoric }) {
    const { t } = useTranslation();
    const [adminData, setAdminData] = useState([]);


    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/users?userMail=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setAdminData(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        {
            caption: t('actions'),
            width: 150,
            alignment: 'center',
            cssClass: 'action-column-separator',
            allowReordering: false,
            allowFiltering: false,
            allowSorting: false,
            allowGrouping: false,
            cellRender: (cellData) => (
                <div style={{ display: 'flex', gap: '10px', justifyContent: 'center' }}>
                    <i
                        className="dx-icon-edit"
                        title={t('edit')}
                        style={{ cursor: 'pointer', color: '#d32f2f' }}
                        onClick={() => onGoToProfile(cellData.data)}
                    />
                    <i className="dx-icon-key"
                       title={t('permissions')}
                       style={{ cursor: 'pointer', color: '#d32f2f' }}
                       onClick={() => onGoToPermissions(cellData.data)}
                    />
                    <i className="dx-icon-trash" style={{ cursor: 'pointer', color: '#d32f2f' }} />
                </div>
            )
        },
        {
            dataField: 'active',
            caption: t('status'),
            alignment: 'center',
            cellRender: (cellData) => {
                const isActive = cellData.value === 1;
                return (
                    <i
                        className="dx-icon-user"
                        title={isActive ? t('active') : t('inactive')}
                        style={{
                            color: isActive ? '#2e7d32' : '#d32f2f',
                            fontSize: '18px'
                        }}
                    />
                );
            }
        },
        { dataField: 'email', caption: t('email'), alignment:'left' },
        { dataField: 'lastname', caption: t('lastname'), alignment:'left' },
        { dataField: 'firstname', caption: t('firstname'), alignment:'left' },
        { dataField: 'function', caption: t('function'), alignment:'left' },
        { dataField: 'profile', caption: t('profile'), alignment:'left' }
    ];

    return (
        <div className="admin-container container-fluid p-3" style={{
            backgroundColor: '#f8f9fa',
            minHeight: '100vh',
            display: 'block'
        }}>
            <Header onLogout={onLogout} />
            <Navigation
                activeTab="Admin"
                onLogout={onLogout}
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToOrders={onGoToOrders}
                onGoToAdmin={() => {}}
            />

            <div className="shadow-sm bg-white p-2 p-md-3 rounded overflow-auto mb-4 mt-4">
                <CustomGrid
                    dataSource={adminData}
                    columns={columnConfig}
                    keyExpr="id"
                    columnAutoWidth={true}
                    wordWrapEnabled={true}
                />
            </div>
        </div>
    );
}

export default Admin;