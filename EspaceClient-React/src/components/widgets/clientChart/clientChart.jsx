import React from 'react';
import { PieChart, Series, Legend, Tooltip } from 'devextreme-react/pie-chart';
import { useTranslation } from "react-i18next";

const ClientChart = ({ data }) => {

    const { t } = useTranslation();

    return (
        <PieChart
            id="client-pie"
            dataSource={data}
            palette="Material"
            title={t('my_client')}
        >
            <Series
                argumentField="customerName" // Le nom de la société
                valueField="valeurPart" // NCS comme valeur par défaut
            />

            <Legend visible={true} verticalAlignment="bottom" horizontalAlignment="center" />

            <Tooltip
                enabled={true}
                customizeTooltip={(arg) => {
                    const clientComplet = arg.point.data;

                    return {
                        text: `${arg.argumentText} \n NCS: ${clientComplet.ncs}`
                    };
                }}
            />
        </PieChart>
    );
};

export default ClientChart;

