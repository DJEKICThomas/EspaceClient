import React, { useState } from 'react';
import { useTranslation } from "react-i18next";
import { SelectBox } from 'devextreme-react/select-box';
import Navigation from '../navigation/navigation';
import logo from "../../../assets/favicon.ico";

function Header({ onLogout, ...navProps }) {
    const { t, i18n } = useTranslation();
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const languages = [
        { id: 'en', text: 'English' },
        { id: 'fr', text: 'Français' },
        { id: 'de', text: 'Deutsch' },
        { id: 'nl', text: 'Nederlands' }
    ];

    return (
        <>
            {/* Barre principale avec hauteur fixe */}
            <div className="dx-card d-flex align-items-center p-0 bg-white shadow-sm border-0 mb-0"
                 style={{ height: '55px', position: 'relative', zIndex: 1000 }}>

                {/* Bouton menu visible sur mobile uniquement */}
                <div className="d-lg-none ps-3">
                    <i
                        className={isMenuOpen ? "fi fi-bs-cross" : "fi fi-bs-menu-burger"}
                        style={{ fontSize: '1.5rem', cursor: 'pointer', verticalAlign: 'middle' }}
                        onClick={() => setIsMenuOpen(!isMenuOpen)}
                    />
                </div>

                {/* Logo */}
                <div className="text-center p-4" >
                    <a href="https://www.altiore.be/fr/" target="_blank" rel="noreferrer" >
                        <img id="logo" src={logo} alt="Logo" style={{ maxWidth: '100px', height: 'auto' }} />
                    </a>
                </div>

                {/* Navigation horizontale : visible sur Desktop uniquement */}
                <div className="flex-grow-1 h-100 d-none d-lg-block" style={{ overflow: 'hidden' }}>
                    <Navigation {...navProps} />
                </div>

                {/* Bloc de droite : Langues et Logout */}
                <div className="d-flex align-items-center gap-2 px-3 ms-auto"
                     style={{
                         width: 'auto',
                         minWidth: '120px',
                         justifyContent: 'flex-end',
                         backgroundColor: 'white'
                     }}>

                    <SelectBox
                        items={languages}
                        displayExpr="text"
                        valueExpr="id"
                        value={i18n.language.substring(0, 2)}
                        onValueChanged={(e) => i18n.changeLanguage(e.value)}
                        width={110}
                        stylingMode="filled"
                    />

                    <div
                        className="dx-button dx-button-mode-outlined dx-button-danger"
                        onClick={onLogout}
                        style={{
                            cursor: 'pointer',
                            borderRadius: '4px',
                            width: 'auto',
                            minWidth: '40px',
                            flex: 'none'
                        }}
                    >
                        <div className="dx-button-content d-flex align-items-center justify-content-center">
                            <i className="fi fi-bs-exit" style={{ fontSize: '1.1rem', verticalAlign: 'middle' }} />
                            <span className="dx-button-text d-none d-md-inline-block ms-2" style={{
                                minWidth: '110px',
                                textAlign: 'center',
                                whiteSpace: 'nowrap'
                            }}>
                                {t('logout')}
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            {/* Menu Mobile : S'affiche sous le header quand isMenuOpen est true */}
            {isMenuOpen && (
                <div className="d-lg-none dx-card shadow-sm p-0 mb-3 bg-white border-0 animate__animated animate__fadeInDown"
                     style={{
                         position: 'relative',
                         top: '-5px',
                         zIndex: 999,
                         borderTop: '1px solid #eee'
                     }}>
                    <Navigation
                        {...navProps}
                        isMobile={true}
                        closeMenu={() => setIsMenuOpen(false)}
                    />
                </div>
            )}
        </>
    );
}

export default Header;
