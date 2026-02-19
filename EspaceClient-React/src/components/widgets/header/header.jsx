import React from 'react';
import { useTranslation } from "react-i18next";
import { SelectBox } from 'devextreme-react/select-box';
import Navigation from '../navigation/navigation';

function Header({ onLogout, ...navProps }) {
    const { t, i18n } = useTranslation();

    const languages = [
        { id: 'en', text: 'English' },
        { id: 'fr', text: 'Français' },
        { id: 'de', text: 'Deutsch' },
        { id: 'nl', text: 'Nederlands' }
    ];

    return (
        <div className="dx-card d-flex align-items-center p-0 bg-white shadow-sm border-0 mb-3"
             style={{ height: '55px', overflow: 'hidden' }}>

            <div className="flex-grow-1 h-100" style={{ overflow: 'hidden' }}>
                <Navigation {...navProps} />
            </div>

            <div className="d-flex align-items-center gap-2 px-3"
                 style={{
                     width: '320px',
                     minWidth: '320px',
                     maxWidth: '320px',
                     justifyContent: 'flex-end',
                     backgroundColor: 'white',
                     zIndex: 1
                 }}>

                <SelectBox
                    items={languages}
                    displayExpr="text"
                    valueExpr="id"
                    value={i18n.language.substring(0, 2)}
                    onValueChanged={(e) => i18n.changeLanguage(e.value)}
                    width={120}
                    stylingMode="filled"
                />

                <div
                    className="dx-button dx-button-mode-outlined dx-button-danger"
                    onClick={onLogout}
                    style={{
                        cursor: 'pointer',
                        borderRadius: '4px',
                        width: '160px',
                        minWidth: '160px',
                        flex: 'none'
                    }}
                >
                    <div className="dx-button-content d-flex align-items-center justify-content-center">
                        <i className="dx-icon-runner me-2" />
                        <span className="dx-button-text" style={{
                            display: 'inline-block',
                            textAlign: 'center',
                            whiteSpace: 'nowrap'
                        }}>
                            {t('logout')}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Header;