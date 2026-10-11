import React, { useState } from 'react';
import '../../app/App.css';

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
                        display: 'flex',
                        alignItems: 'center'
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
                            padding: '10px 45px 10px 12px',
                            borderRadius: '4px',
                            border: 'none',
                            outline: 'none',
                            width: '100%',
                            fontSize: '1rem',
                            background: 'transparent'
                        }}
                    />

                    {type === 'password' && (
                        <i
                            className={`fi ${showPassword ? 'fi-rs-eye' : 'fi-rs-crossed-eye'}`}
                            onClick={() => setShowPassword(!showPassword)}
                            style={{
                                position: 'absolute',
                                right: '12px',
                                cursor: 'pointer',
                                color: '#000',
                                fontSize: '1.1rem',
                                zIndex: 10,
                                display: 'inline-block'
                            }}
                        />
                    )}
                </div>
            </div>
        </div>
    );
}

export default Input;