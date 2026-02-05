import React, { useState } from 'react';
import './register.css'; // Utilise le CSS ci-dessous
import logoReact from '../../assets/react.webp';
import Input from "../widgets/input/input";
import Button from "../widgets/button/button";
import Link from "../widgets/link/link";

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
        <div className="container-fluid">
            <div className="login-form register-mode">
                <div>
                    <img id="logo" src={logoReact} alt="Logo React"/>
                </div>
                <div className="login-form-content">
                    <h3>Inscription</h3>

                    <form onSubmit={handleSubmit}>
                        <Input
                            id="username"
                            label="Nom d'utilisateur"
                            type="text"
                            value={formData.username}
                            onChange={(e) => setFormData({...formData, username: e.target.value})}
                            required
                        />

                        {/* Conteneur flex pour mettre Prénom et Nom sur la même ligne */}
                        <div style={{ display: 'flex', gap: '15px' }}>
                            <Input
                                id="firstname"
                                label="Prénom"
                                type="text"
                                value={formData.firstname}
                                onChange={(e) => setFormData({...formData, firstname: e.target.value})}
                                required
                            />
                            <Input
                                id="lastname"
                                label="Nom"
                                type="text"
                                value={formData.lastname}
                                onChange={(e) => setFormData({...formData, lastname: e.target.value})}
                                required
                            />
                        </div>

                        <Input
                            id="birthdate"
                            label="Date de naissance"
                            type="date"
                            value={formData.birthdate}
                            onChange={(e) => setFormData({...formData, birthdate: e.target.value})}
                            required
                        />

                        <Input
                            id="company"
                            label="Entreprise"
                            type="text"
                            value={formData.company}
                            onChange={(e) => setFormData({...formData, company: e.target.value})}
                            required
                        />

                        <Input
                            id="password"
                            label="Mot de passe"
                            type="password"
                            value={formData.password}
                            onChange={(e) => setFormData({...formData, password: e.target.value})}
                            required
                        />

                        <Input
                            id="confirmPassword"
                            label="Vérification mot de passe"
                            type="password"
                            value={formData.confirmPassword}
                            onChange={(e) => setFormData({...formData, confirmPassword: e.target.value})}
                            required
                        />

                        <div className="login-actions">
                            <Button label="S'inscrire" type="submit" className="btn-success"/>
                        </div>
                    </form>
                    <div style={{ marginTop: '20px' }}>
                        <Link
                            text="Déjà inscrit ?"
                            onClick={onBack}
                            style={{ color: 'white' }}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Register;