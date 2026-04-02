import React, { useState } from 'react';
import logoReact from '../../assets/favicon.ico';
import Button from "../widgets/button/button";
import Input from "../widgets/input/input";
import { useTranslation } from "react-i18next";

function Login({ onLoginSuccess, onGoToRegister, onGoToForgotPassword }) {
    const { t } = useTranslation();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    // --- États pour le 2FA ---
    const [totpRequired, setTotpRequired] = useState(false);
    const [totpCode, setTotpCode] = useState('');
    const [pendingUserId, setPendingUserId] = useState(null);

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

                // CAS 1 : Le 2FA est activé → on passe à l'écran TOTP
                if (result.totpRequired) {
                    setPendingUserId(result.userId);
                    setTotpRequired(true);
                    return;
                }

                // CAS 2 : Pas de 2FA → login classique
                if (result.loginOk && result.token){
                    localStorage.setItem('token', result.token);
                    localStorage.setItem('username', username);
                    localStorage.setItem('userId', result.userId);

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

    // Soumet le code TOTP pour obtenir le JWT
    const handleTotpSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/totp/verify`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ userId: pendingUserId, code: totpCode })
            });

            if (response.ok) {
                const result = await response.json();
                if (result.loginOk && result.token) {
                    localStorage.setItem('token', result.token);
                    localStorage.setItem('username', username);
                    localStorage.setItem('userId', result.userId);

                    const allNcs = result.customers.map(c => c.ncs).join(',');
                    localStorage.setItem('selectedNcs', allNcs);

                    onLoginSuccess(username);
                } else {
                    setError(t('totp_invalid_code'));
                }
            } else {
                setError(t('totp_invalid_code'));
            }
        } catch (error) {
            setError(t('error_server_unavailable'));
        }
    };

    // Retour au formulaire login
    const handleBackToLogin = () => {
        setTotpRequired(false);
        setTotpCode('');
        setPendingUserId(null);
        setError('');
    };

    return (
        <div className="container-fluid vh-100 d-flex align-items-center justify-content-center dx-viewport dx-theme-background-color">

            <div className="col-11 col-sm-8 col-md-6 col-lg-4 bg-white shadow-lg rounded p-4">
                <div className="text-center mb-4">
                    <img id="logo" src={logoReact} alt="Logo" style={{ maxWidth: '200px', height: 'auto' }} />
                </div>

                <div className="text-center">
                    <h3 className="dx-theme-text-color mb-4">{t('customer_area')}</h3>

                    {/* ========== ÉCRAN TOTP (étape 2) ========== */}
                    {totpRequired ? (
                        <form onSubmit={handleTotpSubmit} className="text-start">
                            <p className="text-muted small mb-3">{t('totp_enter_code')}</p>

                            <Input
                                id="totpCode"
                                label={t('totp_code_label')}
                                type="text"
                                value={totpCode}
                                onChange={(e) => setTotpCode(e.target.value)}
                                maxLength={6}
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
                                    label={t('totp_verify_button')}
                                    type="submit"
                                    className="dx-button-default w-100"
                                />
                                <Button
                                    label={t('back_button')}
                                    type="button"
                                    className="dx-button-normal w-100"
                                    onClick={handleBackToLogin}
                                />
                            </div>
                        </form>
                    ) : (
                        /* ========== ÉCRAN LOGIN (étape 1) ========== */
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
                    )}

                    <div className="mt-4 text-center">
                        <span
                            onClick={onGoToForgotPassword }
                            className="dx-theme-accent-color text-decoration-none border-bottom small"
                            style={{ cursor:'pointer' }}
                        >
                            {t('forgot_password')}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;
