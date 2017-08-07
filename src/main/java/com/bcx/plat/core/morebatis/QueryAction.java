package com.bcx.plat.core.morebatis;

import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.substance.Field;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QueryAction {

  private static final Field ALL_FIELD = new Field("*");
  private List<Column> columns;
  private TableSource tableSource;
  private List<FieldCondition> where;

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

//  public PageResult<Map<String,Object>> pageQue

}
