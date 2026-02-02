import React from 'react';
import './customer.css';
import logoReact from '../../assets/react.webp';

function Customer({onLogout, username}){
    return(
        <div className="customer-container">
            <nav className="customer-navbar">
                <div className="navbar-left">
                    <img src={logoReact} alt="Logo React" className="nav-logo"/>
                </div>

                <div className="navbar-right">
                    <span className="user-name">Admin</span>
                    <button className="btn btn-link logout-button" onClick={onLogout}>
                        <i className="bi bi-person-circle"></i>
                        <span> Déconnexion</span>
                    </button>
                </div>
            </nav>
            <div className="customer-content">
                <h2>Bienvenue dans votre espace client REACT {username}</h2>
                <hr />
                {/* Ajout des contrats etc plus tard */}
                <p>Sélectionnez un client pour commencer.</p>
            </div>
        </div>
    )
}

export default Customer;