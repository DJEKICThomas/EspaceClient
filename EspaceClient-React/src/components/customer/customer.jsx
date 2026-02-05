import React from 'react';
import './customer.css';
import logoReact from '../../assets/react.webp';
import Button from "../widgets/button/button";
import Link from "../widgets/link/link";

function Customer({onLogout, username, onGoToInvoices}){
    return(
        <div className="customer-container">
            <nav className="customer-navbar">
                <div className="navbar-left">
                    <img src={logoReact} alt="Logo React" className="nav-logo"/>
                </div>
                <div className="nav-links d-flex gap-2">
                    {/*Mettre les liens pour les différentes pages*/}
                    <Link text="Mes Factures" className="text-dark" onClick={onGoToInvoices}/>
                    <Link text="Mon Historique" className="text-dark" onClick={() => console.log("Vers mon historique")}/>
                </div>
                <div className="navbar-right">
                    <span className="user-name">Admin</span>
                   <Button
                        label="Déconnexion"
                        onClick={onLogout}
                        className="btn-outline-danger"
                   />
                </div>
            </nav>
            <div className="customer-content">
                <h2>Bienvenue dans votre espace client REACT {username}</h2>
                <hr />
                {/* Ajout des contrats etc plus tard */}
                <p>Sélectionnez un client parmis cette liste</p>
            </div>
        </div>
    )
}

export default Customer;