import React, { useState } from 'react';
import logoReact from '../../assets/favicon.ico';
import Button from '../widgets/button/button';
import Input from '../widgets/input/input';
import { useTranslation } from "react-i18next";
import { SelectBox } from "devextreme-react";

function ForgotPassword({ onBackToLogin }){

    const { t, i18n } = useTranslation();
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [submitted, setSubmitted] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/users/password`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email })
            });

            if (response.status === 500) {
                setError(t('error_server_unavailable'));
                return;
            }

            setSubmitted(true);

        } catch (err) {
            setError(t('error_server_unvailable'));
        }
    };

    return(
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
                    <h3 className="dx-theme-text-color mb-4">{t('forgot_password_title')}</h3>

                    {submitted ? (
                        <div>
                            <p className="text-success small">{t('forgot_password_email_sent')}</p>
                            <div className="d-grid gap-2 mt-4">
                                <Button
                                    label={t('back_to_login')}
                                    type="button"
                                    className="dx-button-default w-100"
                                    onClick={onBackToLogin}
                                />
                            </div>
                        </div>
                    ) : (
                        <form onSubmit={handleSubmit} className="text-start">
                            <p className="text-muted small mb-3">{t('forgot_password_description')}</p>
                            <Input
                                id="email"
                                label={t('email')}
                                type="text"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
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
                                    label={t('send_reset_link')}
                                    type="submit"
                                    className="dx-button-default w-100"
                                />
                                <Button
                                    label={t('back_to_login')}
                                    type="button"
                                    className="dx-button-normal w-100"
                                    onClick={onBackToLogin}
                                />
                            </div>
                        </form>
                    )}
                </div>
            </div>
        </div>
    );
}

export default ForgotPassword;