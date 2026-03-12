import React, { useState, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import SummaryCard from "../widgets/summaryCard/summaryCard";
import CustomGrid from "../widgets/grid/customGrid";
import Header from "../widgets/header/header";
import GenericDetail from "../widgets/genericDetail/genericDetail";
import { useTranslation } from "react-i18next";

function Offers({ onLogout, onGoToInvoices, onGoToOrders, onGoToSubscription, onGoToHome, onGoToHistoric, onGoToAdmin, onGoToDocument }) {
    const { t, i18n } = useTranslation();
    const [offersData, setOffersData] = useState([]);

    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';
    const totalOffers = offersData.length;
    const latestOffer = offersData.length > 0 ? offersData[0] : null;

    const loadData = async () => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('username');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/offers/user?userId=${userId}`, {
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

    const offerDetailConfig = {
        endpoint: "offers/details",
        idField: "document",
        title: t('offer_details'),
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
        {
            datafield: 'action',
            caption: t('col_inv_action'),
            width: 100,
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
        { dataField: 'document', caption: t('col_offer_num'), alignment: 'center', width: 200 },
        {
            dataField: 'docCreation',
            caption: t('date'),
            width: 150,
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
        { dataField: 'fournisseur', caption: t('supplier'), alignment:'left', width: 350},
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
        {
          dataField: 'expiryDate',
          caption: t('expiryDate'),
          width: 150,
          alignment: 'center',
          dataType: 'date',
          format: 'dd/MM/yyyy',
          cellRender: (cellData) => {
              return (
                  <span style={{ color: '#e67e22', fontWeight: '500'}}>
                      {cellData.text}
                  </span>
              );
          }
        }
    ];

    return (
        <div className="container-fluid dx-viewport" style={{ backgroundColor: '#f3f3f7', maxHeight: '100vh' }}>
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
            />

            <div className="row g-3">
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
                        value={latestOffer
                            ? latestOffer.montantTotal.toLocaleString(locale, { style: 'currency', currency: 'EUR' })
                            : "-"
                        }
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

            <div className="dx-card dx-widget shadow-sm p-2 p-md-3 rounded overflow-hidden mb-4">
                <CustomGrid
                    dataSource={offersData}
                    columns={columnConfig}
                    keyExpr="document"
                    height="53vh"
                    showSearch={true}
                    masterDetail={{
                        enabled: true,
                        template: (container, options) => {
                            const root = createRoot(container);
                            root.render(<GenericDetail data={options.data} {...offerDetailConfig} />);
                        }
                    }}
                />
            </div>
        </div>
    );
}

export default Offers;