import React, { useState } from 'react';
import '../../../css/dx.EGlobal.css';

function Button({
                    label,
                    onClick,
                    className = "dx-button-default",
                    type = "button"
                }){
    const [isHover, setIsHover] = useState(false);
    const isPrimary = className.includes('dx-button-default');

    const primaryStyle = {
        backgroundColor: isHover ? '#1f838f' : '#28a1b1',
        color: '#ffffff',
        border: 'none',
        borderRadius: '6px',
        padding: '10px 20px',
        cursor: 'pointer',
        transition: 'background-color 0.2s ease'
    };

    const secondaryStyle = {
        backgroundColor: isHover ? 'rgba(61, 170, 185, 0.1)' : '#ffffff',
        color: '#28a1b1',
        border: '1px solid #28a1b1',
        borderRadius: '6px',
        padding: '10px 20px',
        cursor: 'pointer',
        transition: 'all 0.2s ease'
    };

    const buttonStyle = isPrimary ? primaryStyle : secondaryStyle;

    return(
        <button
            type={type}
            className={`dx-button dx-button-mode-contained dx-widget ${className}`}
            onClick={onClick}
            onMouseEnter={() => setIsHover(true)}
            onMouseLeave={() => setIsHover(false)}
            style={buttonStyle}
        >
            <div className="dx-button-content">
                <span className="dx-button-text fw-bold text-uppercase" style={{ fontSize: '0.85rem', letterSpacing: '0.5px' }}>
                    {label}
                </span>
            </div>
        </button>
    );
}

export default Button;