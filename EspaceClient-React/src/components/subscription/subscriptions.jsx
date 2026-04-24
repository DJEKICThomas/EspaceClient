import React, { useState, useEffect, useMemo, useCallback } from 'react';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";
import {createRoot} from "react-dom/client";
import GenericDetail from "../widgets/genericDetail/genericDetail";
import PdfModal from "../widgets/pdfModal/pdfModal";

function Subscriptions({ onLogout, onGoToCustomerSelection, onGoToCompanySelection, onGoToInvoices, onGoToOffers, onGoToHome, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToOrders, onGoToSettings, searchFilter, clearSearchFilter }) {
    const { t, i18n } = useTranslation();
    const [subscriptionsData, setSubscriptionsData] = useState([]);
    const [showPdfModal, setShowPdfModal] = useState(false);
    const [selectedPdfUrl, setSelectedPdfUrl] = useState('');
    const [selectedPdfTitle, setSelectedPdfTitle] = useState('');

    const currentLocale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    const subStats = useMemo(() => {
        const today = new Date().setHours(0, 0, 0, 0);
        const in30Days = today + (30 * 24 * 60 * 60 * 1000);

        let active = 0, soon = 0

        subscriptionsData.forEach(sub => {
            const end = Array.isArray(sub.endDate)
                ? new Date(sub.endDate[0], sub.endDate[1] - 1, sub.endDate[2]).getTime()
                : new Date(sub.endDate).getTime();

            if (end <= in30Days) {
                soon++;
            } else {
                active++;
            }
        });
        return { active, soon };
    }, [subscriptionsData]);

    const loadData = async () => {
        const ncs = localStorage.getItem('selectedNcs');
        const company = localStorage.getItem('selectedCompany');
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        const today = new Date().setHours(0, 0, 0, 0);

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/subscriptions/user?userId=${userId}&ncs=${ncs}&company=${company}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();

                const onlyActiveSub = data.filter( sub => {
                    const end = Array.isArray(sub.endDate)
                        ? new Date(sub.endDate[0], sub.endDate[1] - 1, sub.endDate[2]).getTime()
                        : new Date(sub.endDate).getTime();

                    return sub.active && end >= today;
                });

                setSubscriptionsData(onlyActiveSub);
            }
        } catch (error) {
            console.error("Erreur réseau : ", error);
        }
    };

    const subscriptionDetailConfig = {
        endpoint: "subscriptions/details",
        idParamName: "docNumber",
        idField: "docCtr",

        title: t('subscription_details'),
        columns: [
            { dataField: 'articleCode', caption: t('article'), width: 120 },
            { dataField: 'description', caption: t('col_description') },
            {
                dataField: 'quantity',
                caption: t('quantity'),
                width: 80,
                alignment: 'left'
            },
            {
                dataField: 'price',
                caption: t('amount'),
                width: 100,
                alignment: 'center',
                cellRender: (cellData) => {
                    return cellData.value !== undefined && cellData.value !== null
                        ? cellData.value.toLocaleString(currentLocale, {style: 'currency', currency: 'EUR'})
                        : (0).toLocaleString(currentLocale, {style: 'currency', currency: 'EUR'});
                }
            },
            {
                dataField: 'startDate',
                caption: t('start_date'),
                dataType: 'date',
                alignment: 'right',
                format: 'dd/MM/yyyy',
                width: 110
            },
            {
                dataField: 'nextDueDate',
                caption: t('next_due_date'),
                dataType: 'date',
                alignment: 'right',
                format: 'dd/MM/yyyy',
                width: 110
            },
            {
                dataField: 'status',
                caption: t('status'),
                width: 130,
                alignment: 'center',
                cellRender: (cellData) => {
                    const row = cellData.data;
                    const today = new Date().setHours(0, 0, 0, 0);
                    const in30Days = today + (30 * 24 * 60 * 60 * 1000);

                    const dueDateRaw = row.nextDueDate;
                    let dueTime = null;

                    if (Array.isArray(dueDateRaw) && dueDateRaw.length === 3) {
                        dueTime = new Date(dueDateRaw[0], dueDateRaw[1] - 1, dueDateRaw[2]).getTime();
                    } else if (dueDateRaw) {
                        dueTime = new Date(dueDateRaw).getTime();
                    }

                    if (dueTime === null){
                        return <span className="badge bg-secondary">-</span>
                    }

                    if (dueTime <= in30Days) {
                        return <span className="badge" style={{backgroundColor: '#ff9800'}}>{t('expiring_soon')}</span>;
                    } else {
                        return <span className="badge bg-success">{t('active')}</span>;
                    }
                }
            }
        ]
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

    useEffect(() => {
        loadData();
    }, []);

    const columnConfig = [
        {
            dataField: 'action',
            width: 100,
            caption: t('col_inv_action'),
            alignment: 'center',
            cellRender: (cellData) => (
                <i className="fi fi-ts-file-pdf pdf-icon-click" onClick={() => handleViewPdf(cellData.data.docCtr)} />
            )
        },
        { dataField: 'reference', caption: t('reference'), alignment: 'center', width: 350},
        {
            dataField: 'startDate',
            width: 150,
            caption: t('start_date'),
            dataType: 'date',
            sortOrder: 'desc',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.startDate)) {
                    return new Date(rowData.startDate[0], rowData.startDate[1] - 1, rowData.startDate[2]);
                }
                return rowData.startDate;
            }
        },
        {
            dataField: 'endDate',
            caption: t('end_date'),
            width: 150,
            dataType: 'date',
            alignment: 'right',
            format: 'dd/MM/yyyy',
            calculateCellValue: (rowData) => {
                if (Array.isArray(rowData.endDate)) {
                    return new Date(rowData.endDate[0], rowData.endDate[1] - 1, rowData.endDate[2]);
                }
                return rowData.endDate;
            }
        },
        { dataField: 'description', caption: t('label'), alignment: 'left',},
        { dataField: 'recur', caption: t('frequency'), alignment: 'center', width: 200 },
        {
            dataField: 'active',
            caption: t('status'),
            width: 200,
            alignment: 'center',
            headerFilter: {
                dataSource: [
                    {
                        text: t('active'),
                        value: true
                    },
                    {
                        text: t('expiring_soon'),
                        value: false
                    },
                ]
            },
            cellRender: (cellData) => {
                const sub = cellData.data;
                const today = new Date().setHours(0, 0, 0, 0);
                const in30Days = today + (30 * 24 * 60 * 60 * 1000);
                const end = Array.isArray(sub.endDate)
                    ? new Date(sub.endDate[0], sub.endDate[1] - 1, sub.endDate[2]).getTime()
                    : new Date(sub.endDate).getTime();

                // Logique des badges
                if (end <= in30Days) {
                    return <span className="badge" style={{backgroundColor: '#ff9800'}}>{t('expiring_soon')}</span>;
                } else {
                    return <span className="badge bg-success">{t('active')}</span>;
                }
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
                 overflow: 'hidden'
             }}>
            <Header
                onLogout={onLogout}
                activeTab="Abonnements"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToOrders={onGoToOrders}
                onGoToAdmin={onGoToAdmin}
                onGoToDocument={onGoToDocument}
                onGoToSubscriptions={() => {}}
                onGoToSettings={onGoToSettings}
                onGoToCustomerSelection={onGoToCustomerSelection}
                onGoToCompanySelection={onGoToCompanySelection}
            />

            <div className="row g-3 mb-3">
                <div className="col-12 col-md-6 d-flex">
                    <SummaryCard
                        title={t('my_subscriptions')}
                        value={subscriptionsData.length.toString()}
                        details={[
                            {
                                label: t('active_now'),
                                value: <span style={{ color: '#198754', fontWeight: 'bold' }}>{subStats.active}</span>
                            },
                            {
                                label: t('expiring_soon'),
                                value: <span style={{ color: '#ff9800', fontWeight: 'bold' }}>{subStats.soon}</span>
                            }
                        ]}
                    />
                </div>
            </div>

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden"
                 style={{
                     flexGrow: 1,
                     minHeight: 0,
                     display: 'flex',
                     flexDirection: 'column'
                 }}>
                <CustomGrid
                    dataSource={subscriptionsData}
                    onRefresh={() => {
                        setGridFilter(null);
                        if (clearSearchFilter) clearSearchFilter();
                        loadData();
                    }}
                    columns={columnConfig}
                    keyExpr="id"
                    height="100%"
                    showSearch={true}
                    defaultFilter={gridFilter}
                    masterDetail={{
                        enabled: true,
                        template: (container, options) => {
                            const root = createRoot(container);
                            root.render(<GenericDetail data={options.data} {...subscriptionDetailConfig} onViewPdf={handleViewPdf} />);
                        }
                    }}
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

export default Subscriptions;