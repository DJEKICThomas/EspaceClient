import React from 'react';
import { useTranslation } from "react-i18next";

function Header({ onLogout }) {
    const { t, i18n } = useTranslation();

    const changeLanguage = (lng) => {
        i18n.changeLanguage(lng);
    };

    return (
        /* dx-card : On l'utilise ici pour que le header ait le même style d'ombre et de bordure que le reste.
           flex-wrap : Crucial pour le responsive (les boutons passeront à la ligne sur petit écran).
        */
        <div className="dx-card dx-widget d-flex flex-wrap justify-content-between align-items-center mb-4 p-2 bg-white shadow-sm border-0">

            {/* Sélecteur de langue */}
            <div className="d-flex flex-wrap gap-1 mb-2 mb-md-0">
                {['fr', 'en', 'de', 'nl'].map((lng) => {
                    const isSelected = i18n.language.startsWith(lng);

                    return (
                        <div
                            key={lng}
                            onClick={() => changeLanguage(lng)}
                            /* On utilise dx-button-default : il sera Orange (ou Bleu) si sélectionné.
                               On utilise dx-button-normal : il sera gris discret si non sélectionné.
                            */
                            className={`dx-button dx-button-mode-${isSelected ? 'contained' : 'text'} ${isSelected ? 'dx-button-default' : 'dx-button-normal'} dx-widget`}
                            style={{
                                fontSize: '0.75rem',
                                padding: '4px 10px',
                                textTransform: 'uppercase',
                                cursor: 'pointer',
                                transition: 'all 0.2s'
                            }}
                        >
                            <div className="dx-button-content">
                                <span className="dx-button-text" style={{ fontWeight: isSelected ? 'bold' : '500' }}>
                                    {lng}
                                </span>
                            </div>
                        </div>
                    );
                })}
            </div>

            {/* Bouton Logout */}
            <div
                className="dx-button dx-button-mode-outlined dx-button-danger dx-widget px-3"
                onClick={onLogout}
                style={{ cursor: 'pointer' }}
            >
                <div className="dx-button-content">
                    <i className="dx-icon-runner" style={{ marginRight: '8px', fontSize: '16px' }} />
                    <span className="dx-button-text">{t('logout')}</span>
                </div>
            </div>
        </div>
    );
}

export default Header;