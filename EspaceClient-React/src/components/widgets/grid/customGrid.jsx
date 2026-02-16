import React from 'react';
import { DataGrid, Column, HeaderFilter, SearchPanel, GroupPanel, Scrolling, Pager, FilterRow, MasterDetail, Editing } from "devextreme-react/data-grid";
import './customGrid.css';

function CustomGrid({dataSource, columns, keyExpr = "id", masterDetail, editing, onRowUpdated}){

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

    return(
        <div className="custom-grid-container">
            <DataGrid
                dataSource={dataSource}
                keyExpr={keyExpr}
                height="85vh"
                showBorders={true}
                columnAutoWidth={true}
                columnHidingEnabled={false}
                wordWrapEnabled={true}
                rowAlternationEnabled={true}
                allowColumnReordering={true}
                allowColumnResizing={true}
                columnResizingmode="widget"
                className="custom-grid-style"
                onRowClick={handleRowClick}
                onRowUpdated={onRowUpdated}
            >
                <FilterRow visible={false}/>
                <HeaderFilter visible={true} />
                <SearchPanel visible={false} width={240} />
                <GroupPanel visible={true} />

                {editing && (
                    <Editing
                        mode={editing.mode}
                        allowUpdating={editing.allowUpdating}
                        allowAdding={editing.allowAdding}
                        allowDeleting={editing.allowDeleting}
                    />
                )}

                <Scrolling mode="virtual" rowRenderingMode="virtual"/>

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
                <Pager showPageSizeSelector={true} allowedPageSizes={100} />
            </DataGrid>
        </div>
    );
}

export default CustomGrid;