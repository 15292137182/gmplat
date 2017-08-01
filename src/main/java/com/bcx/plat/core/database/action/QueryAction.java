package com.bcx.plat.core.database.action;

import com.bcx.plat.core.database.action.phantom.Column;
import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.action.substance.Field;
import com.bcx.plat.core.database.action.substance.FieldCondition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueryAction {
    private static final Field ALL_FIELD = new Field("*");
    private List<Column> columns;
    private TableSource tableSource;
    private List<FieldCondition> where;
//    Integer page;
//    Integer pageSize;

    public QueryAction() {
        where = new LinkedList<>();
        columns = new LinkedList<>();
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public QueryAction select(Column... column) {
        return select(Arrays.asList(column));
    }


    public QueryAction select(List<Column> column) {
        setColumns(column);
        return this;
    }

    public QueryAction selectAll() {
        return select(ALL_FIELD);
    }

    public QueryAction where(FieldCondition... fieldCondition) {
        return where(Arrays.asList(fieldCondition));
    }

    public QueryAction where(List<FieldCondition> fieldCondition) {
        setWhere(fieldCondition);
        return this;
    }

    public QueryAction from(TableSource tableSource) {
        setTableSource(tableSource);
        return this;
    }

    public List<FieldCondition> getWhere() {
        return where;
    }

    public void setWhere(List<FieldCondition> where) {
        this.where = where;
    }

    public TableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource(TableSource tableSource) {
        this.tableSource = tableSource;
    }

}
