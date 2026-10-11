import React from 'react';
import '../../css/dx.EGlobal.css';

function IconButton({
                        iconName,
                        color,
                        onClick,
                        title
                    }){
    return(
        <div
            onClick={onClick}
            title={title}
            /* dx-button-mode-text : pas de fond, juste l'icône
               dx-button-has-icon : ajuste les marges pour les icônes
            */
            className="dx-button dx-button-mode-text dx-widget dx-button-has-icon"
            style={{
                cursor: 'pointer',
                padding: '8px',
                display: 'inline-block'
            }}
        >
            <div className="dx-button-content">
                <i
                    className={`dx-icon dx-icon-${iconName}`}
                    style={{
                        fontSize: '20px',
                        color: color || 'inherit' // Utilise la couleur passée ou celle du thème
                    }}
                />
            </div>
        </div>
    );
}

export default IconButton;