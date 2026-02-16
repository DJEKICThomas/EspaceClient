import React from 'react';
import Navigation from "../widgets/navigation/navigation";
import Header from "../widgets/header/header";
import { useTranslation } from "react-i18next";

function ProfilUser({ onLogout, userToEdit, onBack, onGoToHome, onGoToInvoices, onGoToOrders, onGoToOffers, onGoToHistoric }) {
    const { t, i18n } = useTranslation();

    const formatDate = (dateArray) => {
        if (!dateArray || !Array.isArray(dateArray) || dateArray.length < 3) return "";
        const [year, month, day] = dateArray;
        const date = new Date(year, month - 1, day);
        const locale = i18n.language === 'en' ? 'en-GB' : (i18n.language === 'nl' ? 'nl-BE' : 'fr-BE');
        return date.toLocaleDateString(locale);
    };

    return (
        <div className="profil-container container-fluid p-3" style={{ backgroundColor: '#f8f9fa', minHeight: '100vh' }}>
            <Header onLogout={onLogout} />
            <Navigation
                activeTab="Admin"
                onLogout={onLogout}
                onGoToHome={onGoToHome}
                onGoToInvoices={onGoToInvoices}
                onGoToOrders={onGoToOrders}
                onGoToOffers={onGoToOffers}
                onGoToHistoric={onGoToHistoric}
                onGoToAdmin={onBack}
            />

            <div className="bg-white p-4 rounded shadow-sm mt-3 mx-auto" style={{ maxWidth: '1200px', color: '#000' }}>

                <h2 className="mb-4" style={{ fontWeight: 'normal', color: '#000', display: 'block' }}>
                    <span style={{ borderBottom: '3px solid #d32f2f', paddingBottom: '5px'}}>{t('user_account')}</span>
                </h2>

                <div className="row mt-5" style={{ color: '#000' }}>
                    {/* Colonne de Gauche */}
                    <div className="col-md-6 px-4">
                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('email')} *</label>
                            <div className="input-group">
                                <input
                                    type="text"
                                    className="form-control bg-light"
                                    style={{ height: '45px', color: '#000', cursor:'not-allowed', borderRight: 'none' }}
                                    value={userToEdit?.email || ''}
                                    readOnly
                                />
                                <span className="input-group-text bg-light" style={{ borderLeft: 'none' }}>
                                    <i className="dx-icon-lock" style={{ color: '#004a99' }}/>
                                </span>
                            </div>
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('password')} *</label>
                            <input
                                type="password"
                                className="form-control bg-light"
                                style={{ height: '45px' }}
                                value="********"
                                readOnly
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('confirm_password')} *</label>
                            <input
                                type="password"
                                className="form-control bg-light"
                                style={{ height: '45px' }}
                                value="********"
                                readOnly
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('title_label')} *</label>
                            <select className="form-select bg-light" style={{ height: '45px' }} disabled>
                                <option>{userToEdit?.title || ''}</option>
                            </select>
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('lastname')} *</label>
                            <input
                                type="text"
                                className="form-control bg-light"
                                style={{ height: '45px', color: '#000' }}
                                value={userToEdit?.lastname || ''}
                                readOnly
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('firstname')} *</label>
                            <input
                                type="text"
                                className="form-control bg-light"
                                style={{ height: '45px', color: '#000' }}
                                value={userToEdit?.firstname || ''}
                                readOnly
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('profile')} *</label>
                            <select className="form-select bg-light" style={{ height: '45px' }} disabled>
                                <option>{userToEdit?.profile || ''}</option>
                            </select>
                        </div>
                    </div>

                    <div className="col-md-6 px-4">
                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('phone')}</label>
                            <input
                                type="text"
                                className="form-control bg-light"
                                style={{ height: '45px' }}
                                value={userToEdit?.telephone || ''}
                                readOnly
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('mobile')}</label>
                            <input
                                type="text"
                                className="form-control bg-light"
                                style={{ height: '45px' }}
                                value={userToEdit?.mobile || ''}
                                readOnly
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('fax')}</label>
                            <input
                                type="text"
                                className="form-control bg-light"
                                style={{ height: '45px' }}
                                value={userToEdit?.fax || ''}
                                readOnly
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('login_validity')}</label>
                            <div className="input-group">
                                <input
                                    type="text"
                                    className="form-control bg-light"
                                    style={{ height: '45px' }}
                                    value={formatDate(userToEdit?.usrexpdate)}
                                    readOnly
                                />
                                <span className="input-group-text bg-light">
                                    <i className="dx-icon-event"/>
                                </span>
                            </div>
                        </div>

                        <div className="mb-4">
                            <label className="form-label text-muted small">{t('function_label')}</label>
                            <input
                                type="text"
                                className="form-control bg-light"
                                style={{ height: '45px' }}
                                value={userToEdit?.function || ''}
                                readOnly
                            />
                        </div>

                        <div className="mb-4 mt-5">
                            <label className="form-label text-muted small d-block">{t('active_account')}</label>
                            <div className="form-check form-switch mt-2">
                                <input
                                    className="form-check-input"
                                    type="checkbox"
                                    style={{ transform: 'scale(1.5)', marginLeft: '0', cursor: 'default' }}
                                    checked={userToEdit?.active === 1}
                                    disabled
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <div className="d-flex justify-content-between mt-5 pt-3">
                    <button className="btn btn-danger px-4 py-2 fw-bold" style={{ borderRadius: '5px', backgroundColor: '#d32f2f', border: 'none' }}>
                        {t('confirm_button')}
                    </button>
                    <button className="btn btn-danger px-4 py-2 fw-bold" style={{ borderRadius: '5px', backgroundColor: '#d32f2f', border: 'none' }} onClick={onBack}>
                        {t('back_button')}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default ProfilUser;