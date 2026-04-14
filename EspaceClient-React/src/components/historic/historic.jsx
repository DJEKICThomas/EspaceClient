import React, { useState, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";
import { useTranslation } from 'react-i18next';

function Historic({ onLogout, onGoToCustomerSelection, onGoToCompanySelection, onGoToInvoices, onGoToOrders, onGoToSubscription, onGoToHome, onGoToOffers, onGoToAdmin, onGoToDocument, onGoToSettings }) {
    const { t, i18n } = useTranslation();
    const [historicData, setHistoricData] = useState([]);

    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    const fullHistoric = historicData.length;
    const latestHistoric = historicData.length > 0 ? historicData[0] : null;

    const loadData = async () => {
        const ncs = localStorage.getItem('selectedNcs');
        const company = localStorage.getItem('selectedCompany');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/historic/user?userId=${userId}&ncs=${ncs}&company=${company}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setHistoricData(data);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    const historicDetailConfig = {
        endpoint: "historic/details",
        idField: "document",
        title: t('order_details'),
        columns: [
            { dataField: 'article', caption: t('article') },
            { dataField: 'articleLab', caption: t('designation') },
            { dataField: 'priceUnit', caption: t('unit_price') },
            { dataField: 'quantity', caption: t('quantity') }
        ]
    };

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        { dataField: 'document', caption: t('col_order_num'), alignment: 'center', width: 200},
        {
            dataField: 'docCreation',
            width: 150,
            caption: t('col_date_creation'),
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
        {
            dataField: 'docCloture',
            width: 150,
            caption: t('col_date_closure'),
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.docCloture)) {
                    return new Date(rowData.docCloture[0], rowData.docCloture[1] - 1, rowData.docCloture[2]);
                }
                return rowData.docCloture;
            }
        },
        { dataField: 'docDes', caption: t('label'), alignment:'left', width: 350},
        { dataField: 'reference', caption: t('reference'), alignment:'left', },
        { dataField: 'fournisseur', caption: t('client'), alignment:'left', width: 350},
        {
            dataField: 'montantTotal',
            width: 200,
            caption: t('amount'),
            alignment: 'center',
            cellRender: (cellData) => {
                return cellData.value !== undefined && cellData.value !== null
                    ? cellData.value.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                    : (0).toLocaleString(locale, { style: 'currency', currency: 'EUR' });
            }
        }];

    return (
        <div className="container-fluid dx-viewport dx-theme-background-color"
             style={{
                 height: '100vh',
                 display: 'flex',
                 flexDirection: 'column',
                 overflow: 'hidden'
             }}>
            <Header
                onLogout={onLogout}
                activeTab="Historique"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={() => {}}
                onGoToAdmin={onGoToAdmin}
                onGoToDocument={onGoToDocument}
                onGoToSubscription={onGoToSubscription}
                onGoToSettings={onGoToSettings}
                onGoToCustomerSelection={onGoToCustomerSelection}
                onGoToCompanySelection={onGoToCompanySelection}
            />

            <div className="row g-3">
                <div className="col-12 col-md-4 d-flex">
                    <SummaryCard
                        title={t('historic')}
                        value={fullHistoric > 0 ? `${fullHistoric} ` + t('orders_count') : t('no_history')}
                        subValue={fullHistoric > 0 ? t('history_followup') : t('history_empty')}
                    />
                </div>
                <div className="col-12 col-md-4 d-flex">
                    <SummaryCard
                        title={t('last_order')}
                        value={latestHistoric
                            ? latestHistoric.montantTotal.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                            : "-"
                        }
                        details={[
                            {
                                label: t('placed_on'),
                                value: latestHistoric && Array.isArray(latestHistoric.docCreation)
                                    ? new Date(latestHistoric.docCreation[0], latestHistoric.docCreation[1] - 1, latestHistoric.docCreation[2]).toLocaleDateString(locale)
                                    : "-"
                            },
                            { label: t('reference'), value: latestHistoric ? latestHistoric.document : "-" }
                        ]}
                    />
                </div>
            </div>
            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden mt-3"
                 style={{
                     flexGrow: 1,
                     minHeight: 0,
                     display: 'flex',
                     flexDirection: 'column'
                 }}>
                <CustomGrid
                    dataSource={historicData}
                    columns={columnConfig}
                    keyExpr="document"
                    showSearch={true}
                    height="100%"
                    masterDetail={{
                        enabled: true,
                        template: (container, options) => {
                            const root = createRoot(container);
                            root.render(<GenericDetail data={options.data} {...historicDetailConfig} />);
                        }
                    }}
                />
            </div>
        </div>
    );
}

export default Historic;