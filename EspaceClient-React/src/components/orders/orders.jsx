import React, { useState, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import 'devextreme/dist/css/dx.material.orange.light.css';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";

function Orders({ onLogout, onGoToInvoices, onGoToOffers, onGoToHome, onGoToHistory }) {
    const [ordersData, setOrdersData] = useState([]);

    const totalOrders = ordersData.length;
    const latestOrder = ordersData.length > 0 ? ordersData[0] : null;

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`http://localhost:8080/EspaceClient-api/orders/user?userId=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setOrdersData(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    const orderDetailConfig = {
        endpoint: "orders/details",
        idField: "document",
        title: "Détails de la commande",
        columns: [
            { dataField: 'article', caption: 'Article' },
            { dataField: 'articleLab', caption: 'Désignation' },
            { dataField: 'priceUnit', caption: 'Prix unitaire' },
            { dataField: 'quantity', caption: 'Quantité' }
        ]
    };

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        { dataField: 'document', caption: 'N° Commande', alignment: 'center' },
        {
            dataField: 'docCreation',
            caption: 'Date',
            dataType: 'date',
            alignment: 'left',
            format: 'dd/MM/yyyy',
            sortOrder: 'desc',
            // Transformation du tableau [Y, M, D] en Date JS pour l'affichage
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.docCreation)) {
                    return new Date(rowData.docCreation[0], rowData.docCreation[1] - 1, rowData.docCreation[2]);
                }
                return rowData.docCreation;
            }
        },
        { dataField: 'docDes', caption: 'Libellé', alignment:'left', },
        { dataField: 'reference', caption: 'Référence', alignment:'left' },
        { dataField: 'fournisseur', caption: 'Fournisseur',alignment:'left' },
        {
            dataField: 'montantTotal',
            caption: 'Montant',
            alignment: 'center',
            cellRender: (cellData) => cellData.value ? `${cellData.value.toLocaleString('fr-BE', { minimumFractionDigits: 2 })} €` : "0,00 €"
        }
    ];

    return (
        <div className="orders-container container-fluid p-3" style={{
            backgroundColor: '#f8f9fa',
            minHeight: '100vh',
            display: 'block'
        }}>
            <Header onLogout={onLogout} />
            <Navigation
                activeTab="Commandes"
                onLogout={onLogout}
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOffers={onGoToOffers}
                onGoToHistory={onGoToHistory}
                onGoToOrders={() => {}}
            />

            <div className="row">
                <div className="col-12 col-md-6 mb-3">
                    <SummaryCard
                        title="Mes Commandes"
                        value={totalOrders > 0 ? `${totalOrders} commande(s)` : "Aucune commande"}
                        subValue={totalOrders > 0 ? "Suivi de vos commandes en cours" : "Passez votre première commande dès maintenant"}
                        color="#ff9800"
                    />
                </div>

                <div className="col-12 col-md-6 mb-3">
                    <SummaryCard
                        title="Dernière commande"
                        value={latestOrder ? `${latestOrder.montantTotal.toLocaleString('fr-BE', { minimumFractionDigits: 2 })} €` : "-"}
                        details={[
                            {
                                label: "Passée le",
                                value: latestOrder && Array.isArray(latestOrder.docCreation)
                                    ? new Date(latestOrder.docCreation[0], latestOrder.docCreation[1] - 1, latestOrder.docCreation[2]).toLocaleDateString('fr-BE')
                                    : "-"
                            },
                            { label: "Référence", value: latestOrder ? latestOrder.document : "-" }
                        ]}
                        color="#2980b9"
                    />
                </div>
            </div>

            <div className="shadow-sm bg-white p-2 p-md-3 rounded overflow-auto mb-4">
                <CustomGrid
                    dataSource={ordersData}
                    columns={columnConfig}
                    keyExpr="document"
                    columnAutoWidth={true}
                    wordWrapEnabled={true}
                    masterDetail={{
                        enabled: true,
                        showExpandIcon: false,
                        template: (container, options) => {
                            const root = createRoot(container);
                            root.render(
                                <GenericDetail
                                    data={options.data}
                                    {...orderDetailConfig}
                                />,
                                container
                            );
                        }
                    }}
                />
            </div>
        </div>
    );
}

export default Orders;