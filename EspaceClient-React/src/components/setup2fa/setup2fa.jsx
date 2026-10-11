import React, { useState, useEffect } from 'react';
import { useTranslation } from "react-i18next";
import notify from "devextreme/ui/notify";
import { useTheme } from '../../context/ThemeContext.jsx';
import logo from "../../assets/favicon.ico";

/**
 * Page de setup 2FA forcée.
 * Affichée juste après un login réussi quand l'utilisateur n'a pas encore activé son 2FA.
 * L'utilisateur ne peut PAS quitter cette page sans activer son 2FA — la seule sortie
 * autorisée est le bouton de logout.
 */
function Setup2Fa({ onSetupSuccess, onLogout }) {
    const { t } = useTranslation();
    const { isDarkMode } = useTheme();

    const [qrCodeDataUri, setQrCodeDataUri] = useState(null);
    const [totpCode, setTotpCode] = useState('');
    const [loading, setLoading] = useState(false);

    // Au montage, demande automatiquement la génération du QR code
    useEffect(() => {
        generateQrCode();
    }, []);

    const generateQrCode = async () => {
        setLoading(true);
        try {
            const token = localStorage.getItem('token');
            const userId = localStorage.getItem('userId');
            const response = await fetch(`${process.env.REACT_APP_API_URL}/totp/setup`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    userId: Number(userId),
                    companyName: localStorage.getItem('selectedCompanyName')
                })
            });

            if (response.ok) {
                const result = await response.json();
                if (result.success) {
                    setQrCodeDataUri(result.qrCodeDataUri);
                } else {
                    notify(result.message || t('totp_setup_error'), "error", 3000);
                }
            }
        } catch (error) {
            notify(t('error_server_unavailable'), "error", 3000);
        }
        setLoading(false);
    };

    const handleConfirm = async () => {
        if (totpCode.length !== 6) return;
        setLoading(true);
        try {
            const token = localStorage.getItem('token');
            const userId = localStorage.getItem('userId');
            const response = await fetch(`${process.env.REACT_APP_API_URL}/totp/verify`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({ userId: Number(userId), code: totpCode })
            });

            if (response.ok) {
                const result = await response.json();
                if (result.loginOk) {
                    notify(t('totp_activated_success'), "success", 2500);
                    // Le 2FA est désormais activé, on enchaîne le flow login normal
                    onSetupSuccess();
                } else {
                    notify(t('totp_invalid_code'), "error", 3000);
                }
            } else {
                notify(t('totp_invalid_code'), "error", 3000);
            }
        } catch (error) {
            notify(t('error_server_unavailable'), "error", 3000);
        }
        setLoading(false);
    };

    const cardBg = isDarkMode ? '#494957' : '#ffffff';
    const cardText = isDarkMode ? '#d4d4d4' : '#000';
    const titleText = isDarkMode ? '#ffffff' : '#000';

    return (
        <div className="container-fluid vh-100 d-flex align-items-center justify-content-center dx-viewport dx-theme-background-color">
            <div
                className="col-11 col-sm-9 col-md-7 col-lg-5 shadow-lg rounded p-4"
                style={{ backgroundColor: cardBg, color: cardText }}
            >
                <div className="text-center mb-3">
                    <img src={logo} alt="Logo" style={{ maxWidth: '120px', height: 'auto' }} />
                </div>

                <h3 className="mb-3 text-center" style={{ fontWeight: 'normal', color: titleText }}>
                    <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '5px' }}>
                        {t('force_2fa_title')}
                    </span>
                </h3>

                <p className="text-muted small text-center mb-4">{t('force_2fa_description')}</p>

                {qrCodeDataUri ? (
                    <>
                        <p className="small fw-bold mb-2">{t('totp_scan_qr')}</p>
                        <div className="text-center mb-3">
                            <img src={qrCodeDataUri} alt="QR Code TOTP" style={{ maxWidth: '200px' }} />
                        </div>

                        <p className="small text-muted">{t('totp_enter_code_confirm')}</p>
                        <div className="d-flex gap-2 align-items-end mb-3">
                            <input
                                type="text"
                                className="form-control"
                                placeholder="000000"
                                maxLength={6}
                                value={totpCode}
                                onChange={(e) => setTotpCode(e.target.value.replace(/\D/g, ''))}
                                style={{ maxWidth: '160px', fontSize: '1.2rem', letterSpacing: '0.3rem', textAlign: 'center' }}
                            />
                            <button
                                className="btn btn-success"
                                onClick={handleConfirm}
                                disabled={loading || totpCode.length !== 6}
                            >
                                {t('confirm_button')}
                            </button>
                        </div>
                    </>
                ) : (
                    <p className="text-center text-muted small">{t('loading')}</p>
                )}

                <div className="d-flex justify-content-center mt-3">
                    <button
                        className="btn btn-outline-danger btn-sm"
                        onClick={onLogout}
                    >
                        <i className="fi fi-bs-exit me-2" style={{ verticalAlign: 'middle' }} />
                        {t('logout') || 'Déconnexion'}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Setup2Fa;
