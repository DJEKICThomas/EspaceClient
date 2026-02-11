import React, { useState } from 'react';
import './login.css';
import logoReact from '../../assets/react.webp';
import Button from "../widgets/button/button";
import Input from "../widgets/input/input";

function Login({ onLoginSuccess, onGoToRegister }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

            if (response.ok) {
                const result = await response.json();

                if (result.loginOk && result.token){
                    localStorage.setItem('token', result.token);
                    localStorage.setItem('username', username);
                    localStorage.setItem('selectedNcs', result.customers[0].ncs);
                    onLoginSuccess(username);
                } else {
                    setError("Identifiant incorrect");
                }
            } else {
                setError("Nom d'utilisateur ou mot de passe incorrect.");
            }
        } catch (error) {
            console.error("Erreur lors de la connexion : ", error);
            setError("Le serveur est indisponible");
        }
    };

    return (
        <div className="container-fluid vh-100 d-flex align-items-center justify-content-center bg-light">
            {/* On limite la largeur sur PC (col-md-4) mais on prend tout sur mobile (col-11) */}
            <div className="login-card col-11 col-sm-8 col-md-6 col-lg-4 shadow-lg rounded overflow-hidden bg-white">

                <div className="login-header text-center p-4 bg-dark">
                    <img id="logo" src={logoReact} alt="Logo React" style={{ maxWidth: '100px', height: 'auto' }} />
                </div>

                <div className="login-form-content p-4 text-center" style={{ backgroundColor: '#e74c3c' }}>
                    <h3 className="text-white mb-4">Espace client</h3>

                    <form id="loginForm" onSubmit={handleSubmit} className="text-start">
                        <div className="mb-3">
                            <Input
                                id="username"
                                label="Nom d'utilisateur"
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                        </div>
                        <div className="mb-4">
                            <Input
                                id="password"
                                label="Mot de passe"
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>

                        {error && <div className="alert alert-danger p-2 small">{error}</div>}

                        <div className="d-flex justify-content-center gap-2 mt-4">
                            <div className="login-actions">
                                <Button label="Connexion" type="submit" className="btn-success w-100" />
                                <Button label="S'inscrire" type="button" className="btn-secondary w-100" onClick={onGoToRegister}/>
                            </div>
                        </div>
                    </form>

                    <div className="mt-4">
                        <a href="/forgotPassword" size="small" className="text-white text-decoration-none border-bottom">
                            Mot de passe oublié ?
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;