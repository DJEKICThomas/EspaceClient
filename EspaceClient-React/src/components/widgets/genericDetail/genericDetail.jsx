import React, { useState, useEffect } from 'react';


const GenericDetail = ({ data, endpoint, columns, title, idField }) => {
    const [details, setDetails] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadDetails = async () => {
            const token = localStorage.getItem('token');
            const idValue = data[idField]; // récup la clé dynamiquement

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

    if (loading) return <div className="p-3 italic">Chargement...</div>;

    return (
        <div className="p-4 bg-light border-start border-4 border-warning">
            <h6 className="fw-bold">{title} : {data[idField]}</h6>
            <table className="table table-sm bg-white mt-3 shadow-sm">
                <thead>
                <tr>
                    {columns.map((col, i) => <th key={i}>{col.caption}</th>)}
                </tr>
                </thead>
                <tbody>
                {details.map((row, i) => (
                    <tr key={i}>
                        {columns.map((col, j) => (
                            <td key={j}>{row[col.dataField] || '-'}</td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default GenericDetail;