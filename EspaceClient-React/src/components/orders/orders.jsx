import React, { useState, useEffect } from 'react';
import 'devextreme/dist/css/dx.material.orange.light.css';
import Button from "../widgets/button/button";
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";

// N'oublie pas de récupérer les props ici !
function Orders({ onLogout, onGoToInvoices, onGoToHistory, onGoToHome, onGoToOffers }) {
    const [ordersData, setOrdersData] = useState([]);

    const columnConfig = [];

    // Simulation de chargement de données
    useEffect(() => {
        // loadOrdersData();
    }, []);

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
                onGoToOrders={() => {}}
                onGoToOffers={onGoToOffers}
                onGoToHistory={onGoToHistory}
            />

            {/* Contenu spécifique aux commandes */}
            <div className="row">
                <div className="col-12 mb-3">
                    <SummaryCard
                        title="Mes commandes"
                        value="En cours"
                        subValue="Suivez l'état de vos commandes"
                        color="#ff9800"
                    />
                </div>
            </div>

            <div className="shadow-sm bg-white p-2 p-md-3 rounded overflow-auto mb-4">
                <h5 className="text-muted p-3">Liste des commandes (bientôt disponible)</h5>
                {/* <CustomGrid dataSource={ordersData} columns={orderColumns} /> */}
            </div>
        </div>
    );
}

export default Orders;