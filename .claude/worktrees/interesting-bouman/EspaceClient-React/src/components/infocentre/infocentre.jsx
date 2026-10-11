import React, { useState, useEffect, useCallback } from 'react';
import CustomGrid from '../widgets/grid/customGrid';
import { locale } from 'devextreme/localization';
import Header from '../widgets/header/header';
import { useTranslation } from 'react-i18next';
import PdfModal from "../widgets/pdfModal/pdfModal";

function Infocentre({ onLogout, onGoToCustomerSelection, onGoToOrders, onGoToHistoric, onGoToSubscription, onGoToHome, onGoToOffers, onGoToAdmin, onGoToInvoices, onGoToSettings }) {
    const {t, i18n} = useTranslation();
    const [documents, setDocuments] = useState([]);
    const [showPdfModal, setShowPdfModal] = useState(false);
    const [selectedPdfUrl, setSelectedPdfUrl] = useState('');
    const [selectedPdfTitle, setSelectedPdfTitle] = useState('');

    useEffect(() => {
        locale(i18n.language);
    }, [i18n.language]);

    const loadData = async () => {
        const ncs = localStorage.getItem('selectedNcs');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/infocentre/user?userId=${userId}&ncs=${ncs}`, {
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
    const handleViewPdf = useCallback((docNumber) => {
        const testPdfUrl = "/pdf/DescriptionStage2025_EspaceClient.pdf";
        setSelectedPdfUrl(testPdfUrl);
        setSelectedPdfTitle(`${t('document')} ${docNumber}`);
        setShowPdfModal(true);
    }, [t]);

    const handleClosePdf = useCallback(() => {
        setShowPdfModal(false);
        setSelectedPdfUrl('');
        setSelectedPdfTitle('');
    }, []);

    /* Dès que j'ai l'url de l'API
    const handleViewPdf = useCallback((filePath) => {
        if (!filePath) {
            alert("Chemin du document introuvable.");
            return;
        }

        const baseUrl = process.env.REACT_APP_API_URL.replace('/api', '');
        const finalUrl = `${baseUrl}${filePath}`;
        setSelectedPdfUrl(finalUrl);
        setSelectedPdfTitle(`Document`);
        setShowPdfModal(true);
    }, []);*/



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
        <div className="container-fluid dx-viewport dx-theme-background-color"
             style={{
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
                 overflow: 'hidden'
             }}>
            <div style={{ flexShrink: 0 }}>
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
                    onGoToSettings={onGoToSettings}
                    onGoToCustomerSelection={onGoToCustomerSelection}
                />
            </div>

            <div className="mx-3 mt-4 mb-3" style={{ flex: 1, minHeight: 0, display: 'flex', flexDirection: 'column' }}>
                <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden" style={{ height: '100%' }}>
                    <CustomGrid
                        dataSource={documents}
                        columns={columnConfig}
                        keyExpr="id"
                        showSearch={true}
                        height="100%"
                    />
                </div>
            </div>

            <PdfModal
                show={showPdfModal}
                onClose={handleClosePdf}
                pdfUrl={selectedPdfUrl}
                title={selectedPdfTitle}
            />
        </div>
    );
}

export default Infocentre;