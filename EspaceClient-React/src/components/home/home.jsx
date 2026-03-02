import React, { useEffect, useState } from 'react';
import ClientChart from '../widgets/clientChart/clientChart';
//import SummaryCard from "../widgets/summaryCard/summaryCard";
//import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";

function Historic({ onLogout, onGoToInvoices, onGoToOrders, onGoToHome, onGoToOffers, onGoToAdmin, onGoToHistoric, onGoToDocument }) {
    const [myClients, setMyClients] = useState([]);
    const { t } = useTranslation();

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');

        try{
            const response = await fetch(`${process.env.REACT_APP_API_URL}/wm-customers?userId=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if(response.ok){
                const data = await response.json();
                setMyClients(data); //Stockage des clients
            }
        } catch (error) {
            console.error("Erreur réseau lors de la récupération des clients :", error);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const chartData = myClients.map(client => ({
        ...client,
        valeurPart: 1
    }));

    return (
        <div className="container-fluid dx-viewport" style={{ backgroundColor: '#f3f3f7', minHeight: '100vh' }}> {/*4dbcca autre couleur ?*/}
            <Header
                onLogout={onLogout}
                activeTab="Accueil"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onGoToAdmin}
                onGoToDocument={onGoToDocument}
            />

            <div className="p-4">
                <h2 className="mb-4 text-center" style={{ fontWeight: '300' }}> {t('dashboard')} </h2>

                <div className="row justify-content-center">
                    <div className="col-12 col-lg-8 col-xl-6 mb-4">
                        <div className="dx-card shadow-sm p-4 bg-white border-0" style={{ borderRadius: '15px' }}>
                            <ClientChart data={chartData}/>

                            <div className="text-center mt-3">
                                <span className="badge rounded-pill bg-light text-dark p-2 border">
                                    {myClients.length} {t('active_clients')}
                                </span>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    );
}

export default Historic;