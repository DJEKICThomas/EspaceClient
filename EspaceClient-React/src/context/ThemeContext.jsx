import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';

const ThemeContext = createContext();

const LIGHT_THEME_CSS = '/css/dx.EGlobal.css';
const DARK_THEME_CSS = '/css/dx.dark.css';
const THEME_LINK_ID = 'dx-theme-link';

export function ThemeProvider({ children }) {
    const [isDarkMode, setIsDarkMode] = useState(() => {
        return localStorage.getItem('theme') === 'dark';
    });

    const applyTheme = useCallback((dark) => {
        // Toggle class on body for custom CSS overrides
        document.body.classList.toggle('dark-mode', dark);

        // Swap DevExtreme theme stylesheet
        let themeLink = document.getElementById(THEME_LINK_ID);
        if (!themeLink) {
            themeLink = document.createElement('link');
            themeLink.id = THEME_LINK_ID;
            themeLink.rel = 'stylesheet';
            document.head.appendChild(themeLink);
        }

        if (dark) {
            themeLink.href = DARK_THEME_CSS;
        } else {
            // In light mode, remove the dark override — dx.EGlobal.css is imported statically
            themeLink.removeAttribute('href');
        }
    }, []);

    useEffect(() => {
        applyTheme(isDarkMode);
    }, [isDarkMode, applyTheme]);

    const toggleTheme = useCallback(() => {
        setIsDarkMode(prev => {
            const next = !prev;
            localStorage.setItem('theme', next ? 'dark' : 'light');
            return next;
        });
    }, []);

    const resetTheme = useCallback(() => {
        setIsDarkMode(false);
    }, []);

    return (
        <ThemeContext.Provider value={{ isDarkMode, toggleTheme, resetTheme }}>
            {children}
        </ThemeContext.Provider>
    );
}

export function useTheme() {
    const context = useContext(ThemeContext);
    if (!context) {
        throw new Error('useTheme must be used within a ThemeProvider');
    }
    return context;
}
