import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from "react-i18next";

function GlobalSearch({ invoices, orders, offers, subscriptions, onNavigate}) {
    const {t} = useTranslation();
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const [showDropdown, setShowDropdown] = useState(false);
    const wrapperRef = useRef(null);
    const debounceRef = useRef(null);

    useEffect(() => {
        const handleClickOutside = (e) => {
            // Clique en dehors
            if(wrapperRef.current && !wrapperRef.current.contains(e.target)){
                setShowDropdown(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    // Nettoyer l'écran
    const search = (text) => {
        if(!text || text.length < 2){
            setResults([]);
            setShowDropdown(false);
            return;
        }

        const q = text.toLowerCase();
        const found = [];

        // Factures
        invoices
            .filter(inv =>
                inv.num?.toString().toLowerCase().includes(q) ||
                inv.des?.toLowerCase().includes(q)
            )
            .slice(0, 4) //Que 4 résultats
            .forEach(inv => found.push({
                type: 'invoices',
                label: `${t('invoice')} ${inv.num}`,
                sub: inv.des || '',
                filterField: 'num',
                filterValue: inv.num,
                icon: 'fi fi-rs-receipt'
            }));

        // Commandes
        orders
            .filter(ord =>
                ord.document?.toString().toLowerCase().includes(q) ||
                ord.docDes?.toLowerCase().includes(q) ||
                ord.reference?.toLowerCase().includes(q) ||
                ord.fournisseur?.toLowerCase().includes(q)
            )
            .slice(0, 4)
            .forEach(ord => found.push({
                type: 'orders',
                label: `${t('order')} ${ord.document}`,
                sub: ord.fournisseur || ord.docDes || '',
                filterField: 'document',
                filterValue: ord.document,
                icon: 'fi fi-rs-box-open'
            }));

        // Offres
        offers
            .filter(off =>
                off.document?.toString().toLowerCase().includes(q) ||
                off.docDes?.toLowerCase().includes(q) ||
                off.reference?.toLowerCase().includes(q) ||
                off.fournisseur?.toLowerCase().includes(q)
            )
            .slice(0, 4)
            .forEach(off => found.push({
                type: 'offers',
                label: `${t('offer')} ${off.document}`,
                sub: off.fournisseur || off.docDes || '',
                filterField: 'document',
                filterValue: off.document,
                icon: 'fi fi-rs-document-signed'
            }));

        // Abonnements
        subscriptions
            .filter(sub =>
                sub.reference?.toLowerCase().includes(q) ||
                sub.description?.toLowerCase().includes(q)
            )
            .slice(0, 4)
            .forEach(sub => found.push({
                type: 'subscriptions',
                label: `${t('subscription')} ${sub.reference}`,
                sub: sub.description || '',
                filterField: 'reference',
                filterValue: sub.reference,
                icon: 'fi fi-rs-refresh'
            }));

        setResults(found);
        setShowDropdown(found.length > 0);
    };

    // Permet d'attendre et de faire la recherche quand fini de tapper
    const handleChange = (e) => {
        const value = e.target.value;
        setQuery(value);

        clearTimeout(debounceRef.current);
        debounceRef.current = setTimeout(() => search(value), 300);
    };

    // Navigation
    const handleSelect = (result) => {
        setShowDropdown(false);
        setQuery('');
        onNavigate(result.type, result.filterField, result.filterValue);
    };

    return (
        <div ref={wrapperRef} style={{ position: 'relative', width: '100%', maxWidth: 500 }}>
            <input
                type="text"
                className="form-control"
                placeholder={t('global_search_placeholder')}
                value={query}
                onChange={handleChange}
                onFocus={() => results.length > 0 && setShowDropdown(true)}
                style={{
                    borderRadius: 8,
                    padding: '10px 16px',
                    paddingLeft: 40,
                    fontSize: 14
                }}
            />
            {/* Icône loupe */}
            <i className="fi fi-rr-search" style={{
                position: 'absolute', left: 12, top: '50%',
                transform: 'translateY(-50%)', color: '#999', fontSize: 16
            }} />

            {/* Dropdown résultats */}
            {showDropdown && (
                <div style={{
                    position: 'absolute',
                    top: '100%',
                    left: 0,
                    right: 0,
                    background: 'var(--dx-color-main-bg, #fff)',
                    border: '1px solid #ddd',
                    borderRadius: 8,
                    marginTop: 4,
                    maxHeight: 400,
                    overflowY: 'auto',
                    zIndex: 9999,
                    boxShadow: '0 4px 12px rgba(0,0,0,0.15)'
                }}>
                    {results.map((r, i) => (
                        <div
                            key={i}
                            onClick={() => handleSelect(r)}
                            style={{
                                padding: '10px 16px',
                                cursor: 'pointer',
                                borderBottom: i < results.length - 1 ? '1px solid #f0f0f0' : 'none',
                                display: 'flex',
                                alignItems: 'center',
                                gap: 12,
                                minHeight: 52,
                                transition: 'background-color 0.15s'
                            }}
                            onMouseEnter={(e) => e.currentTarget.style.background = 'var(--dx-color-hover-bg, #f5f5f5)'}
                            onMouseLeave={(e) => e.currentTarget.style.background = 'transparent'}
                        >
                            <div style={{
                                width: 36,
                                height: 36,
                                borderRadius: 8,
                                background: 'var(--dx-color-hover-bg, #f0f0f0)',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                flexShrink: 0
                            }}>
                                <i className={r.icon} style={{ fontSize: 16, color: '#f39c12' }} />
                            </div>
                            <div style={{ minWidth: 0, flex: 1 }}>
                                <div style={{ fontWeight: 600, fontSize: 14, color: 'var(--dx-color-text, #333)', textAlign: 'left' }}>{r.label}</div>
                                {r.sub && <div style={{
                                    fontSize: 12,
                                    color: 'var(--dx-color-text-secondary, #888)',
                                    whiteSpace: 'nowrap',
                                    overflow: 'hidden',
                                    textOverflow: 'ellipsis',
                                    textAlign: 'left'
                                }}>{r.sub}</div>}
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default GlobalSearch;


