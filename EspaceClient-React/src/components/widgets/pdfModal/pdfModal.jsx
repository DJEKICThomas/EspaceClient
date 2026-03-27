import React, { useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';

const PdfModal = ({ show, onClose, pdfUrl, title }) => {
    const { t } = useTranslation();
    const modalRef = useRef(null);

    useEffect(() => {
        const handleEscape = (e) => {
            if (e.key === 'Escape') onClose();
        };

        if (show) {
            document.addEventListener('keydown', handleEscape);
            document.body.style.overflow = 'hidden';
        }

        return () => {
            document.removeEventListener('keydown', handleEscape);
            document.body.style.overflow = '';
        };
    }, [show, onClose]);

    const handleBackdropClick = (e) => {
        if (e.target === modalRef.current) {
            onClose();
        }
    };

    if (!show) return null;

    return (
        <div
            ref={modalRef}
            className="modal fade show d-block"
            tabIndex="-1"
            role="dialog"
            style={{ backgroundColor: 'rgba(0,0,0,0.6)' }}
            onClick={handleBackdropClick}
        >
            <div className="modal-dialog modal-xl modal-dialog-centered" style={{ maxWidth: '90vw', height: '90vh' }}>
                <div className="modal-content h-100">
                    <div className="modal-header py-2">
                        <h5 className="modal-title">
                            <i className="fi fi-ts-file-pdf me-2" style={{ color: '#e74c3c' }} />
                            {title || t('pdf_viewer')}
                        </h5>
                        <button
                            type="button"
                            className="btn-close"
                            aria-label="Close"
                            onClick={onClose}
                        />
                    </div>
                    <div className="modal-body p-0" style={{ flex: 1, minHeight: 0 }}>
                        <iframe
                            src={pdfUrl}
                            title={title || t('pdf_viewer')}
                            style={{
                                width: '100%',
                                height: '100%',
                                border: 'none'
                            }}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PdfModal;
