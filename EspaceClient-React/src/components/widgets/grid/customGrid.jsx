import React from 'react';
import { DataGrid, Column, HeaderFilter, SearchPanel, ColumnChooser, Position, GroupPanel, Scrolling, FilterRow, MasterDetail, Editing, Export } from "devextreme-react/data-grid";
import '../../../css/dx.EGlobal.css';
import { useTranslation } from "react-i18next";

import { exportDataGrid } from 'devextreme/excel_exporter';
import { Workbook } from 'exceljs';
import { saveAs } from 'file-saver';

function CustomGrid({ dataSource, columns, keyExpr = "id", masterDetail, editing, onRowUpdated, showSearch, height = "100%", defaultFilter }) {

    const { t } = useTranslation();

    const onExporting = (e) => {
        const workbook = new Workbook();
        const worksheet = workbook.addWorksheet('Données');

        exportDataGrid({
            component: e.component,
            worksheet: worksheet,
            autoFilterEnabled: true,
        }).then(() => {
            workbook.xlsx.writeBuffer().then((buffer) => {
                saveAs(new Blob([buffer], { type: 'application/octet-stream' }), 'Export_Depairon.xlsx');
            });
        });
        e.cancel = true;
    };

    const handleCellClick = (e) => {
        // On ne fait rien si ce n'est pas une ligne de données ou si le détail n'est pas activé
        if (e.rowType !== "data" || !masterDetail || !masterDetail.enabled) {
            return;
        }

        // Laisser DevExtreme gérer nativement : chevron d'extension, case de sélection, bouton adaptatif
        if (e.column && (e.column.command === "expand" || e.column.command === "select" || e.column.command === "adaptive")) {
            return;
        }

        // Laisser DevExtreme gérer les boutons de commande (modifier, supprimer, etc.)
        if (e.column && e.column.type === "buttons") {
            return;
        }

        // Si on arrive ici, c'est un clic sur une cellule de texte -> on bascule l'affichage du détail
        if (e.component.isRowExpanded(e.key)) {
            e.component.collapseRow(e.key);
        } else {
            e.component.expandRow(e.key);
        }
    };

    return (
        <div className="custom-grid-container" style={{ height: '100%', width: '100%' }}>
            <DataGrid
                dataSource={dataSource}
                keyExpr={keyExpr}
                height={height}
                width="100%"
                defaultFilterValue={defaultFilter}
                showBorders={true}
                columnAutoWidth={true}
                columnHidingEnabled={false}
                wordWrapEnabled={true}
                rowAlternationEnabled={true}
                allowColumnReordering={true}
                allowColumnResizing={true}
                columnResizingMode="widget"
                className="dx-datagrid-borders custom-grid-style"
                onCellClick={handleCellClick}
                onRowUpdated={onRowUpdated}
                onContentReady={(e) => e.component.updateDimensions()}
                onExporting={onExporting}
            >

                <Export
                    enabled={true}
                    allowExportSelectedData={false}
                />

                <ColumnChooser enabled={true} mode="select">
                    <Position my="right top" at="right bottom" of=".dx-datagrid-column-chooser-button" />
                </ColumnChooser>

                <FilterRow visible={false} />
                <HeaderFilter visible={true} />
                <SearchPanel visible={showSearch} width={240} placeholder={t('search')} />

                <GroupPanel visible={true} emptyPanelText={t('grid_group_panel')} />

                {editing && (
                    <Editing
                        mode={editing.mode || 'row'}
                        allowUpdating={editing.allowUpdating}
                        allowAdding={editing.allowAdding}
                        allowDeleting={editing.allowDeleting}
                        useIcons={true}
                    />
                )}

                <Scrolling mode="virtual" />

                {masterDetail && (
                    <MasterDetail
                        enabled={masterDetail.enabled}
                        template={masterDetail.template}
                    />
                )}

                {columns.map((col, index) => (
                    <Column
                        key={index}
                        {...col}
                        alignment={col.alignment || 'left'}
                    />
                ))}
            </DataGrid>
        </div>
    );
}

export default CustomGrid;