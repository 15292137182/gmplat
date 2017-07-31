package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by Went on 2017/7/31.
 * 维护数据库表实体类
 */
public class MaintTableInfo extends BaseEntity<MaintTableInfo> implements Serializable {

    private String schema;//表schema
    private String tableEName;//表中文名
    private String tableCName;//表英文名
    private String explain;//说明

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
