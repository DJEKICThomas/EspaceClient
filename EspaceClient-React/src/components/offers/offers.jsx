import React, { useState, useEffect, useCallback } from 'react';
import { createRoot } from 'react-dom/client';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";
import PdfModal from "../widgets/pdfModal/pdfModal";
import { useTranslation } from "react-i18next";

function Offers({ onLogout, onGoToCustomerSelection, onGoToCompanySelection, onGoToInvoices, onGoToOrders, onGoToSubscription, onGoToHome, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToSettings, searchFilter, clearSearchFilter }) {
    const { t, i18n } = useTranslation();
    const [offersData, setOffersData] = useState([]);
    const [showPdfModal, setShowPdfModal] = useState(false);
    const [selectedPdfUrl, setSelectedPdfUrl] = useState('');
    const [selectedPdfTitle, setSelectedPdfTitle] = useState('');

    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';
    const totalOffers = offersData.length;
    const latestOffer = offersData.length > 0 ? offersData[0] : null;

    const loadData = async () => {
        const ncs = localStorage.getItem('selectedNcs');
        const company = localStorage.getItem('selectedCompany');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/offers/user?userId=${userId}&ncs=${ncs}&company=${company}`, {
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

    const offerDetailConfig = {
        endpoint: "offers/details",
        idField: "document",
        title: t('offer_details'),
        columns: [
            { dataField: 'article', caption: t('article') },
            { dataField: 'articleLab', caption: t('designation') },
            { dataField: 'priceUnit', caption: t('unit_price'),
                cellRender: (cellData) => {
                    return cellData.value !== undefined && cellData.value !== null
                        ? cellData.value.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                        : (0).toLocaleString(locale, { style: 'currency', currency: 'EUR' });
                }},
            { dataField: 'quantity', caption: t('quantity') }
        ]
    };

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        {
            datafield: 'action',
            caption: t('col_inv_action'),
            width: 50,
            alignment: 'center',
            cellRender: (cellData) => {
                return (
                    <i
                        className="fi fi-ts-file-pdf pdf-icon-click"
                        onClick={() => handleViewPdf(cellData.data.document)}
                    />
                );
            }
        },
        { dataField: 'document', caption: t('col_offer_num'), alignment: 'center', width: 200 },
        {
            dataField: 'docCreation',
            caption: t('date'),
            width: 100,
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            sortOrder: 'desc',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.docCreation)) {
                    return new Date(rowData.docCreation[0], rowData.docCreation[1] - 1, rowData.docCreation[2]);
                }
                return rowData.docCreation;
            }
        },
        { dataField: 'docDes', caption: t('label'), alignment:'left', width: 150},
        { dataField: 'reference', caption: t('customer_ref'), alignment:'left',},
        { dataField: 'fournisseur', caption: t('client'), alignment:'left', width: 200},
        {
            dataField: 'montantTotal',
            caption: t('amount'),
            width: 190,
            alignment: 'center',
            cellRender: (cellData) => {
                return cellData.value !== undefined && cellData.value !== null
                    ? cellData.value.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                    : (0).toLocaleString(locale, { style: 'currency', currency: 'EUR' });
            }
        },
        /*{
            dataField: 'docCloture',
            caption: t('expiryDate'),
            width: 150,
            alignment: 'center',
            dataType: 'date',
            format: 'dd/MM/yyyy',
            // Convertit le tableau [année, mois, jour] en jj/mm/yyyy
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.docCloture)) {
                    return new Date(rowData.docCloture[0], rowData.docCloture[1] - 1, rowData.docCloture[2]);
                }
                return rowData.docCloture;
            },
            cellRender: (cellData) => {
                return (
                    <span style={{ color: '#ff9800', fontWeight: '500'}}>
                      {cellData.text}
                  </span>
                );
            }
        },*/
        {
            dataField: 'statut',
            caption: t('status'),
            width: 200,
            alignment: 'center',
            cellRender: (cellData) => {
                const keys = cellData.value ? cellData.value.split(' / ') : [];
                const translated = keys.map(k => t(k)).join(' / ');
                return (
                    <span style={{
                        color: '#ffffff',
                        fontWeight: 'bold',
                        padding: '4px 8px',
                        borderRadius: '4px',
                        background: '#28a1b1',
                        display: 'inline-block',
                        minWidth: '80px'
                    }}>
                        {translated || '-'}
                    </span>
                );
            }
        }
    ];

    const [gridFilter, setGridFilter] = useState(null);

    // Nettoyer le filtre quand on refresh DevExtreme
    useEffect(() => {
        if (searchFilter) {
            setGridFilter([searchFilter.field, '=', searchFilter.value]);
        }
    }, [searchFilter]);

    // Nettoyer le filtre quand on quitte la page
    useEffect(() => {
        return () => {
            if (clearSearchFilter) clearSearchFilter();
        };
    }, [clearSearchFilter]);

    return (
        <div className="container-fluid dx-viewport dx-theme-background-color"
             style={{
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
             }}>
            <div style={{ flexShrink: 0 }}>
                <Header
                    onLogout={onLogout}
                    activeTab="Offres"
                    onGoToHome={onGoToHome}
                    onGoToInvoices={onGoToInvoices}
                    onGoToOrders={onGoToOrders}
                    onGoToHistoric={onGoToHistoric}
                    onGoToOffers={() => {}}
                    onGoToAdmin={onGoToAdmin}
                    onGoToDocument={onGoToDocument}
                    onGoToSubscription={onGoToSubscription}
                    onGoToSettings={onGoToSettings}
                    onGoToCustomerSelection={onGoToCustomerSelection}
                    onGoToCompanySelection={onGoToCompanySelection}
                />

                <div className="row g-3 px-3 mt-1 mb-2">
                    <div className="col-12 col-md-6 d-flex">
                        <SummaryCard
                            title={t('my_offers')}
                            value={totalOffers > 0 ? `${totalOffers} ` + t('offers_label') : t('no_offers')}
                            subValue={totalOffers > 0 ? t('awaiting_your_approval') : t('consult_rep_for_quote')}
                        />
                    </div>
                    <div className="col-12 col-md-6 d-flex">
                        <SummaryCard
                            title={t('last_proposal')}
                            value={latestOffer ? latestOffer.montantTotal.toLocaleString(locale, { style: 'currency', currency: 'EUR' }) : "-"}
                            details={[
                                {
                                    label: t('received_on'),
                                    value: latestOffer && Array.isArray(latestOffer.docCreation)
                                        ? new Date(latestOffer.docCreation[0], latestOffer.docCreation[1] - 1, latestOffer.docCreation[2]).toLocaleDateString(locale)
                                        : "-"
                                },
                                { label: t('reference'), value: latestOffer ? latestOffer.document : "-" }
                            ]}
                        />
                    </div>
                </div>
            </div>

            <div className="mx-3 mb-3" style={{ flex: 1, minHeight: 0, display: 'flex', flexDirection: 'column' }}>
                <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden" style={{ height: '100%' }}>
                    <CustomGrid
                        dataSource={offersData}
                        onRefresh={() => {
                            setGridFilter(null);
                            if (clearSearchFilter) clearSearchFilter();
                            loadData();
                        }}
                        columns={columnConfig}
                        keyExpr="document"
                        height="100%"
                        showSearch={true}
                        defaultFilter={gridFilter}
                        masterDetail={{
                            enabled: true,
                            template: (container, options) => {
                                const root = createRoot(container);
                                root.render(<GenericDetail data={options.data} {...offerDetailConfig} onViewPdf={handleViewPdf} />);
                            }
                        }}
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

export default Offers;