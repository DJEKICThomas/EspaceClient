import React, {useState} from 'react';
import './login.css';
import logoReact from '../../assets/react.webp';

function Login({onLoginSuccess}){
    //State : stocker username et pwd
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    //Réecriture de la fonction submit
    const handleSubmit = async (e) => {
        e.preventDefault();

        const loginData = {
            user: username,
            pwd: password
        };

        console.log("Tentative de connexion pour : ", username);

        try{
            /* mettre l'API */

            if (loginData.user === "thomas" && loginData.pwd === "1234")
            {
                setError('');
                onLoginSuccess(username); //redirection vers costumer
            } else{
                setError("Nom d'utilisateur ou mot de passe incorrect.");
            }
        } catch (error){
            console.error("Erreur lors de la connexion : ", error);
            setError("Le serveur est indisponible pour le moment.");
        }
    };

    console.log("Pseudo en direct :", username);
    console.log("Mot de passe en direct :", password);

    return(
        <div className="container-fluid">
            <div className="login-form">
                <div>
                    <img id="logo" src={logoReact} alt="Logo React" />
                </div>
                <div className="login-form-content">
                    <h3>Espace client</h3>

                    <form id="loginForm" onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label>Nom d'utilisateur</label>
                            <input
                                id="username"
                                type="text"
                                className="form-control"
                                placeholder=""
                                required
                                value={username}
                                onChange={(e) => setUsername(e.target.value)} // On capture ce que l'utilisateur tape
                            />
                        </div>
                        <div className="form-group">
                            <label>Mot de passe</label>
                            <input
                                id="password"
                                type="password"
                                className="form-control"
                                placeholder=""
                                required
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>

                        {error && (
                            <div className="error-message">
                                {error}
                            </div>
                        )}

                        <div className="login-actions">
                            <button type="submit" className="btn btn-success">
                                Connexion
                            </button>

                            <button type="button" className="btn btn-secondary btn-register"
                                    onClick={() => window.location.href = '/userCreation'}> S'inscrire
                            </button>
                        </div>
                    </form>

                    {/* Ton lien du JSP */}
                    <a href="/forgotPassword" style={{ color: 'white', display: 'block', marginTop: '15px' }}>
                        Mot de passe oublié
                    </a>
                </div>
            </div>
        </div>
    );
}

export default Login;