import React from 'react';
import '../../../css/dx.EGlobal.css';

function Button({
                    label,
                    onClick,
                    className = "dx-button-default",
                    type = "button"
                }){
    return(
        <button
            type={type}
            /* dx-button : base du composant
               dx-button-mode-contained : bouton plein
               dx-widget : applique la police du thème
            */
            className={`dx-button dx-button-mode-contained dx-widget ${className}`}
            onClick={onClick}
            style={{ cursor: 'pointer' }}
        >
            <div className="dx-button-content">
                <span className="dx-button-text">{label}</span>
            </div>
        </button>
    );
}

export default Button;