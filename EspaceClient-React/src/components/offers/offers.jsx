import React, { useState, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import 'devextreme/dist/css/dx.material.orange.light.css';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";

function Offers({ onLogout, onGoToInvoices, onGoToOrders, onGoToHome, onGoToHistory, onGoToAdmin }) {
    const [offersData, setOffersData] = useState([]);

    const totalOffers = offersData.length;
    const latestOffer = offersData.length > 0 ? offersData[0] : null;

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/offers/user?userId=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setOffersData(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    // Configuration pour le composant de détail générique
    const offerDetailConfig = {
        endpoint: "offers/details",
        idField: "document",
        title: "Articles de l'offre",
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
        { dataField: 'document', caption: 'N° Offre', alignment: 'center' },
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
        { dataField: 'docDes', caption: 'Libellé', alignment:'left'},
        { dataField: 'reference', caption: 'Référence Client', alignment:'left' },
        { dataField: 'fournisseur', caption: 'Fournisseur', alignment:'left' },
        {
            dataField: 'montantTotal',
            caption: 'Montant',
            alignment: 'center',
            cellRender: (cellData) => {
                return cellData.value !== undefined && cellData.value !== null
                    ? cellData.value.toLocaleString('fr-BE', { style: 'currency', currency: 'EUR' })
                    : (0).toLocaleString('fr-BE', { style: 'currency', currency: 'EUR' });
            }
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
                activeTab="Offres"
                onLogout={onLogout}
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToHistory={onGoToHistory}
                onGoToOffers={() => {}}
                onGoToAdmin={onGoToAdmin}
            />

            <div className="row">
                <div className="col-12 col-md-6 mb-3">
                    <SummaryCard
                        title="Mes Offres"
                        value={totalOffers > 0 ? `${totalOffers} offre(s)` : "Aucune offre"}
                        subValue={totalOffers > 0 ? "En attente de votre validation" : "Consultez votre délégué pour un devis"}
                        color="#ff9800"
                    />
                </div>

                <div className="col-12 col-md-6 mb-3">
                    <SummaryCard
                        title="Dernière proposition"
                        value={latestOffer
                            ? latestOffer.montantTotal.toLocaleString('fr-BE', { style: 'currency', currency: 'EUR' })
                            : "-"
                        }
                        details={[
                            {
                                label: "Reçue le",
                                value: latestOffer && Array.isArray(latestOffer.docCreation)
                                    ? new Date(latestOffer.docCreation[0], latestOffer.docCreation[1] - 1, latestOffer.docCreation[2]).toLocaleDateString('fr-BE')
                                    : "-"
                            },
                            { label: "Référence", value: latestOffer ? latestOffer.document : "-" }
                        ]}
                        color="#2980b9"
                    />
                </div>
            </div>

            <div className="shadow-sm bg-white p-2 p-md-3 rounded overflow-auto mb-4">
                <CustomGrid
                    dataSource={offersData}
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
                                    {...offerDetailConfig}
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

export default Offers;