import React from 'react';
import { PieChart, Series, Legend, Tooltip, Label } from 'devextreme-react/pie-chart';

const GenericPieCard = ({
                            data,
                            cardTitle,
                            argumentField,
                            valueField,
                            tooltipField1,
                            tooltipField2,
                            palette = "Material",
                            type = "pie",
                            totalCount,
                            labelText
                        }) => {
    return (
        /* UN SEUL RECTANGLE (Bordure orange à gauche) */
        <div className="summary-card shadow-sm rounded bg-white p-4 border-start" style={{ borderColor: '#f39c12', borderLeftWidth: '8px' }}>

            {/* Titre interne au rectangle */}
            <div className="text-center mb-3">
                <h5 className="text-muted text-uppercase small fw-bold" style={{ letterSpacing: '0.5px' }}>
                    {cardTitle}
                </h5>
            </div>

            {/* Section Graphique - On enlève la div 'pie-container' à hauteur fixe */}
            <PieChart
                dataSource={data}
                palette={palette}
                type={type}
                height={300} // On définit la hauteur ici
            >
                <Series argumentField={argumentField} valueField={valueField}>
                    <Label visible={false}/>
                </Series>
                <Legend
                    visible={true}
                    verticalAlignment="bottom"
                    horizontalAlignment="center"
                    itemTextPosition="right"
                    rowCount={1}
                />
                <Tooltip
                    enabled={true}
                    customizeTooltip={(arg) => {
                        const item = arg.point.data;
                        // On récupère dynamiquement les champs grâce aux props
                        const info1 = item[tooltipField1] || "";
                        const info2 = item[tooltipField2] ? `\n ${item[tooltipField2]}` : "";

                        return {
                            text: `${info1}${info2}`
                        };
                    }}
                />
            </PieChart>

            {/* Section Badge - TOUJOURS À L'INTÉRIEUR DU RECTANGLE */}
            <div className="text-center mt-4 pt-3" style={{ borderTop: '1px solid #f0f0f0' }}>
                <span className="badge rounded-pill bg-light text-dark p-2 border">
                    {totalCount} {labelText}
                </span>
            </div>
        </div>
    );
};

export default GenericPieCard;