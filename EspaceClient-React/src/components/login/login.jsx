import React, { useState } from 'react';
import logoReact from '../../assets/favicon.ico';
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

                    const allNcs = result.customers.map(c => c.ncs).join(',');
                    localStorage.setItem('selectedNcs', allNcs);

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
        <div className="container-fluid vh-100 d-flex align-items-center justify-content-center dx-viewport dx-theme-background-color">

            <div className="col-11 col-sm-8 col-md-6 col-lg-4 bg-white shadow-lg rounded p-4">
                <div className="text-center mb-4">
                    <img id="logo" src={logoReact} alt="Logo" style={{ maxWidth: '200px', height: 'auto' }} />
                </div>

                <div className="text-center">
                    <h3 className="dx-theme-text-color mb-4">{t('customer_area')}</h3>

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

                    <div className="mt-4 text-center">
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