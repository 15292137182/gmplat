package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 数据库表信息，记录实体表的信息
 * Create By HCL at 2017/7/31
 */
public class TableInfo extends BaseEntity<TableInfo>{

    private String schema;
    private String tableEName;
    private String tableCName;
    private String explain;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableEName() {
        return tableEName;
    }

    public void setTableEName(String tableEName) {
        this.tableEName = tableEName;
    }

    public String getTableCName() {
        return tableCName;
    }

    public void setTableCName(String tableCName) {
        this.tableCName = tableCName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
