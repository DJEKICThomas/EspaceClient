import React, { useState, useEffect } from 'react';
import CustomGrid from '../widgets/grid/customGrid';
import { locale } from 'devextreme/localization';
import Header from '../widgets/header/header';
import { useTranslation } from 'react-i18next';

function Infocentre({ onLogout, onGoToOrders, onGoToHistoric, onGoToSubscription, onGoToHome, onGoToOffers, onGoToAdmin, onGoToInvoices }) {
    const {t, i18n} = useTranslation();
    const [documents, setDocuments] = useState([]);

    useEffect(() => {
        locale(i18n.language);
    }, [i18n.language]);

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        //const selectedNcs = localStorage.getItem('selectedNcs'); //Si on veut juste le ncs
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/infocentre/user?userId=${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setDocuments(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    //Pour le moment
    const handleViewPdf = (docNumber) => {
        const testPdfUrl = "/pdf/DescriptionStage2025_EspaceClient.pdf";
        const width = window.screen.availWidth;
        const height = window.screen.availHeight;

        const windowFeatures = `
        width=${width},
        height=${height},
        top=0,
        left=0,
        popup=yes,
        resizable=yes,
        scrollbars=yes
    `;

        window.open(testPdfUrl, '_blank', windowFeatures);

        console.log("Visualisation du document : " + docNumber);
    };

    /* Dès que j'ai l'url de l'API
    const handleViewPdf = (filePath) => {
        if (!filePath) {
            alert("Chemin du document introuvable.");
            return;
        }

        // Concaténer l'URL de base.
        const baseUrl = process.env.REACT_APP_API_URL.replace('/api', '');
        const finalUrl = `${baseUrl}${filePath}`;

        const width = window.screen.availWidth;
        const height = window.screen.availHeight;

        window.open(finalUrl, '_blank', `width=${width},height=${height},popup=yes`);
    };*/



    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        {
            datafield: 'action',
            width: 250,
            caption: t('col_inv_action'),
            alignment: 'center',
            cellRender: (cellData) => {
                return (
                    <i
                        className="fi fi-ts-file-pdf pdf-icon-click"
                        onClick={() => handleViewPdf(cellData.data.id)}
                        //onClick={() => handleViewPdf(cellData.data.info)}
                    />
                );
            }
        },
        {
            dataField: 'ncs',
            width: 300,
            caption: t('NCS'),
            alignment: 'center',
        },
        {
            dataField: 'reference',
            caption: t('reference')
        },
        {
            dataField: 'dateDoc',
            caption: t('date'),
            width: 270,
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy'
        },
    ];

    return (
        <div className="container-fluid dx-viewport" style={{backgroundColor: '#f3f3f7', maxHeight: '100vh'}}>
            <Header
                onLogout={onLogout}
                activeTab="Document"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onGoToAdmin}
                onGoToSubscription={onGoToSubscription}
            />

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden mt-4">
                <CustomGrid
                    dataSource={documents}
                    columns={columnConfig}
                    keyExpr="id"
                    showSearch={true}
                    height="80vh"
                />
            </div>
        </div>
    );
}

export default Infocentre;