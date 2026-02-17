import React, { useState } from 'react';
import logoReact from '../../assets/react.webp';
import Button from "../widgets/button/button";
import Input from "../widgets/input/input";
import { useTranslation } from "react-i18next";

function Login({ onLoginSuccess, onGoToRegister }) {
    const { t } = useTranslation();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const result = await response.json();
                if (result.loginOk && result.token){
                    localStorage.setItem('token', result.token);
                    localStorage.setItem('username', username);
                    localStorage.setItem('selectedNcs', result.customers[0].ncs);
                    onLoginSuccess(username);
                } else {
                    setError(t('error_incorrect_id'));
                }
            } else {
                setError(t('error_invalid_credentials'));
            }
        } catch (error) {
            setError(t('error_server_unavailable'));
        }
    };

    return (
        <div className="container-fluid vh-100 d-flex align-items-center justify-content-center dx-viewport"
             style={{ backgroundColor: '#f3f3f7' }}>

            <div className="dx-card dx-widget col-11 col-sm-8 col-md-6 col-lg-4 shadow-lg rounded overflow-hidden p-0 border-0 bg-white">

                <div className="text-center p-4 bg-dark">
                    <img id="logo" src={logoReact} alt="Logo" style={{ maxWidth: '60px', height: 'auto' }} />
                </div>

                <div className="p-4 text-center">
                    <h3 className="dx-theme-text-color mb-4 fw-light">{t('customer_area')}</h3>

                    <form onSubmit={handleSubmit} className="text-start">
                        <Input
                            id="username"
                            label={t('username')}
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />

                        <Input
                            id="password"
                            label={t('password')}
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />

                        {error && (
                            <div className="dx-message-error p-2 mb-3 rounded border text-danger small">
                                <i className="dx-icon-warning" style={{marginRight: '8px'}} />
                                {error}
                            </div>
                        )}

                        <div className="d-grid gap-2 mt-4">
                            <Button
                                label={t('login_button')}
                                type="submit"
                                className="dx-button-default w-100"
                            />
                            <Button
                                label={t('register_button')}
                                type="button"
                                className="dx-button-normal w-100"
                                onClick={onGoToRegister}
                            />
                        </div>
                    </form>

                    <div className="mt-4">
                        <a href="/forgotPassword" icon="small" className="dx-theme-accent-color text-decoration-none border-bottom small">
                            {t('forgot_password')}
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;