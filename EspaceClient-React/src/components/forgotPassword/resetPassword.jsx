import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import logoReact from '../../assets/favicon.ico';
import Button from "../widgets/button/button";
import Input from "../widgets/input/input";
import { useTranslation } from "react-i18next";
import { SelectBox } from "devextreme-react";

function ResetPassword({onBackToLogin}){

    const {t, i18n} = useTranslation();
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token');

    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);
    const [tokenValid, setTokenValid] = useState(null);

    useEffect(() => {
        if(!token){
            setTokenValid(false);
            return;
        }

        const validateToken = async () => {
            try{
                const response = await fetch(
                    `${process.env.REACT_APP_API_URL}/users/password/validate?token=${token}`
                );
                setTokenValid(response.ok);
            } catch {
                setTokenValid(false);
            }
        };

        validateToken();
    }, [token]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if(password !== confirmPassword){
            setError(t('password_do_not_match'));
            return;
        }

        if(password.length < 8){
            setError(t('password_too_short'));
            return;
        }

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/users/password/reset`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ token, password, confirmPassword })
            });

            if (response.ok) {
                setSuccess(true);
            } else {
                const result = await response.json();
                setError(result.message || t('error_reset_password'));
            }
        } catch {
            setError(t('error_server_unavailable'));
        }
    };

    return (
        <div className="container-fluid vh-100 d-flex align-items-center justify-content-center dx-viewport dx-theme-background-color">
            <div className="col-11 col-sm-8 col-md-6 col-lg-4 bg-white shadow-lg rounded p-4">
                <div className="d-flex justify-content-end">
                    <SelectBox
                        items={[
                            { id: 'en', text: 'English' },
                            { id: 'fr', text: 'Français' },
                            { id: 'de', text: 'Deutsch' },
                            { id: 'nl', text: 'Nederlands' }
                        ]}
                        displayExpr="text"
                        valueExpr="id"
                        value={i18n.language.substring(0, 2)}
                        onValueChanged={(e) => i18n.changeLanguage(e.value)}
                        width={130}
                        stylingMode="filled"
                    />
                </div>
                <div className="text-center mb-4">
                    <img id="logo" src={logoReact} alt="Logo" style={{ maxWidth: '200px', height: 'auto' }} />
                </div>

                <div className="text-center">
                    <h3 className="dx-theme-text-color mb-4">{t('reset_password_title')}</h3>

                    {/* Token invalide ou expiré */}
                    {tokenValid === false && (
                        <div>
                            <p className="text-danger small">{t('reset_token_expired')}</p>
                            <div className="d-grid gap-2 mt-4">
                                <Button
                                    label={t('back_to_login')}
                                    type="button"
                                    className="dx-button-default w-100"
                                    onClick={onBackToLogin}
                                />
                            </div>
                        </div>
                    )}

                    {/* Chargement */}
                    {tokenValid === null && (
                        <p className="text-muted small">{t('loading')}</p>
                    )}

                    {/* Succès */}
                    {success && (
                        <div>
                            <p className="text-success small">{t('password_reset_success')}</p>
                            <div className="d-grid gap-2 mt-4">
                                <Button
                                    label={t('back_to_login')}
                                    type="button"
                                    className="dx-button-default w-100"
                                    onClick={onBackToLogin}
                                />
                            </div>
                        </div>
                    )}

                    {/* Formulaire */}
                    {tokenValid === true && !success && (
                        <form onSubmit={handleSubmit} className="text-start">
                            <Input
                                id="password"
                                label={t('new_password')}
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                            <Input
                                id="confirmPassword"
                                label={t('confirm_password')}
                                type="password"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                required
                            />

                            {error && (
                                <div className="dx-message-error p-2 mb-3 rounded border text-danger small">
                                    <i className="dx-icon-warning" style={{ marginRight: '8px' }} />
                                    {error}
                                </div>
                            )}

                            <div className="d-grid gap-2 mt-4">
                                <Button
                                    label={t('reset_password_button')}
                                    type="submit"
                                    className="dx-button-default w-100"
                                />
                            </div>
                        </form>
                    )}
                </div>
            </div>
        </div>
    );
}

export default ResetPassword;