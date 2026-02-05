import React from 'react';

function Input({
    id,
    label,
    type = "text",
    value,
    onChange,
    placeholder = "",
    required = false
}){
    return (
        <div className="form-group mb-3">
            {label && <label className="text-white mb-1">{label}</label>}
            <input
                id={id}
                type={type}
                className="form-control"
                placeholder={placeholder}
                required={required}
                value={value}
                onChange={onChange}
            />
        </div>
    );
}

export default Input;