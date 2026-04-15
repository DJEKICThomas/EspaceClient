import React from 'react';
import { useTheme } from '../../../context/ThemeContext.jsx';
import '../../../css/dx.EGlobal.css';

const SummaryCard = ({ title, value, subValue, details, status, onClick, compact, icon }) => {
    const accentColor = status?.color || '#f39c12';
    const { isDarkMode } = useTheme();
    const borderColor = isDarkMode ? '#ffffff' : '#000000';

    return (
        <div
            className="summary-card-wrapper"
            onClick={onClick}
            style={{
                flex: 1,
                display: 'flex',
                flexDirection: 'column',
                borderLeft: `8px solid ${accentColor}`,
                borderTop: `1px solid ${borderColor}`,
                borderRight: `1px solid ${borderColor}`,
                borderBottom: `1px solid ${borderColor}`,
                borderRadius: '4px',
                marginTop: compact ? '10px' : '15px',
                cursor: onClick ? 'pointer' : 'default'
            }}
        >
            <div className={`dx-card dx-widget shadow-sm ${compact ? 'p-2' : 'p-3'}`}
                 style={{
                     flex: 1,
                     display: 'flex',
                     flexDirection: 'column',
                     border: 'none',
                     borderRadius: '0 4px 4px 0',
                     backgroundColor: isDarkMode ? '#1e1e1e' : '#ffffff',
                     color: isDarkMode ? '#f0f0f0' : '#333'
                 }}
            >
                <div className="d-flex justify-content-between align-items-start mb-2">
                    <h6 className="dx-theme-text-color-muted text-uppercase small fw-bold m-0 d-flex align-items-center">
                        {icon && <i className={icon} style={{ marginRight: '6px' }} />}
                        {title}
                    </h6>
                    {status && status.label && (
                        <span className="badge" style={{
                            backgroundColor: status.bgColor || 'transparent',
                            color: status.color,
                            fontWeight: 'bold',
                            fontSize: '0.75rem'
                        }}>
                            {status.label}
                        </span>
                    )}
                </div>

                <div className={compact ? 'my-1' : 'my-2'}>
                    {compact
                        ? <h4 className="dx-theme-accent-color fw-bold m-0">{value}</h4>
                        : <h2 className="dx-theme-accent-color fw-bold m-0">{value}</h2>
                    }
                    {subValue && (
                        <div className="dx-theme-text-color-muted small">
                            {subValue}
                        </div>
                    )}
                </div>

                {details && details.length > 0 && (
                    <div className={`${compact ? 'mt-2 pt-1' : 'mt-3 pt-2'} border-top dx-theme-border-color`}>
                        {details.map((detail, index) => (
                            <div key={index} className="d-flex justify-content-between small py-1">
                                <span className="dx-theme-text-color-muted">{detail.label}</span>
                                <span className="fw-bold dx-theme-text-color">{detail.value}</span>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default SummaryCard;
