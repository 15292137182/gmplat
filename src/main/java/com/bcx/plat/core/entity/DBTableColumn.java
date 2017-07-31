package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 数据库表信息
 * Create By HCL at 2017/7/31
 */
public class DBTableColumn extends BaseEntity<DBTableColumn> {

    private String relateTableRowId;
    private String columnEname;
    private String columnCname;
    private String desp;

    public String getRelateTableRowId() {
        return relateTableRowId;
    }

    public void setRelateTableRowId(String relateTableRowId) {
        this.relateTableRowId = relateTableRowId;
    }

    public String getColumnEname() {
        return columnEname;
    }

    public void setColumnEname(String columnEname) {
        this.columnEname = columnEname;
    }

    public String getColumnCname() {
        return columnCname;
    }

    public void setColumnCname(String columnCname) {
        this.columnCname = columnCname;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}