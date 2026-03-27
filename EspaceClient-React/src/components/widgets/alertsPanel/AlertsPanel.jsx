import React from 'react';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../../../context/ThemeContext.jsx';
import useAlerts from './useAlerts.jsx';

const SEVERITY_STYLES = {
    urgent: { bg: '#fdedec', bgDark: '#3b1a1a', border: '#e74c3c' },
    warning: { bg: '#fef9e7', bgDark: '#3b351a', border: '#f39c12' },
};

function AlertsPanel({ invoices, subscriptions, onGoToInvoices, onGoToSubscription }) {
    const { t } = useTranslation();
    const { isDarkMode } = useTheme();
    const alerts = useAlerts(invoices, subscriptions);

    if (alerts.length === 0) return null;

    const handleAction = (action) => {
        if (action === 'invoices') onGoToInvoices?.();
        if (action === 'subscriptions') onGoToSubscription?.();
    };

    return (
        <div className="dx-card dx-widget shadow-sm rounded p-3" style={{
            backgroundColor: isDarkMode ? '#1e1e1e' : '#ffffff',
            border: isDarkMode ? '1px solid #444' : '1px solid #e0e0e0',
        }}>
            <div className="d-flex align-items-center mb-2">
                <span style={{ fontSize: '1.1rem', marginRight: 8 }}>&#9889;</span>
                <h6 className="text-uppercase small fw-bold m-0" style={{
                    color: isDarkMode ? '#f0f0f0' : '#333',
                    letterSpacing: '0.5px',
                }}>
                    {t('alerts_title', { count: alerts.length })}
                </h6>
            </div>

            {alerts.map((alert, index) => {
                const style = SEVERITY_STYLES[alert.severity];
                return (
                    <div
                        key={alert.id}
                        className="d-flex align-items-center justify-content-between px-3 py-2"
                        style={{
                            backgroundColor: isDarkMode ? style.bgDark : style.bg,
                            borderLeft: `4px solid ${style.border}`,
                            borderRadius: 4,
                            marginBottom: index < alerts.length - 1 ? 8 : 0,
                            cursor: 'pointer',
                        }}
                        onClick={() => handleAction(alert.action)}
                    >
                        <div>
                            <div style={{
                                fontWeight: 600,
                                fontSize: '0.875rem',
                                color: isDarkMode ? '#f0f0f0' : '#333',
                            }}>
                                <span style={{ marginRight: 6 }}>{alert.icon}</span>
                                {alert.title}
                            </div>
                            <div style={{
                                fontSize: '0.75rem',
                                color: isDarkMode ? '#aaa' : '#777',
                                marginTop: 2,
                            }}>
                                {alert.subtitle}
                            </div>
                        </div>
                        <span style={{
                            fontSize: '0.75rem',
                            color: style.border,
                            fontWeight: 600,
                            whiteSpace: 'nowrap',
                            marginLeft: 16,
                        }}>
                            {t('alert_view')} &#8250;
                        </span>
                    </div>
                );
            })}
        </div>
    );
}

export default AlertsPanel;
