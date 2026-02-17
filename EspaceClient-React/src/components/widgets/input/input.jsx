import React, { useState } from 'react';

function Input({ id, label, type, value, onChange, required }) {
    const [showPassword, setShowPassword] = useState(false);
    const inputType = type === 'password' ? (showPassword ? 'text' : 'password') : type;

    return (
        <div className="mb-3">
            <label
                htmlFor={id}
                style={{
                    display: 'block',
                    textAlign: 'left',
                    marginBottom: '6px',
                    fontSize: '0.9rem',
                    fontWeight: 'bold',
                    color: '#333'
                }}
            >
                {label}
            </label>

            <div className="dx-widget dx-textbox dx-texteditor">
                <div
                    className="dx-texteditor-container"
                    style={{
                        position: 'relative',
                        border: '1px solid #ddd',
                        borderRadius: '4px',
                        backgroundColor: 'white',
                        display: 'flex'
                    }}
                >
                    <input
                        id={id}
                        type={inputType}
                        className="dx-texteditor-input"
                        value={value}
                        onChange={onChange}
                        required={required}
                        style={{
                            padding: '10px 12px',
                            borderRadius: '4px',
                            border: 'none',
                            outline: 'none',
                            width: '100%',
                            fontSize: '1rem'
                        }}
                    />

                    {type === 'password' && (
                        <div
                            onClick={() => setShowPassword(!showPassword)}
                            style={{
                                position: 'absolute',
                                right: '10px',
                                top: '50%',
                                transform: 'translateY(-50%)',
                                cursor: 'pointer',
                                zIndex: 10,
                                display: 'flex',
                                alignItems: 'center',
                                background: 'white',
                                paddingLeft: '5px'
                            }}
                        >
                            <i className={`dx-icon ${showPassword ? 'dx-icon-eyeopen' : 'dx-icon-eyeclose'}`}
                               style={{ fontSize: '18px', color: '#666' }} />
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Input;