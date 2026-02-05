import React from 'react';
import { DataGrid, Column, HeaderFilter, SearchPanel, GroupPanel, Scrolling, Pager, FilterRow } from "devextreme-react/data-grid";
import './customGrid.css';

function CustomGrid({dataSource, columns}){
    return(
        <div className="custom-grid-container">
            <DataGrid
                dataSource={dataSource}
                keyExpr="id"
                height="85vh"
                showBorders={true}
                columnAutoWidth={true}
                rowAlternationEnabled={true}
                allowColumnReordering={true}
                allowColumnResizing={true}
                columnResizingmode="widget"
                className="custom-grid-style"
            >
                <FilterRow visible={true}/>
                <HeaderFilter visible={true} />
                <SearchPanel visible={true} width={240} />
                <GroupPanel visible={true} />

                <Scrolling mode="virtual" rowRenderingMode="virtual"/>

                {/* permet de boucler sur les colonnes*/}
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