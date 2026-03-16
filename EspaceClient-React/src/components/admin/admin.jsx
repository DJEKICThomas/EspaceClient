import React, {useState, useEffect, useCallback} from 'react';
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";
import { confirm } from 'devextreme/ui/dialog';
import notify from "devextreme/ui/notify";

function Admin({ onLogout, onGoToProfile, onGoToSubscription, onGoToPermissions, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToHome, onGoToHistoric, onGoToDocument }) {
    const { t } = useTranslation();
    const [adminData, setAdminData] = useState([]);

    const loadData = useCallback(async () => {
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
                const activeUsers = data.filter(user => user.active !== -10);
                setAdminData(activeUsers);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    }, []);

    useEffect(() => {
        loadData();
    }, [loadData]);

    const handleDeleteUser = async (user) => {
        const result = await confirm({
            title: t('delete'),
            messageHtml: `<i>${t('confirm_delete')}</i>`,
            buttons: [
                {
                    text: t('yes'),
                    onClick: () => true,
                },
                {
                    text: t('no'),
                    onClick: () => false
                }
            ]
        });

        if (result) {
            const token = localStorage.getItem('token');
            try {
                const response = await fetch(`${process.env.REACT_APP_API_URL}/users/${user.id}`, {
                    method: 'DELETE',
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                if (response.ok) {
                    notify(t('user_deleted'), "success", 2000);
                    loadData();
                }
            } catch (e) {
                notify("Erreur", "error", 2000);
            }
        }
    };

    const columnConfig = [
        {
            caption: t('actions'),
            width: 150,
            alignment: 'center',
            allowReordering: false,
            allowFiltering: false,
            allowSorting: false,
            allowGrouping: false,
            cellRender: (cellData) => (
                <div style={{ display: 'flex', gap: '15px', justifyContent: 'center' }}>
                    <i
                        className="dx-icon-edit dx-theme-accent-color"
                        title={t('edit')}
                        style={{ cursor: 'pointer', fontSize: '18px' }}
                        onClick={() => onGoToProfile(cellData.data)}
                    />
                    <i
                        className="dx-icon-key dx-theme-accent-color"
                        title={t('permissions')}
                        style={{ cursor: 'pointer', fontSize: '18px' }}
                        onClick={() => onGoToPermissions(cellData.data)}
                    />
                    <i className="dx-icon-trash text-muted"
                       title={t('delete')}
                       style={{ cursor: 'pointer', fontSize: '18px' }}
                       onClick={() => handleDeleteUser(cellData.data)}
                    />
                </div>
            )
        },
        {
            dataField: 'active',
            width: 130,
            caption: t('status'),
            alignment: 'center',
            cellRender: (cellData) => {
                const isActive = cellData.value === 1;
                return (
                    <i
                        className="dx-icon-user"
                        title={isActive ? t('active') : t('inactive')}
                        style={{
                            color: isActive ? '#2e7d32' : '#bd2130',
                            fontSize: '20px'
                        }}
                    />
                );
            }
        },
        { dataField: 'email', caption: t('email'), alignment:'left', },
        { dataField: 'lastname', caption: t('lastname'), alignment:'left',width: 200 },
        { dataField: 'firstname', caption: t('firstname'), alignment:'left', width: 200},
        { dataField: 'function', caption: t('function'), alignment:'left',width: 300 },
        { dataField: 'profile', caption: t('profile'), alignment:'left', width: 300 }
    ];

    return (
        <div className="container-fluid dx-viewport"
             style={{
                 backgroundColor: '#f3f3f7',
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
                 overflow: 'hidden'
             }}>
            <Header
                onLogout={onLogout}
                activeTab="Admin"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToOrders={onGoToOrders}
                onGoToAdmin={() => {}}
                onGoToDocument={onGoToDocument}
                onGoToSubscription={onGoToSubscription}
            />

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden mt-4"
                 style={{
                     flexGrow: 1,
                     minHeight: 0,
                     display: 'flex',
                     flexDirection: 'column'
                 }}>
                <CustomGrid
                    dataSource={adminData}
                    columns={columnConfig}
                    keyExpr="id"
                    showSearch={true}
                    height="100%"
                />
            </div>
        </div>
    );
}

export default Admin;