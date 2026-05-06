import React, { useState } from 'react';
import Header from "../widgets/header/header";
import { Switch } from 'devextreme-react/switch';
import { useTranslation } from "react-i18next";
import { useTheme } from '../../context/ThemeContext.jsx';
import notify from "devextreme/ui/notify";
import { validatePassword } from "../../security/passwordValidator";

function Settings({ onLogout, onGoToCustomerSelection, onGoToCompanySelection, onGoToHome, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToHistoric, onGoToAdmin, onGoToDocument, onGoToSubscription, onGoToSettings }) {
    const { t } = useTranslation();
    const { isDarkMode, toggleTheme } = useTheme();

    // --- États 2FA ---
    const [twoFaEnabled, setTwoFaEnabled] = useState(false);   // Le 2FA est-il actif pour cet utilisateur ?
    const [qrCodeDataUri, setQrCodeDataUri] = useState(null);   // Le QR code retourné par le backend
    const [totpCode, setTotpCode] = useState('');                // Le code à 6 chiffres saisi
    const [showSetup, setShowSetup] = useState(false);           // Afficher l'écran de setup (QR code)
    const [loading, setLoading] = useState(false);

    // --- États changement de mot de passe ---
    const [currentPassword, setCurrentPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [showCurrentPwd, setShowCurrentPwd] = useState(false);
    const [showNewPwd, setShowNewPwd] = useState(false);
    const [showConfirmPwd, setShowConfirmPwd] = useState(false);
    const [pwdLoading, setPwdLoading] = useState(false);

    // Au montage, vérifier si le 2FA est déjà activé pour cet utilisateur
    React.useEffect(() => {
        checkTwoFaStatus();
    }, []);

    const checkTwoFaStatus = async () => {
        try {
            const token = localStorage.getItem('token');
            const userId = localStorage.getItem('userId');
            const response = await fetch(`${process.env.REACT_APP_API_URL}/totp/status/${userId}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (response.ok) {
                const result = await response.json();
                setTwoFaEnabled(result.success);
            }
        } catch (error) {
            console.error("Erreur lors de la vérification du statut 2FA", error);
        }
    };

    // Étape 1 : Demander la génération du QR code
    const handleSetup = async () => {
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
                }),
            });

            if (response.ok) {
                const result = await response.json();
                if (result.success) {
                    setQrCodeDataUri(result.qrCodeDataUri);
                    setShowSetup(true);
                } else {
                    notify(result.message || t('totp_setup_error'), "error", 3000);
                }
            }
        } catch (error) {
            notify(t('error_server_unavailable'), "error", 3000);
        }
        setLoading(false);
    };

    // Étape 2 : Confirmer le setup en vérifiant le premier code TOTP
    const handleConfirmSetup = async () => {
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
                    setTwoFaEnabled(true);
                    setShowSetup(false);
                    setQrCodeDataUri(null);
                    setTotpCode('');
                    notify(t('totp_activated_success'), "success", 3000);
                } else {
                    notify(t('totp_invalid_code'), "error", 3000);
                }
            }
        } catch (error) {
            notify(t('error_server_unavailable'), "error", 3000);
        }
        setLoading(false);
    };

    // Annuler le setup en cours
    const handleCancel = () => {
        setShowSetup(false);
        setQrCodeDataUri(null);
        setTotpCode('');
    };

    // --- Changement de mot de passe ---
    const handleChangePassword = async () => {
        // Validation côté front avant l'appel
        if(!currentPassword){
            notify(t('current_password_required'), "error", 3000);
            return;
        }
        if(newPassword !== confirmPassword){
            notify(t('password_do_not_match'), "error", 3000);
            return;
        }
        const errors = validatePassword(newPassword, t);
        if(errors.length > 0){
            notify(errors.join(' / '), "error", 4000);
            return;
        }

        setPwdLoading(true);
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(`${process.env.REACT_APP_API_URL}/users/me/password`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({ currentPassword, newPassword, confirmPassword })
            });

            const result = await response.json();
            if (response.ok) {
                notify(result.message || t('password_changed_success'), "success", 3000);
                setCurrentPassword('');
                setNewPassword('');
                setConfirmPassword('');
            } else {
                notify(result.message || t('password_change_error'), "error", 4000);
            }
        } catch (error) {
            notify(t('error_server_unavailable'), "error", 3000);
        }
        setPwdLoading(false);
    };

    return (
        <div className="settings-container container-fluid dx-viewport dx-theme-background-color"
             style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>

            <Header
                onLogout={onLogout}
                activeTab="Settings"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onGoToAdmin}
                onGoToDocument={onGoToDocument}
                onGoToSubscription={onGoToSubscription}
                onGoToSettings={onGoToSettings}
                onGoToCustomerSelection={onGoToCustomerSelection}
                onGoToCompanySelection={onGoToCompanySelection}
            />

            <div className="dx-card dx-widget p-4 shadow-sm mt-3 mx-auto" style={{ maxWidth: '800px', color: isDarkMode ? '#d4d4d4' : '#000', backgroundColor: isDarkMode ? '#494957' : undefined, width: '100%' }}>
                <h2 className="mb-4" style={{ fontWeight: 'normal', color: isDarkMode ? '#ffffff' : '#000', display: 'block' }}>
                    <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '5px' }}>
                        {t('settings_title')}
                    </span>
                </h2>

                {/* ========== SECTION DARK MODE ========== */}
                <div className="mb-4 p-3" style={{ borderBottom: `1px solid ${isDarkMode ? '#555' : '#eee'}` }}>
                    <div className="d-flex align-items-center justify-content-between">
                        <div>
                            <h5 className="mb-1" style={{ fontWeight: 500 }}>
                                <i className="fi fi-rs-moon me-2" style={{ verticalAlign: 'middle' }} />
                                {t('settings_appearance')}
                            </h5>
                            <p className="text-muted small mb-0">{isDarkMode ? t('dark_mode') : t('light_mode')}</p>
                        </div>
                        <Switch
                            value={isDarkMode}
                            onValueChanged={toggleTheme}
                            switchedOnText=""
                            switchedOffText=""
                            width={50}
                            height={26}
                        />
                    </div>
                </div>

                {/* ========== SECTION CHANGEMENT DE MOT DE PASSE ========== */}
                <div className="mb-4 p-3" style={{ borderBottom: `1px solid ${isDarkMode ? '#555' : '#eee'}` }}>
                    <h5 className="mb-3" style={{ fontWeight: 500 }}>
                        <i className="fi fi-rs-key me-2" style={{ verticalAlign: 'middle' }} />
                        {t('change_password_title')}
                    </h5>
                    <p className="text-muted small mb-3">{t('change_password_description')}</p>

                    <div className="mb-2" style={{ maxWidth: '400px' }}>
                        <label className="form-label text-muted small">{t('current_password')} *</label>
                        <div className="input-group">
                            <input
                                type={showCurrentPwd ? "text" : "password"}
                                className="form-control"
                                value={currentPassword}
                                onChange={(e) => setCurrentPassword(e.target.value)}
                                autoComplete="current-password"
                                placeholder="***********"
                            />
                            <span
                                className="input-group-text bg-light"
                                style={{ cursor: 'pointer', borderLeft: 'none' }}
                                onClick={() => setShowCurrentPwd(!showCurrentPwd)}
                            >
                                <i
                                    className={`fi ${showCurrentPwd ? 'fi-rs-eye' : 'fi-rs-crossed-eye'}`}
                                    style={{ color: 'var(--dx-theme-accent-color)', fontSize: '1.1rem' }}
                                />
                            </span>
                        </div>
                    </div>

                    <div className="mb-2" style={{ maxWidth: '400px' }}>
                        <label className="form-label text-muted small">{t('new_password')} *</label>
                        <div className="input-group">
                            <input
                                type={showNewPwd ? "text" : "password"}
                                className="form-control"
                                value={newPassword}
                                onChange={(e) => setNewPassword(e.target.value)}
                                autoComplete="new-password"
                                placeholder="***********"
                            />
                            <span
                                className="input-group-text bg-light"
                                style={{ cursor: 'pointer', borderLeft: 'none' }}
                                onClick={() => setShowNewPwd(!showNewPwd)}
                            >
                                <i
                                    className={`fi ${showNewPwd ? 'fi-rs-eye' : 'fi-rs-crossed-eye'}`}
                                    style={{ color: 'var(--dx-theme-accent-color)', fontSize: '1.1rem' }}
                                />
                            </span>
                        </div>
                    </div>

                    <div className="mb-3" style={{ maxWidth: '400px' }}>
                        <label className="form-label text-muted small">{t('confirm_password')} *</label>
                        <div className="input-group">
                            <input
                                type={showConfirmPwd ? "text" : "password"}
                                className="form-control"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                autoComplete="new-password"
                                placeholder="***********"
                            />
                            <span
                                className="input-group-text bg-light"
                                style={{ cursor: 'pointer', borderLeft: 'none' }}
                                onClick={() => setShowConfirmPwd(!showConfirmPwd)}
                            >
                                <i
                                    className={`fi ${showConfirmPwd ? 'fi-rs-eye' : 'fi-rs-crossed-eye'}`}
                                    style={{ color: 'var(--dx-theme-accent-color)', fontSize: '1.1rem' }}
                                />
                            </span>
                        </div>
                    </div>

                    <button
                        className="btn btn-outline-success"
                        onClick={handleChangePassword}
                        disabled={pwdLoading || !currentPassword || !newPassword || !confirmPassword}
                    >
                        <i className="fi fi-rs-shield-check me-2" style={{ verticalAlign: 'middle' }} />
                        {t('change_password_button')}
                    </button>
                </div>

                {/* ========== SECTION 2FA ========== */}
                <div className="p-3">
                    <h5 className="mb-3" style={{ fontWeight: 500 }}>
                        <i className="fi fi-rs-lock me-2" style={{ verticalAlign: 'middle' }} />
                        {t('settings_2fa_title')}
                    </h5>
                    <p className="text-muted small mb-3">{t('settings_2fa_description')}</p>

                    {/* État : 2FA désactivé, pas en train de configurer */}
                    {!twoFaEnabled && !showSetup && (
                        <button
                            className="btn btn-outline-success"
                            onClick={handleSetup}
                            disabled={loading}
                        >
                            <i className="fi fi-rs-shield-check me-2" style={{ verticalAlign: 'middle' }} />
                            {t('totp_enable_button')}
                        </button>
                    )}

                    {/* État : Setup en cours → affichage du QR code + input confirmation */}
                    {showSetup && qrCodeDataUri && (
                        <div className="mt-3">
                            <p className="small fw-bold">{t('totp_scan_qr')}</p>
                            <div className="text-center mb-3">
                                <img src={qrCodeDataUri} alt="QR Code TOTP" style={{ maxWidth: '200px' }} />
                            </div>
                            <p className="small text-muted">{t('totp_enter_code_confirm')}</p>
                            <div className="d-flex gap-2 align-items-end">
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="000000"
                                    maxLength={6}
                                    value={totpCode}
                                    onChange={(e) => setTotpCode(e.target.value.replace(/\D/g, ''))}
                                    style={{ maxWidth: '150px', fontSize: '1.2rem', letterSpacing: '0.3rem', textAlign: 'center' }}
                                />
                                <button className="btn btn-success" onClick={handleConfirmSetup} disabled={loading || totpCode.length !== 6}>
                                    {t('confirm_button')}
                                </button>
                                <button className="btn btn-outline-secondary" onClick={handleCancel}>
                                    {t('totp_cancel')}
                                </button>
                            </div>
                        </div>
                    )}

                    {/* État : 2FA activé — la désactivation n'est pas autorisée car le 2FA est obligatoire */}
                    {twoFaEnabled && (
                        <div>
                            <div className="d-flex align-items-center gap-2 mb-2">
                                <span className="badge bg-success">{t('totp_status_active')}</span>
                            </div>
                            <p className="text-muted small mb-0">
                                <i className="fi fi-rs-info me-1" style={{ verticalAlign: 'middle' }} />
                                {t('totp_mandatory_info')}
                            </p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Settings;
