import React, {useState, useEffect, useCallback} from 'react';
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import CustomGrid from "../widgets/grid/customGrid";
import { useTranslation } from "react-i18next";
import notify from "devextreme/ui/notify";

function PermissionsUser({onLogout, userToEdit, onBack, onGoToHome, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToHistoric, onGoToAdmin}) {
    const { t } = useTranslation();
    const [permissionData, setPermissionData] = useState([]);
    const [currentUser, setCurrentUser] = useState(userToEdit);

    const loadData = useCallback(async (email) => {
        const token = localStorage.getItem('token');
        const targetEmail = email || currentUser?.email;

        if (!targetEmail) return;

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/permission/list?mail=${targetEmail}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setPermissionData(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    }, [currentUser?.email]);

    useEffect(() => {
        let userFound = userToEdit;

        if (!userFound) {
            const savedUser = localStorage.getItem('userToEdit');
            if (savedUser) {
                userFound = JSON.parse(savedUser);
                setCurrentUser(userFound);
            }
        } else {
            localStorage.setItem('userToEdit', JSON.stringify(userToEdit));
            setCurrentUser(userToEdit);
        }

        if (userFound) {
            loadData(userFound.email);
        }
    }, [userToEdit, loadData]);

    const handleUpdatePermission = async (event) => {
        const token = localStorage.getItem('token');
        const targetEmail = currentUser?.email;

        const updatedRow = event.data;

        const permissionDTO = {
            role: updatedRow.role || updatedRow.code,
            hasAccess: updatedRow.hasAccess,
            comment: updatedRow.comment
        };

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/permission?mail=${targetEmail}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(permissionDTO)
            });

            if (response.ok) {
                if (updatedRow.hasAccess) {
                    notify(t('permission_added_success'), "success", 2000);
                } else {
                    notify(t('permission_removed_success'), "error", 2000);
                }
            } else {
                notify(t('permission_updated_error'), "error", 3000);
                loadData(targetEmail);
            }
        } catch (error) {
            console.error("Erreur lors de l'update des accès", error);
            notify("Erreur réseau", "error", 3000);
            loadData(targetEmail);
        }
    };

    const columnConfig = [
        {
            dataField: 'code',
            caption: t('col_code'),
            allowEditing: false,
            width: 150
        },
        {
            dataField: 'comment',
            caption: t('col_description'),
            alignment: 'left',
            allowEditing: false
        },
        {
            dataField: 'hasAccess',
            caption: t('col_access'),
            dataType: 'boolean',
            alignment: 'center',
            allowEditing: true
        }
    ];

    return (
        <div className="container-fluid p-3 dx-viewport" style={{ backgroundColor: '#f3f3f7', minHeight: '100vh' }}>
            <Header onLogout={onLogout} />
            <Navigation
                activeTab="Admin"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onBack}
            />

            <div className="dx-card dx-widget p-4 shadow-sm mt-3">
                <h2 className="mb-4 fw-light dx-theme-text-color">
                    <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '8px' }}>
                        {t('account')} : {currentUser?.firstname} {currentUser?.lastname} ({currentUser?.profile})
                    </span>
                </h2>

                <div className="mt-5">
                    <h3 className="mb-4 text-start fw-light dx-theme-text-color" style={{ fontSize: '1.4rem' }}>
                        <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '5px' }}>
                            {t('permissions_access')}
                        </span>
                    </h3>

                    <div className="dx-card dx-widget shadow-sm overflow-hidden">
                        <CustomGrid
                            dataSource={permissionData}
                            columns={columnConfig}
                            keyExpr="code"
                            editing={{
                                mode: 'cell',
                                allowUpdating: true
                            }}
                            onRowUpdated={handleUpdatePermission}
                        />
                    </div>
                </div>

                <div className="d-flex justify-content-start mt-5">
                    <div
                        className="dx-button dx-button-mode-contained dx-button-default dx-widget p-2 px-4"
                        onClick={onBack}
                        style={{ cursor: 'pointer', borderRadius: '4px' }}
                    >
                        <span className="dx-button-text fw-bold">{t('back_button')}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PermissionsUser;