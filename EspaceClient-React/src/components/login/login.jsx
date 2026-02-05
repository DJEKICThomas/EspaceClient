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
            const response = await fetch('http://localhost:8080/EspaceClient-api/login', {
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
        <div className="container-fluid">
            <div className="login-form">
                <div>
                    <img id="logo" src={logoReact} alt="Logo React" />
                </div>
                <div className="login-form-content">
                    <h3>Espace client</h3>

                    <form id="loginForm" onSubmit={handleSubmit}>
                        <Input
                            id="username"
                            label="Nom d'utilisateur"
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                        <Input
                            id="password"
                            label="Mot de passe"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />

                        {error && <div className="error-message">{error}</div>}

                        <div className="login-actions">
                            <Button label="Connexion" type="submit" className="btn-success" />
                            <Button label="S'inscrire" type="button" className="btn-secondary btn-register" onClick={onGoToRegister}/>
                        </div>
                    </form>

                    <a href="/forgotPassword" style={{ color: 'white', display: 'block', marginTop: '15px' }}>
                        Mot de passe oublié
                    </a>
                </div>
            </div>
        </div>
    );
}

export default Login;