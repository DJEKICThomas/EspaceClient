import React from 'react';

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
            className="icon-button-eglobal"
            style={{
                //border: `1px solid ${color}`,
                color: color,
                cursor: 'pointer',
            }}
        >
            <i className={`dx-icon-${iconName}`} style={{ fontSize: '20px' }}></i>
        </div>
    );
}

export default IconButton;