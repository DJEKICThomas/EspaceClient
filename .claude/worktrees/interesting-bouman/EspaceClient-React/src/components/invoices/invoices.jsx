import React, { useState, useEffect, useCallback } from 'react';
import CustomGrid from '../widgets/grid/customGrid';
import SummaryCard from '../widgets/summaryCard/summaryCard';
import { locale } from 'devextreme/localization';
import Header from '../widgets/header/header';
import { useTranslation } from 'react-i18next';
import PdfModal from "../widgets/pdfModal/pdfModal";

function Invoices({ onLogout, onGoToCustomerSelection, onGoToOrders, onGoToHistoric, onGoToSubscription, onGoToHome, onGoToOffers, onGoToAdmin, onGoToDocument, onGoToSettings, searchFilter, clearSearchFilter }) {
    const {t, i18n} = useTranslation();
    const [customerData, setCustomerData] = useState([]);
    const [showPdfModal, setShowPdfModal] = useState(false);
    const [selectedPdfUrl, setSelectedPdfUrl] = useState('');
    const [selectedPdfTitle, setSelectedPdfTitle] = useState('');

    useEffect(() => {
        locale(i18n.language);
    }, [i18n.language]);

    const currentLocale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    const loadData = async () => {
        const ncs = localStorage.getItem('selectedNcs');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/invoices/user?userId=${userId}&ncs=${ncs}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setCustomerData(data);
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

    //Après avec l'API
    /*const handleViewPdf = async (docNumber) => {
    const token = localStorage.getItem('token');
    const ncs = localStorage.getItem('username');

    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/documents/list?docNumber=${docNumber}&ncs=${ncs}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const data = await response.json();

            if (data && data.length > 0) {
                const realPath = data[0].filePath;
                setSelectedPdfUrl(`${process.env.REACT_APP_API_URL}/documents/view?path=${encodeURIComponent(realPath)}`);
                setSelectedPdfTitle(`Document ${docNumber}`);
                setShowPdfModal(true);
            } else {
                console.warn("Aucun document trouvé pour ce numéro.");
            }
        }
    } catch (error) {
        console.error("Erreur lors de la récupération du chemin PDF", error);
    }
  };*/


    useEffect(() => {
        loadData();
    }, []);

    const getStatusColors = (invoice) => {
        if (!invoice) return {color: '#7f8c8d', bgColor: '#f3f3f7'};
        if (invoice.paid) return {color: '#2ecc71', bgColor: '#eafaf1'};

        const dueDate = new Date(invoice.dueDate);
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        if (dueDate < today) return {color: '#e74c3c', bgColor: '#fdedec'};
        return {color: '#7f8c8d', bgColor: '#f3f3f7'};
    };

    const latestInvoice = customerData.length > 0 ? customerData[0] : null;
    const totalUnpaid = customerData
        .filter(inv => !inv.paid)
        .reduce((sum, inv) => sum + (inv.valCurD || 0), 0);

    const columnConfig = [
        {
            datafield: 'action',
            caption: t('col_inv_action'),
            width: 80,
            alignment: 'center',
            cellRender: (cellData) => {
                return (
                    <i
                        className="fi fi-ts-file-pdf pdf-icon-click"
                        onClick={() => handleViewPdf(cellData.data.num)}
                    />
                );
            }
        },
        {
            dataField: 'num',
            caption: t('col_inv_num'),
            width: 250,
            alignment: 'center',
            calculateSortValue: (rowData) => Number(rowData.num) || 0
        },
        {
            dataField: 'docDate',
            alignment: 'right',
            caption: t('col_date_creation'),
            width: 150,
            sortOrder: 'desc',
            dataType: 'date',
            format: 'dd/MM/yyyy',
        },
        {dataField: 'dueDate', caption: t('due_date'), alignment: 'right', dataType: 'date', format: 'dd/MM/yyyy', width: 150},
        {dataField: 'des', caption: t('label'), alignment: 'left',},
        {
            dataField: 'valCurD',
            caption: t('debit_due'),
            width: 200,
            alignment: 'center',
            cellRender: (cellData) => {
                return cellData.value !== undefined && cellData.value !== null
                    ? cellData.value.toLocaleString(currentLocale, {style: 'currency', currency: 'EUR'})
                    : (0).toLocaleString(currentLocale, {style: 'currency', currency: 'EUR'});
            }
        },
        {
            dataField: 'paid',
            caption: t('status'),
            width: 170,
            alignment: 'center',
            headerFilter: {
                dataSource: [
                    {text: t('paid'), value: ['paid', '=', true]},
                    {text: t('to_settle'), value: ['paid', '=', false]}
                ]
            },
            cellRender: (rowData) => {
                const isPaid = rowData.data.paid;
                return (
                    <span style={{
                        color: isPaid ? '#2ecc71' : '#e74c3c',
                        fontWeight: 'bold',
                        padding: '4px 8px',
                        borderRadius: '4px',
                        background: isPaid ? '#f8e4b4' : '#f8e4b4',
                        display: 'inline-block',
                        minWidth: '80px'
                    }}>
                        {isPaid ? t('paid') : t('to_settle')}
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

    //Nettoyer le filtre quand on quitte la page
    useEffect(() => {
        return() => {
            if(clearSearchFilter) clearSearchFilter();
        };
    }, [clearSearchFilter]);

    return (
        <div className="container-fluid dx-viewport dx-theme-background-color" style={{
            height: '100vh',
            display: 'flex',
            flexDirection: 'column',
            overflow: 'hidden'
        }}>
            <Header
                onLogout={onLogout}
                activeTab="Factures"
                onGoToHome={onGoToHome}
                onGoToInvoices={() => {}}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onGoToAdmin}
                onGoToDocument={onGoToDocument}
                onGoToSubscription={onGoToSubscription}
                onGoToSettings={onGoToSettings}
                onGoToCustomerSelection={onGoToCustomerSelection}
            />

            <div className="row g-3 mb-2" style={{ flexShrink: 0 }}>
                <div className="col-12 col-md-6 d-flex">
                    <SummaryCard
                        title={t('last_invoice')}
                        value={latestInvoice
                            ? latestInvoice.valCurD.toLocaleString(currentLocale, {style: 'currency', currency: 'EUR'})
                            : (0).toLocaleString(currentLocale, {style: 'currency', currency: 'EUR'})
                        }
                        details={[
                            {
                                label: t('issued_on'),
                                value: latestInvoice ? new Date(latestInvoice.docDate).toLocaleDateString(currentLocale) : "-"
                            },
                            {
                                label: t('due_date'),
                                value: latestInvoice ? new Date(latestInvoice.dueDate).toLocaleDateString(currentLocale) : "-"
                            }
                        ]}
                        status={{
                            label: latestInvoice?.paid ? t('paid') : t('to_settle'),
                            color: getStatusColors(latestInvoice).color,
                            bgColor: getStatusColors(latestInvoice).bgColor
                        }}
                    />
                </div>
                <div className="col-12 col-md-6 d-flex">
                    <SummaryCard
                        title={t('total_balance_due')}
                        value={totalUnpaid.toLocaleString(currentLocale, {style: 'currency', currency: 'EUR'})}
                        subValue={totalUnpaid > 0 ? t('invoices_pending') : t('up_to_date')}
                    />
                </div>
            </div>

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden"
                 style={{
                flex: 1,
                minHeight: 0,
                display: 'flex',
                flexDirection: 'column'
            }}>
                <CustomGrid
                    dataSource={customerData}
                    onRefresh={() => {
                        setGridFilter(null);
                        if (clearSearchFilter) clearSearchFilter();
                        loadData();
                    }}
                    columns={columnConfig}
                    keyExpr="id"
                    showSearch={true}
                    defaultFilter={gridFilter}
                    height="100%"
                />
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

export default Invoices;