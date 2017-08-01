package com.bcx.plat.core.database.action;

import com.bcx.plat.core.database.action.phantom.Condition;
import com.bcx.plat.core.database.action.phantom.FieldSource;
import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.action.substance.FieldCondition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteAction {
    private TableSource tableSource;
    private List<FieldCondition> where;

    public TableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource(TableSource tableSource) {
        this.tableSource = tableSource;
    }

    public List<FieldCondition> getWhere() {
        return where;
    }

    public void setWhere(List<FieldCondition> where) {
        this.where = where;
    }

    public DeleteAction where(FieldCondition ... fieldCondition){
        return where(Arrays.asList(fieldCondition));
    }

    public DeleteAction where(List<FieldCondition> fieldCondition){
        setWhere(fieldCondition);
        return this;
    }

    public DeleteAction from(TableSource tableSource){
        setTableSource(tableSource);
        return this;
    }
}
