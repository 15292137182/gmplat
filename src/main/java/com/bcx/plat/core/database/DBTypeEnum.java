package com.bcx.plat.core.database;

/**
 * 数据源类型定义
 */
public enum DBTypeEnum {

    /**
     * postgreSQL 数据库
     */
    PSQL("dataSourcePSQL");

    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
