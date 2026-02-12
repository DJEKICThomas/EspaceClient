import React, { useState, useEffect } from 'react';
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import CustomGrid from "../widgets/grid/customGrid";

function PermissionsUser({
                             onLogout, userToEdit, onBack, onGoToHome, onGoToInvoices,
                             onGoToOrders, onGoToOffers, onGoToHistoric
                         }) {
    const [permissionData, setPermissionData] = useState([]);

    useEffect(() => {
        const loadData = async () => {
            const token = localStorage.getItem('token');
            const targetEmail = userToEdit?.email;

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
        };

        loadData();
    }, [userToEdit]);

    const columnConfig = [
        {
            dataField: 'code',
            caption: 'Code',
            allowEditing: false
        },
        {
            dataField: 'comment',
            caption: 'Description',
            alignment: 'left'
        },
        {
            dataField: 'hasAccess',
            caption: 'Accès',
            dataType: 'boolean',
            alignment: 'center'
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
                {/* Header Profil */}
                <h2 className="mb-4" style={{ fontWeight: 'normal', color: '#000' }}>
                    <span style={{ borderBottom: '3px solid #d32f2f', paddingBottom: '5px' }}>
                        Compte : {userToEdit?.firstname} {userToEdit?.lastname} ({userToEdit?.profile})
                    </span>
                </h2>

                <div className="mt-5">
                    <h3 className="mb-4 text-start" style={{ fontWeight: 'normal', color: '#000' }}>
                        <span style={{ borderBottom: '3px solid #d32f2f', paddingBottom: '5px' }}>
                            Permissions & Accès
                        </span>
                    </h3>

                    <div className="shadow-sm rounded border overflow-hidden">
                        <CustomGrid
                            dataSource={permissionData}
                            columns={columnConfig}
                            keyExpr="code"
                        />
                    </div>
                </div>

                <div className="d-flex justify-content-start mt-4">
                    <button
                        className="btn btn-danger px-4 py-2 fw-bold"
                        style={{ borderRadius: '5px', backgroundColor: '#d32f2f', border: 'none' }}
                        onClick={onBack}
                    >
                        RETOUR
                    </button>
                </div>
            </div>
        </div>
    );
}

export default PermissionsUser;