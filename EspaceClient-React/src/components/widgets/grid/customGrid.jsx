import React from 'react';
import { DataGrid, Column, HeaderFilter, SearchPanel, ColumnChooser, Position, GroupPanel, Scrolling, FilterRow, MasterDetail, Editing } from "devextreme-react/data-grid";
import '../../../css/dx.EGlobal.css';
import { useTranslation } from "react-i18next";

function CustomGrid({ dataSource, columns, keyExpr = "id", masterDetail, editing, onRowUpdated, showSearch, height = "100%" }) {

    const { t } = useTranslation();

    const handleRowClick = (e) => {
        if (masterDetail && masterDetail.enabled) {
            const isExpanded = e.component.isRowExpanded(e.key);
            if (isExpanded) {
                e.component.collapseRow(e.key);
            } else {
                e.component.expandRow(e.key);
            }
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
                onRowClick={handleRowClick}
                onRowUpdated={onRowUpdated}
                /* Force le recalcul des dimensions au zoom ou changement de langue */
                onContentReady={(e) => e.component.updateDimensions()}
            >
                <ColumnChooser
                    enabled={true}
                    mode="select"
                >
                    <Position
                        my="right top"
                        at="right bottom"
                        of=".dx-datagrid-column-chooser-button"
                    />
                </ColumnChooser>

                <FilterRow visible={false} />
                <HeaderFilter visible={true} />
                <SearchPanel visible={showSearch} width={240} placeholder={t('search')} />

                <GroupPanel
                    visible={true}
                    emptyPanelText={t('grid_group_panel')}
                />

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