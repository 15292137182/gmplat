package com.bcx.plat.core.database.action.substance;

import com.bcx.plat.core.database.action.singleton.ImmuteTable;

public class Table extends ImmuteTable {
    public Table(String tableName) {
        super(tableName);
    }

    public void setTableName(String tableName){
        this.tableName=tableName;
    }
}