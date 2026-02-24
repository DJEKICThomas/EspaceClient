import React, { useEffect } from 'react';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";

function Historic({ onLogout, onGoToInvoices, onGoToOrders, onGoToHome, onGoToOffers, onGoToAdmin, onGoToHistoric }) {
    const { t } = useTranslation();

    useEffect(() => {
        // Logique de chargement future
    }, []);

    return (
        <div className="container-fluid p-3 dx-viewport" style={{ backgroundColor: '#f3f3f7', minHeight: '100vh' }}> {/*4dbcca autre couleur ?*/}
            <Header
                onLogout={onLogout}
                activeTab="Accueil"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onGoToAdmin}
            />
        </div>
    );
}

export default Historic;