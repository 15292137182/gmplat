package com.bcx.plat.core.database.action.singleton;

import com.bcx.plat.core.database.action.phantom.TableSource;

public class ImmuteTable implements TableSource{
    protected String tableName;

    public String getTableName() {
        return tableName;
    }

    public ImmuteTable(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getTableSourceSqlFragment() {
        return getTableName();
    }
}
