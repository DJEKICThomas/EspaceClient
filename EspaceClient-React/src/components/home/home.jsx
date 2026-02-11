import React, { useState, useEffect } from 'react';
import 'devextreme/dist/css/dx.material.orange.light.css';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";

// N'oublie pas de récupérer les props ici !
function Historic({ onLogout, onGoToInvoices, onGoToOrders, onGoToHistory, onGoToOffers }) {
    const [ordersData, setOrdersData] = useState([]);

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
                activeTab="Accueil"
                onLogout={onLogout}
                onGoToHome={() => {}}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistory={onGoToHistory}
            />

            {/* Contenu spécifique à l'historique */}
            <div className="row">
                <div className="col-12 mb-3">
                    <SummaryCard
                        title="Votre page d'accueil"
                        value="En cours"
                        subValue="Voici votre accueil personnalisé"
                        color="#ff9800"
                    />
                </div>
            </div>

            <div className="shadow-sm bg-white p-2 p-md-3 rounded overflow-auto mb-4">
                <h5 className="text-muted p-3">Vision globale de votre accueil (bientôt disponible)</h5>
            </div>
        </div>
    );
}

export default Historic;