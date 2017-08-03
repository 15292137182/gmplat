package com.bcx.plat.core.database.action;

import com.bcx.plat.core.database.action.phantom.Column;
import com.bcx.plat.core.database.action.phantom.Condition;
import com.bcx.plat.core.database.action.phantom.FieldSource;
import com.bcx.plat.core.database.action.phantom.TableSource;
import java.util.List;

public abstract class QueryActionPro implements TableSource {

  private List<Column> columns;
  private TableSource tableSource;
  private Condition where;
  private List<FieldSource> group;
  private Integer page;
  private Integer pageSize;
  private Condition having;

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public void setTableSource(TableSource tableSource) {
    this.tableSource = tableSource;
  }

  public Condition getWhere() {
    return where;
  }

  public void setWhere(Condition where) {
    this.where = where;
  }

  public List<FieldSource> getGroup() {
    return group;
  }

  public void setGroup(List<FieldSource> group) {
    this.group = group;
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

  public Condition getHaving() {
    return having;
  }

  public void setHaving(Condition having) {
    this.having = having;
  }
}
