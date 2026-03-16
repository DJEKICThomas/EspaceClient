import React from 'react';
import { DataGrid, Column, HeaderFilter, SearchPanel, ColumnChooser, Position, GroupPanel, Scrolling, FilterRow, MasterDetail, Editing } from "devextreme-react/data-grid";
import '../../../css/dx.EGlobal.css';
import { useTranslation } from "react-i18next";

function CustomGrid({ dataSource, columns, keyExpr = "id", masterDetail, editing, onRowUpdated, showSearch, height = "100%" }) {

    const { t } = useTranslation();

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
            >
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