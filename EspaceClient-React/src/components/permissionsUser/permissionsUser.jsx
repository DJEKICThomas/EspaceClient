import React, {useState, useEffect, useCallback} from 'react';
import Header from "../widgets/header/header";
import CustomGrid from "../widgets/grid/customGrid";
import { useTranslation } from "react-i18next";
import notify from "devextreme/ui/notify";
import Button from "../widgets/button/button";

function PermissionsUser({onLogout, onGoToCustomerSelection, onGoToCompanySelection, userToEdit, onBack, onGoToSubscription, onGoToHome, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToHistoric, onGoToDocument, onGoToSettings}) {
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
        { dataField: 'code', width: 500, caption: t('col_code'), allowEditing: false },
        { dataField: 'comment', caption: t('col_description'), alignment: 'left', allowEditing: false },
        { dataField: 'hasAccess', width: 250, caption: t('col_access'), dataType: 'boolean', alignment: 'center', allowEditing: true }
    ];

    return (
        <div className="container-fluid dx-viewport dx-theme-background-color"
             style={{
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
                 overflow: 'hidden'
             }}>

            {/* Header fixe */}
            <div style={{ flexShrink: 0 }}>
                <Header
                    onLogout={onLogout}
                    activeTab="Permissions User"
                    onGoToHome={onGoToHome}
                    onGoToInvoices={onGoToInvoices}
                    onGoToOrders={onGoToOrders}
                    onGoToOffers={onGoToOffers}
                    onGoToHistoric={onGoToHistoric}
                    onGoToAdmin={onBack}
                    onGoToDocument={onGoToDocument}
                    onGoToSubscription={onGoToSubscription}
                    onGoToSettings={onGoToSettings}
                    onGoToCustomerSelection={onGoToCustomerSelection}
                    onGoToCompanySelection={onGoToCompanySelection}
                />
            </div>

            {/* Grande carte qui s'étire */}
            <div className="dx-card dx-widget p-4 shadow-sm m-3"
                 style={{
                     flex: 1,
                     display: 'flex',
                     flexDirection: 'column',
                     minHeight: 0
                 }}>

                <div style={{ flexShrink: 0 }}>
                    <h2 className="mb-4 fw-light dx-theme-text-color">
                        <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '8px' }}>
                            {t('account')} : {currentUser?.firstname} {currentUser?.lastname} ({currentUser?.profile})
                        </span>
                    </h2>
                </div>

                {/* Zone du tableau qui prend le reste de la place dans la carte */}
                <div style={{ flex: 1, display: 'flex', flexDirection: 'column', minHeight: 0, marginTop: '20px' }}>
                    <h3 className="mb-3 text-start fw-light dx-theme-text-color" style={{ fontSize: '1.4rem', flexShrink: 0 }}>
                        <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '5px' }}>
                            {t('permissions_access')}
                        </span>
                    </h3>

                    <div className="dx-card dx-widget shadow-sm overflow-hidden" style={{ flex: 1 }}>
                        <CustomGrid
                            dataSource={permissionData}
                            columns={columnConfig}
                            keyExpr="code"
                            showSearch={true}
                            height="100%"
                            editing={{ mode: 'cell', allowUpdating: true }}
                            onRowUpdated={handleUpdatePermission}
                        />
                    </div>
                </div>

                {/* Bouton retour fixé en bas de la carte */}
                <div className="d-flex justify-content-start mt-4" style={{ flexShrink: 0 }}>
                    <Button
                        label={t('back_button')}
                        onClick={onBack}
                        type="button"
                        className="dx-button-normal"
                    />
                </div>
            </div>
        </div>
    );
}

export default PermissionsUser;