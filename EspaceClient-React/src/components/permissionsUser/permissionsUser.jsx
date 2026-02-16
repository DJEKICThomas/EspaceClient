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
        <div className="permission-container container-fluid p-3" style={{ backgroundColor: '#f8f9fa', minHeight: '100vh' }}>
            <Header onLogout={onLogout} />
            <Navigation
                activeTab="Admin"
                onLogout={onLogout}
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onBack}
            />

            <div className="bg-white p-4 rounded shadow-sm mt-3 border">
                <h2 className="mb-4" style={{ fontWeight: 'normal', color: '#000' }}>
                    <span style={{ borderBottom: '3px solid #d32f2f', paddingBottom: '5px' }}>
                        {t('account')} : {currentUser?.firstname} {currentUser?.lastname} ({currentUser?.profile})
                    </span>
                </h2>

                <div className="mt-5">
                    <h3 className="mb-4 text-start" style={{ fontWeight: 'normal', color: '#000' }}>
                        <span style={{ borderBottom: '3px solid #d32f2f', paddingBottom: '5px' }}>
                            {t('permissions_access')}
                        </span>
                    </h3>

                    <div className="shadow-sm rounded border overflow-hidden">
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

                <div className="d-flex justify-content-start mt-4">
                    <button
                        className="btn btn-danger px-4 py-2 fw-bold"
                        style={{ borderRadius: '5px', backgroundColor: '#d32f2f', border: 'none' }}
                        onClick={onBack}
                    >
                        {t('back_button')}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default PermissionsUser;