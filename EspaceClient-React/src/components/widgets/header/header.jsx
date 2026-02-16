import React from 'react';
import Button from '../button/button';
import { useTranslation } from "react-i18next";

function Header({ onLogout }) {
    const { t, i18n } = useTranslation();

    const changeLanguage = (lng) => {
        i18n.changeLanguage(lng);
    };

    return (
        <div className="d-flex justify-content-between align-items-center mb-4 p-2 bg-white rounded shadow-sm">
            <div className="d-flex gap-1">
                {['fr', 'en', 'de', 'nl'].map((lng) => {
                    const isSelected = i18n.language.startsWith(lng);

                    return (
                        <button
                            key={lng}
                            onClick={() => changeLanguage(lng)}
                            className="btn btn-sm"
                            style={{
                                fontSize: '0.8rem',
                                fontWeight: 'bold',
                                padding: '8px 15px',
                                border: 'none',
                                borderRadius: '4px',
                                textTransform: 'uppercase',
                                transition: '0.3s',
                                backgroundColor: isSelected ? '#ff9800' : 'transparent',
                                color: isSelected ? '#ffffff' : '#555555',
                                cursor: 'pointer'
                            }}
                        >
                            {lng}
                        </button>
                    );
                })}
            </div>

            <Button
                label={t('logout')}
                onClick={onLogout}
                className="btn-outline-danger px-4"
                style={{ width: 'auto' }}
            />
        </div>
    );
}

export default Header;