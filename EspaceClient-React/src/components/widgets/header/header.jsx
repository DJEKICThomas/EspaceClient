import React from 'react';
import Button from '../button/button';

function Header({ onLogout}) {
    return (
        <div className="d-flex justify-content-between align-items-center mb-3">
            <Button
                label="Déconnexion"
                onClick={onLogout}
                className="btn-outline-danger px-4"
                style={{ width: 'auto' }}
            />
        </div>
    );
}

export default Header;