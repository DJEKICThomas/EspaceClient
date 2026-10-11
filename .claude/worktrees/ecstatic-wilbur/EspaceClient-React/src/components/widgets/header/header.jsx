import React, { useState } from 'react';
import { useTranslation } from "react-i18next";
import { SelectBox } from 'devextreme-react/select-box';
import Navigation from '../navigation/navigation';
import { useTheme } from '../../../context/ThemeContext.jsx';
import logo from "../../../assets/favicon.ico";

function Header({ onLogout, onGoToSettings, ...navProps }) {
    const { t, i18n } = useTranslation();
    const { isDarkMode } = useTheme();
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const languages = [
        { id: 'en', text: 'English' },
        { id: 'fr', text: 'Français' },
        { id: 'de', text: 'Deutsch' },
        { id: 'nl', text: 'Nederlands' }
    ];

    // Couleurs conditionnelles header
    const headerBg = isDarkMode ? '#000000' : '#ffffff';
    const headerText = isDarkMode ? '#ffffff' : '#333';
    const headerBorder = isDarkMode ? '#333' : '#ddd';

    return (
        <>
            <div className={`header-bar d-flex align-items-center p-0 shadow-sm mb-0 ${isDarkMode ? 'header-dark' : 'header-light'}`}
                 style={{
                     height: '55px',
                     position: 'relative',
                     zIndex: 1000,
                     backgroundColor: headerBg,
                     borderBottom: `1px solid ${headerBorder}`,
                     color: headerText
                 }}>

                {/* Burger menu - mobile only */}
                <div className="d-lg-none ps-3">
                    <i
                        className={isMenuOpen ? "fi fi-bs-cross" : "fi fi-bs-menu-burger"}
                        style={{ fontSize: '1.5rem', cursor: 'pointer', verticalAlign: 'middle', color: headerText }}
                        onClick={() => setIsMenuOpen(!isMenuOpen)}
                    />
                </div>

                {/* Logo */}
                <div className="text-center p-4">
                    <a href="https://www.altiore.be/fr/" target="_blank" rel="noreferrer">
                        <img id="logo" src={logo} alt="Logo" style={{ maxWidth: '100px', height: 'auto' }} />
                    </a>
                </div>

                {/* Navigation - desktop */}
                <div className="flex-grow-1 h-100 d-none d-lg-block" style={{ overflow: 'visible' }}>
                    <Navigation {...navProps} isDarkMode={isDarkMode} />
                </div>

                {/* Right block */}
                <div className="d-flex align-items-center gap-2 px-3 ms-auto"
                     style={{
                         width: 'auto',
                         minWidth: '120px',
                         justifyContent: 'flex-end'
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

                    {/* Icône Paramètres → ouvre la page Settings */}
                    <i
                        className="fi fi-rs-settings"
                        onClick={onGoToSettings}
                        style={{
                            fontSize: '1.3rem',
                            cursor: 'pointer',
                            verticalAlign: 'middle',
                            padding: '6px',
                            color: headerText
                        }}
                    />

                    {/* Logout button — TOUJOURS rouge */}
                    <div
                        className="logout-btn"
                        onClick={onLogout}
                        style={{
                            cursor: 'pointer',
                            borderRadius: '4px',
                            border: '1px solid #dc3545',
                            color: '#dc3545',
                            width: 'auto',
                            minWidth: '40px',
                            flex: 'none',
                            padding: '4px 8px'
                        }}
                    >
                        <div className="d-flex align-items-center justify-content-center">
                            <i className="fi fi-bs-exit" style={{ fontSize: '1.1rem', verticalAlign: 'middle', color: '#dc3545' }} />
                            <span className="d-none d-md-inline-block ms-2" style={{
                                minWidth: '110px',
                                textAlign: 'center',
                                whiteSpace: 'nowrap',
                                color: '#dc3545',
                                fontWeight: 500
                            }}>
                                {t('logout')}
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            {/* Mobile menu */}
            {isMenuOpen && (
                <div className="d-lg-none shadow-sm p-0 mb-3 border-0 animate__animated animate__fadeInDown"
                     style={{ position: 'relative', top: '-5px', zIndex: 999 }}>
                    <Navigation
                        {...navProps}
                        isMobile={true}
                        isDarkMode={isDarkMode}
                        closeMenu={() => setIsMenuOpen(false)}
                    />
                </div>
            )}
        </>
    );
}

export default Header;
