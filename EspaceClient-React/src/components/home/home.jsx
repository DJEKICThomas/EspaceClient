import React, { useEffect, useState } from 'react';
import GenericPieChart from '../widgets/genericPieChart/genericPieChart';
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";

function Home({ onLogout, onGoToInvoices, onGoToSubscription, onGoToOrders, onGoToHome, onGoToOffers, onGoToAdmin, onGoToHistoric, onGoToDocument }) {
    const [myClients, setMyClients] = useState([]);
    const [mySubscriptions, setMySubscriptions] = useState([]);
    const { t } = useTranslation();

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');

        try{
            const clientChartResponse = await fetch(`${process.env.REACT_APP_API_URL}/wm-customers?userId=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if(clientChartResponse.ok){
                const clientChartData = await clientChartResponse.json();
                setMyClients(clientChartData); //Stockage des clients
            }

            const subscriptionChartResponse = await fetch(`${process.env.REACT_APP_API_URL}/subscriptions/user?userId=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (subscriptionChartResponse.ok) {
                const subscriptionChartData = await subscriptionChartResponse.json();
                setMySubscriptions(subscriptionChartData);
            }
        } catch (error) {
            console.error("Erreur réseau lors de la récupération des clients :", error);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const clientChartData = myClients.map(client => ({
        ...client,
        valeurPart: 1
    }));

    const subscriptionChartData = mySubscriptions.map(subscription => ({
        ...subscription,
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
                onGoToSubscription={onGoToSubscription}
            />

            <div className="p-4">
                <h2 className="mb-4 text-center" style={{ fontWeight: '300' }}> {t('dashboard')} </h2>

                <div className="row justify-content-center g-3">
                    <div className="col-12 col-md-6 col-lg-5">
                        {/* On appelle le widget DIRECTEMENT sans div 'dx-card' autour */}
                        <GenericPieChart
                            data={clientChartData}
                            argumentField="customerName"
                            valueField="valeurPart"
                            tooltipField1="customerName"
                            tooltipField2="ncs"
                            cardTitle={t('my_client')}
                            totalCount={myClients.length}
                            labelText={t('active_clients')}
                        />
                    </div>

                    {/*<div className="col-12 col-md-4">
                        <GenericPieChart
                            data={subscriptionChartData} // Tes stats calculées (Actifs vs Expirés)
                            argumentField="status"
                            valueField="count"
                            tooltipField1="status"
                            tooltipField2="count"
                            cardTitle={t('subscription_status')}
                            totalCount={mySubscriptions.length} // Le total des abonnements
                            labelText={t('active_contracts')}
                            palette={['#198754', '#dc3545']} // Vert pour Actif, Rouge pour Expiré
                            type="doughnut" // Design en anneau pour varier
                        />
                    </div>

                    <div className="col-12 col-md-4">*/}

                        {/* On appelle le widget DIRECTEMENT sans div 'dx-card' autour */}
                    {/*<GenericPieChart
                            data={clientChartData}
                            argumentField="customerName"
                            valueField="valeurPart"
                            cardTitle={t('my_client')}
                            totalCount={myClients.length}
                            labelText={t('active_clients')}
                        />
                    </div>*/}
                </div>
            </div>
        </div>
    );
}

export default Home;