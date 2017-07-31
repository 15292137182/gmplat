package com.bcx.plat.core.domain;

import java.io.Serializable;

/**
 * Created by Went on 2017/7/31.
 * 维护数据库表实体类
 */
public class MaintTablePojo implements Serializable{

    private Integer rowId;//表id
    private String schema;//表schema
    private String tableChinese;//表中文名
    private String tableEnglish;//表英文名
    private String explain;//说明


    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableChinese() {
        return tableChinese;
    }

    public void setTableChinese(String tableChinese) {
        this.tableChinese = tableChinese;
    }

    public String getTableEnglish() {
        return tableEnglish;
    }

    public void setTableEnglish(String tableEnglish) {
        this.tableEnglish = tableEnglish;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
