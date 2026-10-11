import React, { useMemo } from 'react';
import { Chart, Series, CommonSeriesSettings, Legend, Tooltip, ArgumentAxis, ValueAxis, Animation } from 'devextreme-react/chart';
import { PieChart, Series as PieSeries, Legend as PieLegend, Tooltip as PieTooltip, Label as PieLabel } from 'devextreme-react/pie-chart';
import { useTranslation } from 'react-i18next';

const COLORS = {
    success: '#2ecc71',
    danger: '#e74c3c',
    warning: '#f39c12',
};

function parseDate(dateValue) {
    if (Array.isArray(dateValue)) {
        return new Date(dateValue[0], dateValue[1] - 1, dateValue[2] || 1);
    }
    return new Date(dateValue);
}

function DashboardCharts({ invoices = [], subscriptions = [], offers = [], monthlyTotals = [] }) {
    const { t, i18n } = useTranslation();
    const locale = i18n.language.startsWith('en') ? 'en-GB' : 'fr-BE';

    function toggleVisibility(item) {
        item.isVisible() ? item.hide() : item.show();
    }

    function onPointClick(e) {
        toggleVisibility(e.target);
    }

    function onLegendClick(e) {
        const arg = e.target;
        const item = e.component.getAllSeries()[0].getPointsByArg(arg)[0];
        toggleVisibility(item);
    }

    // --- 1. Commandes vs Factures par mois (données depuis l'API analytics) ---
    const hasOrders = monthlyTotals.some(m => m.ordersTotal !== 0 && m.ordersTotal != null);
    const hasInvoices = monthlyTotals.some(m => m.invoicesTotal !== 0 && m.invoicesTotal != null);

    // --- 2. Subscription Analyse: expiré, bientôt, valide
    const subscriptionExpiryData = useMemo(() => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const in30Days = new Date(today);
        in30Days.setDate(in30Days.getDate() + 30); // Date d'expiration

        let expired = 0;
        let expiringSoon = 0;
        let valid = 0;

        subscriptions.forEach((sub) => {
            if (sub.endDate == null) return;
            const end = parseDate(sub.endDate);
            if (isNaN(end.getTime())) return;
            end.setHours(0, 0, 0, 0);

            if (end < today) {
                expired++;
            } else if (end <= in30Days) {
                expiringSoon++;
            } else {
                valid++;
            }
        });

        return [
            { category: t('expired'), count: expired, color: COLORS.danger },
            { category: t('expiring_soon'), count: expiringSoon, color: COLORS.warning },
            { category: t('valid'), count: valid, color: COLORS.success },
        ].filter(item => item.count > 0);
    }, [subscriptions, t]);

    // --- 3. Payé pas payé ---
    const paidVsOutstandingData = useMemo(() => {
        let paidTotal = 0;
        let outstandingTotal = 0;
        invoices.forEach((inv) => {
            const amount = inv.valCurD || 0;
            if (inv.paid) {
                paidTotal += amount;
            } else {
                outstandingTotal += amount;
            }
        });
        return [
            { category: t('paid'), amount: paidTotal, color: COLORS.success },
            { category: t('chart_outstanding'), amount: outstandingTotal, color: COLORS.danger },
        ];
    }, [invoices, t]);

    const formatCurrency = (value) => {
        return value.toLocaleString(locale, { style: 'currency', currency: 'EUR' });
    };

    return (
        <div style={{ display: 'flex', gap: 12, height: '100%' }}>
            {/* Commandes vs Factures Bar Chart */}
            <div className="dx-card dx-widget shadow-sm rounded p-2" style={{ flex: 1, minWidth: 0, display: 'flex', flexDirection: 'column' }}>
                <h6 className="text-muted text-uppercase small fw-bold text-center mb-1" style={{ letterSpacing: '0.5px', flexShrink: 0 }}>
                    {t('chart_orders_vs_invoices')}
                </h6>
                <div style={{ flex: 1, minHeight: 0 }}>
                    {!hasOrders && !hasInvoices ? (
                        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100%', gap: 8, color: '#999' }}>
                            <i className="dx-icon-warning" style={{ fontSize: 32 }} />
                            <span>{t('no_orders_for_period')}</span>
                            <span>{t('no_invoices_for_period')}</span>
                        </div>
                    ) : !hasOrders || !hasInvoices ? (
                        <>
                            <Chart
                                dataSource={monthlyTotals}
                                height={hasOrders && hasInvoices ? '100%' : 'calc(100% - 28px)'}
                                onLegendClick={(e) => {
                                    const series = e.target;
                                    series.isVisible() ? series.hide() : series.show();
                                }}
                            >
                                <CommonSeriesSettings argumentField="month" type="bar" barPadding={0.2} />
                                <Series valueField="ordersTotal" name={t('chart_orders')} color="#3498db" />
                                <Series valueField="invoicesTotal" name={t('chart_invoices')} color="#e67e22" />
                                <ArgumentAxis />
                                <ValueAxis />
                                <Tooltip
                                    enabled={true}
                                    customizeTooltip={(arg) => ({
                                        text: `${arg.seriesName} — ${arg.argumentText}: ${formatCurrency(arg.value)}`,
                                    })}
                                />
                                <Legend visible={true} verticalAlignment="bottom" horizontalAlignment="center" />
                                <Animation enabled={true} duration={800} />
                            </Chart>
                            <div style={{ textAlign: 'center', color: '#999', fontSize: 12, padding: '2px 0' }}>
                                {!hasOrders && <span>{t('no_orders_for_period')}</span>}
                                {!hasInvoices && <span>{t('no_invoices_for_period')}</span>}
                            </div>
                        </>
                    ) : (
                        <Chart
                            dataSource={monthlyTotals}
                            height="100%"
                            onLegendClick={(e) => {
                                const series = e.target;
                                series.isVisible() ? series.hide() : series.show();
                            }}
                        >
                            <CommonSeriesSettings argumentField="month" type="bar" barPadding={0.2} />
                            <Series valueField="ordersTotal" name={t('chart_orders')} color="#3498db" />
                            <Series valueField="invoicesTotal" name={t('chart_invoices')} color="#e67e22" />
                            <ArgumentAxis />
                            <ValueAxis />
                            <Tooltip
                                enabled={true}
                                customizeTooltip={(arg) => ({
                                    text: `${arg.seriesName} — ${arg.argumentText}: ${formatCurrency(arg.value)}`,
                                })}
                            />
                            <Legend visible={true} verticalAlignment="bottom" horizontalAlignment="center" />
                            <Animation enabled={true} duration={800} />
                        </Chart>
                    )}
                </div>
            </div>

            {/* Subscription Analyse Doughnut */}
            <div className="dx-card dx-widget shadow-sm rounded p-2" style={{ flex: 1, minWidth: 0, display: 'flex', flexDirection: 'column' }}>
                <h6 className="text-muted text-uppercase small fw-bold text-center mb-1" style={{ letterSpacing: '0.5px', flexShrink: 0 }}>
                    {t('chart_subscription_expiry')}
                </h6>
                <div style={{ flex: 1, minHeight: 0 }}>
                    <PieChart
                        dataSource={subscriptionExpiryData}
                        type="doughnut"
                        height="100%"
                        customizePoint={(pointInfo) => {
                            const item = subscriptionExpiryData.find(d => d.category === pointInfo.argument);
                            return item ? { color: item.color } : {};
                        }}
                        innerRadius={0.6}

                        onPointClick={onPointClick}
                        onLegendClick={onLegendClick}
                    >
                        <PieSeries argumentField="category" valueField="count">
                            <PieLabel visible={true} position="outside" customizeText={(point) => `${point.argumentText}: ${point.valueText}`} />
                        </PieSeries>
                        <PieLegend
                            visible={true}
                            verticalAlignment="bottom"
                            horizontalAlignment="center"
                            itemTextPosition="right"
                            hoverMode="excludePoints"
                        />
                        <PieTooltip
                            enabled={true}
                            customizeTooltip={(arg) => ({
                                text: `${arg.argumentText}: ${arg.valueText} (${arg.percentText})`,
                            })}
                        />
                        <Animation enabled={true} duration={800} />
                    </PieChart>
                </div>
            </div>

            {/* Payé pas payé */}
            <div className="dx-card dx-widget shadow-sm rounded p-2" style={{ flex: 1, minWidth: 0, display: 'flex', flexDirection: 'column' }}>
                <h6 className="text-muted text-uppercase small fw-bold text-center mb-1" style={{ letterSpacing: '0.5px', flexShrink: 0 }}>
                    {t('chart_paid_vs_outstanding')}
                </h6>
                <div style={{ flex: 1, minHeight: 0 }}>
                    <Chart
                        dataSource={paidVsOutstandingData}
                        height="100%"
                        customizePoint={(pointInfo) => {
                            const item = paidVsOutstandingData.find((d) => d.category === pointInfo.argument);
                            return item ? { color: item.color } : {};
                        }}
                    >
                        <CommonSeriesSettings argumentField="category" type="bar" barPadding={0.4} />
                        <Series valueField="amount" name={t('amount')} />
                        <ArgumentAxis />
                        <ValueAxis />
                        <Tooltip
                            enabled={true}
                            customizeTooltip={(arg) => ({
                                text: `${arg.argumentText}: ${formatCurrency(arg.value)}`,
                            })}
                        />
                        <Legend visible={false} />
                        <Animation enabled={true} duration={800} />
                    </Chart>
                </div>
            </div>
        </div>
    );
}

export default DashboardCharts;
