import React, { useState, useEffect } from 'react';
import '../../../css/dx.EGlobal.css';

const GenericDetail = ({ data, endpoint, columns, title, idField }) => {
    const [details, setDetails] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadDetails = async () => {
            const token = localStorage.getItem('token');
            const idValue = data[idField];

            try {
                const response = await fetch(`http://localhost:8080/EspaceClient-api/${endpoint}?${idField}=${idValue}`, {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                if (response.ok) {
                    setDetails(await response.json());
                }
            } catch (error) {
                console.error("Erreur chargement détails:", error);
            } finally {
                setLoading(false);
            }
        };
        loadDetails();
    }, [data, endpoint, idField]);

    if (loading) return (
        <div className="p-3 dx-widget" style={{ fontStyle: 'italic' }}>
            <i className="dx-icon-refresh dx-icon-spin" style={{ marginRight: '8px' }}/>
            Chargement...
        </div>
    );

    return (
        <div className="p-4 dx-card dx-widget" style={{
            backgroundColor: '#fafafa',
            borderLeft: `5px solid #ff9800`,
            margin: '10px'
        }}>
            <h6 className="dx-form-group-caption fw-bold mb-3">
                {title} : <span className="dx-theme-accent-color">{data[idField]}</span>
            </h6>

            <div className="dx-datagrid dx-gridbase-container">
                <table className="dx-datagrid-table dx-datagrid-table-fixed" style={{ width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                    <tr className="dx-row dx-column-lines dx-header-row" style={{ backgroundColor: '#f3f3f3' }}>
                        {columns.map((col, i) => (
                            <th key={i} className="dx-datagrid-text-content p-2 text-start" style={{ border: '1px solid #ddd' }}>
                                {col.caption}
                            </th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {details.length > 0 ? (
                        details.map((row, i) => (
                            <tr key={i} className="dx-row dx-data-row dx-column-lines dx-row-alt">
                                {columns.map((col, j) => (
                                    <td key={j} className="p-2" style={{ border: '1px solid #ddd' }}>
                                        {row[col.dataField] || '-'}
                                    </td>
                                ))}
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={columns.length} className="p-3 text-center text-muted">
                                Aucune donnée disponible
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default GenericDetail;