import React, { useState } from 'react';
import './register.css';
import logoReact from '../../assets/react.webp';
import Input from "../widgets/input/input";
import Button from "../widgets/button/button";

function Register({ onBack }) {
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
            alert("Les mots de passe ne correspondent pas !");
            return;
        }
        alert("Demande d'inscription enregistrée (Simulation)");
        onBack();
    };

    return (
        <div className="container-fluid d-flex align-items-center justify-content-center min-vh-100 bg-light">
            <div className="register-mode shadow-lg">
                <div className="text-center mb-4 p-3 bg-dark rounded">
                    <img id="logo" src={logoReact} alt="Logo React" style={{ maxWidth: '80px' }}/>
                </div>

                <div className="login-form-content">
                    <h3 className="text-white text-center mb-4">Inscription</h3>

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

                        {/* Utilisation de row/col Bootstrap pour le responsive Prénom/Nom */}
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

                        <div className="d-grid gap-2">
                            <Button label="S'inscrire" type="submit" className="btn-success w-100"/>
                            <Button label="Retour vers connexion" type="button" className="btn-secondary w-100"  onClick={onBack} />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default Register;