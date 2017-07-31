package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by Went on 2017/7/31.
 * 维护数据库表实体类
 */
public class MaintTableInfo extends BaseEntity<MaintTableInfo> implements Serializable {

    private String schema;//表schema
    private String tableEname;//表中文名
    private String tableCname;//表英文名
    private String explain;//说明

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableEname() {
        return tableEname;
    }

    public void setTableEname(String tableEname) {
        this.tableEname = tableEname;
    }

    public String getTableCname() {
        return tableCname;
    }

    public void setTableCname(String tableCname) {
        this.tableCname = tableCname;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
