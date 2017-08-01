package com.bcx.plat.core.database.action;

import com.bcx.plat.core.database.action.phantom.Column;
import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.action.substance.FieldCondition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueryActionLite{
    List<Column> columns;
    TableSource tableSource;
    List<FieldCondition> where;
    Integer page;
    Integer pageSize;

    public QueryActionLite() {
        where=new LinkedList<>();
        columns=new LinkedList<>();
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public QueryActionLite select(Column ... column){
        return select(Arrays.asList(column));
    }

    public QueryActionLite where(FieldCondition ... fieldCondition){
        return where(Arrays.asList(fieldCondition));
    }

    public QueryActionLite select(List<Column> column){
        columns.addAll(column);
        return this;
    }

    public QueryActionLite where(List<FieldCondition> fieldCondition){
        where.addAll(fieldCondition);
        return this;
    }

    public QueryActionLite from(TableSource tableSource){
        this.tableSource=tableSource;
        return this;
    }

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
