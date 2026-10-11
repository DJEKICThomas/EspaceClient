import React, { useState } from 'react';
import '../../css/dx.EGlobal.css';
import logo from "../../assets/favicon.ico";
import { useTranslation } from "react-i18next";
import Input from "../widgets/input/input";
import notify from "devextreme/ui/notify";

function Register({ onBack }) {
    const { t } = useTranslation();

    const [formData, setFormData] = useState({
        username: '',
        firstname: '',
        lastname: '',
        birthdate: '',
        company: '',
        password: '',
        confirmPassword: ''
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
            notify("Les mots de passe ne correspondent pas !", "error", 3000);
            return;
        }
        notify("Demande d'inscription enregistrée (Simulation)", "success", 2000);
        onBack();
    };

    return (
        <div className="container-fluid d-flex align-items-center justify-content-center min-vh-100 dx-viewport dx-theme-background-color">

            <div className="dx-card dx-widget shadow-lg p-4" style={{ width: '100%', maxWidth: '500px', borderRadius: '12px' }}>

                <div className="text-center mb-4">
                    <img src={logo} alt="Logo React" style={{ maxWidth: '60px' }}/>
                    <h3 className="dx-theme-text-color mt-3 fw-light">
                        <span style={{ borderBottom: '3px solid var(--dx-theme-accent-color)', paddingBottom: '5px' }}>
                            Inscription
                        </span>
                    </h3>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <Input
                            id="username"
                            label="Nom d'utilisateur"
                            type="text"
                            value={formData.username}
                            onChange={(e) => setFormData({...formData, username: e.target.value})}
                            required
                        />
                    </div>

                    <div className="row g-2 mb-3">
                        <div className="col-12 col-sm-6">
                            <Input
                                id="firstname"
                                label="Prénom"
                                type="text"
                                value={formData.firstname}
                                onChange={(e) => setFormData({...formData, firstname: e.target.value})}
                                required
                            />
                        </div>
                        <div className="col-12 col-sm-6">
                            <Input
                                id="lastname"
                                label="Nom"
                                type="text"
                                value={formData.lastname}
                                onChange={(e) => setFormData({...formData, lastname: e.target.value})}
                                required
                            />
                        </div>
                    </div>

                    <div className="mb-3">
                        <Input
                            id="birthdate"
                            label="Date de naissance"
                            type="date"
                            value={formData.birthdate}
                            onChange={(e) => setFormData({...formData, birthdate: e.target.value})}
                            required
                        />
                    </div>

                    <div className="mb-3">
                        <Input
                            id="company"
                            label="Entreprise"
                            type="text"
                            value={formData.company}
                            onChange={(e) => setFormData({...formData, company: e.target.value})}
                            required
                        />
                    </div>

                    <div className="mb-3">
                        <Input
                            id="password"
                            label="Mot de passe"
                            type="password"
                            value={formData.password}
                            onChange={(e) => setFormData({...formData, password: e.target.value})}
                            required
                        />
                    </div>

                    <div className="mb-4">
                        <Input
                            id="confirmPassword"
                            label="Vérification mot de passe"
                            type="password"
                            value={formData.confirmPassword}
                            onChange={(e) => setFormData({...formData, confirmPassword: e.target.value})}
                            required
                        />
                    </div>

                    <div className="d-grid gap-3">
                        <div
                            className="dx-button dx-button-mode-contained dx-button-default dx-widget p-2 px-4"
                            //onClick={handleSubmit}
                            style={{ cursor: 'pointer', borderRadius: '5px' }}
                        >
                            <span className="dx-button-text fw-bold">{t('confirm_button')}</span>
                        </div>

                        <div
                            className="dx-button dx-button-mode-contained dx-widget p-2 px-4"
                            onClick={onBack}
                            style={{ cursor: 'pointer', borderRadius: '5px' }}
                        >
                            <span className="dx-button-text fw-bold">{t('back_button')}</span>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Register;