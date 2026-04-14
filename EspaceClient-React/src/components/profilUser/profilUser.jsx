import React, { useState, useEffect } from 'react';
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";
import notify from "devextreme/ui/notify";

function ProfilUser({ onLogout, userToEdit, onGoToCustomerSelection, onGoToCompanySelection, onBack, onGoToHome, onGoToSubscription, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToHistoric, onGoToDocument, onGoToSettings }) {
    const { t } = useTranslation();

    const [formData, setFormData] = useState(() => {
        if (userToEdit) {
            localStorage.setItem('userToEdit_Backup', JSON.stringify(userToEdit));
            return { ...userToEdit };
        }
        const saved = localStorage.getItem('userToEdit_Backup');
        return saved ? JSON.parse(saved) : {};
    });

    useEffect(() => {
        if (userToEdit) {
            localStorage.setItem('userToEdit_Backup', JSON.stringify(userToEdit));
            setFormData({ ...userToEdit });
        }
    }, [userToEdit]);

    const handleTextChange = (e) => {
        const nomDuChamp = e.target.name;
        const valeurTapee = e.target.value;
        const nouvelleCopie = { ...formData };
        nouvelleCopie[nomDuChamp] = valeurTapee;
        setFormData(nouvelleCopie);
    };

    const handleActiveToggle = (e) => {
        const nouvelleCopie = { ...formData };
        nouvelleCopie.active = e.target.checked ? 1 : 0;
        setFormData(nouvelleCopie);
    };

    const handleConfirm = async () => {
        const token = localStorage.getItem('token');
        const dataToSend = { ...formData };
        const originalUser = userToEdit || JSON.parse(localStorage.getItem('userToEdit_Backup'));

        dataToSend.password = originalUser.password;
        dataToSend.confirmPassword = originalUser.password;

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(dataToSend)
            });

            if (response.ok) {
                notify("Utilisateur mis à jour avec succès", "success", 2000);
                onBack();
            } else {
                notify("Erreur lors de la mise à jour", "error", 3000);
            }
        } catch (error) {
            notify("Erreur réseau", "error", 3000);
        }
    };

    return (
        <div className="profil-container container-fluid dx-viewport dx-theme-background-color"
             style={{
                 minHeight: '100vh',
                 display: 'flex',
                 flexDirection: 'column'
             }}>
            <Header
                onLogout={onLogout}
                activeTab="Profil User"
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onBack}
                onGoToDocument={onGoToDocument}
                onGoToSubscription={onGoToSubscription}
                onGoToSettings={onGoToSettings}
                onGoToCustomerSelection={onGoToCustomerSelection}
                onGoToCompanySelection={onGoToCompanySelection}
            />

            <div className="dx-card dx-widget p-4 shadow-sm mt-3 mx-auto" style={{ maxWidth: '1200px', color: '#000'}}>
                <h2 className="mb-4" style={{ fontWeight: 'normal', color: '#000', display: 'block' }}>
                    <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '5px'}}>{t('user_account')}</span>
                </h2>

                <div className="row mt-5" style={{ color: '#000' }}>
                    <div className="col-md-6 px-4">
                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('email')} *</label>
                            <div className="input-group">
                                <input type="text" className="form-control bg-light" value={formData.email || ''} readOnly style={{cursor:'not-allowed'}} />
                                <span className="input-group-text bg-light" style={{ borderLeft: 'none' }}>
                                    {/* Icône DX avec la couleur thématique */}
                                    <i className="dx-icon-lock" style={{ color: 'var(--dx-theme-accent-color)' }}/>
                                </span>
                            </div>
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('password')} *</label>
                            <input type="password" className="form-control bg-light" value="********" readOnly />
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('confirm_password')} *</label>
                            <input type="password" className="form-control bg-light" value="********" readOnly />
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('title_label')} *</label>
                            <select name="title" className="form-select" value={formData.title || ''} onChange={handleTextChange}>
                                <option value="MR">MR</option>
                                <option value="mlle">MLLE</option>
                                <option value="MME">MME</option>
                            </select>
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('lastname')} *</label>
                            <input type="text" name="lastname" className="form-control" value={formData.lastname || ''} onChange={handleTextChange} />
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('firstname')} *</label>
                            <input type="text" name="firstname" className="form-control" value={formData.firstname || ''} onChange={handleTextChange} />
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('profile')} *</label>
                            <select name="profile" className="form-select" value={formData.profile || ''} onChange={handleTextChange}>
                                <option value="UTILISATEUR">UTILISATEUR</option>
                                <option value="ADMINISTRATEUR">ADMINISTRATEUR</option>
                                <option value="CLIENT">CLIENT</option>
                            </select>
                        </div>
                    </div>

                    <div className="col-md-6 px-4">
                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('phone')}</label>
                            <input type="text" name="telephone" className="form-control" value={formData.telephone || ''} onChange={handleTextChange} />
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('mobile')}</label>
                            <input type="text" name="mobile" className="form-control" value={formData.mobile || ''} onChange={handleTextChange} />
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('fax')}</label>
                            <input type="text" name="fax" className="form-control" value={formData.fax || ''} onChange={handleTextChange} />
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('login_validity')}</label>
                            <div className="input-group">
                                <input
                                    type="date"
                                    name="usrexpdate"
                                    className="form-control"
                                    value={formData.usrexpdate ?
                                        (Array.isArray(formData.usrexpdate) ?
                                            `${formData.usrexpdate[0]}-${String(formData.usrexpdate[1]).padStart(2, '0')}-${String(formData.usrexpdate[2]).padStart(2, '0')}`
                                            : formData.usrexpdate)
                                        : ''}
                                    onChange={handleTextChange}
                                />
                            </div>
                        </div>

                        <div className="mb-2">
                            <label className="form-label text-muted small">{t('function_label')}</label>
                            <input type="text" name="function" className="form-control" value={formData.function || ''} onChange={handleTextChange} />
                        </div>

                        <div className="mb-4 mt-5">
                            <label className="form-label text-muted small d-block">{t('active_account')}</label>
                            <div className="form-check form-switch mt-2">
                                <input
                                    className="form-check-input"
                                    type="checkbox"
                                    style={{ transform: 'scale(1.5)', marginLeft: '0', cursor: 'pointer' }}
                                    checked={formData.active === 1}
                                    onChange={handleActiveToggle}
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <div className="d-flex justify-content-between mt-5 pt-3">
                    <div
                        className="dx-button dx-button-mode-contained dx-button-default dx-widget p-2 px-4"
                        onClick={handleConfirm}
                        style={{ cursor: 'pointer', borderRadius: '5px' }}
                    >
                        <span className="dx-button-text fw-bold" style={{width:'80px'}}>{t('confirm_button')}</span>
                    </div>

                    <div
                        className="dx-button dx-button-mode-contained dx-widget p-2 px-4"
                        onClick={onBack}
                        style={{ cursor: 'pointer', borderRadius: '5px' }}
                    >
                        <span className="dx-button-text fw-bold" style={{width:'60px'}}>{t('back_button')}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProfilUser;