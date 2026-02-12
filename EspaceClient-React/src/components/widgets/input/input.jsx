import React, { useState } from 'react';
import { Eye, EyeSlash } from 'react-bootstrap-icons';

function Input({ id, label, type, value, onChange, required }) {
    const [showPassword, setShowPassword] = useState(false);
    const inputType = type === 'password' ? (showPassword ? 'text' : 'password') : type;

    return (
        <div className="form-group mb-3">
            <label htmlFor={id} className="text-white">{label}</label>

            <div className="position-relative d-flex align-items-center">
                <input
                    id={id}
                    type={inputType}
                    className="form-control"
                    value={value}
                    onChange={onChange}
                    required={required}
                    style={{ paddingRight: '45px' }}
                />

                {type === 'password' && (
                    <span
                        onClick={() => setShowPassword(!showPassword)}
                        style={{
                            position: 'absolute',
                            right: '15px',
                            cursor: 'pointer',
                            color: '#666',
                            display: 'flex',
                            alignItems: 'center',
                            zIndex: 10
                        }}
                    >
                    {showPassword ? <EyeSlash size={22} /> : <Eye size={22} />}
                </span>
                )}
            </div>
        </div>
    );
}

export default Input;