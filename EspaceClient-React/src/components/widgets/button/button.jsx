import React from 'react';

function Button({
    label,
    onClick,
    className = "btn-primary",
    type = "button"
}){
    return(
        <button
            type={type}
            className={`btn ${className}`}
            onClick={onClick}
        >
            {label}
        </button>
    );
}
export default Button;