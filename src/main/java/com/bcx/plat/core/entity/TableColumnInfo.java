package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 数据库表字段信息
 * Create By HCL at 2017/7/31
 */
public class TableColumnInfo extends BaseEntity<TableColumnInfo> {

    private String tableRowId;
    private String columnCName;
    private String columnEName;
    private String explain;

    public String getTableRowId() {
        return tableRowId;
    }

    public void setTableRowId(String tableRowId) {
        this.tableRowId = tableRowId;
    }

    public String getColumnCName() {
        return columnCName;
    }

    public void setColumnCName(String columnCName) {
        this.columnCName = columnCName;
    }

    public String getColumnEName() {
        return columnEName;
    }

    public void setColumnEName(String columnEName) {
        this.columnEName = columnEName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}