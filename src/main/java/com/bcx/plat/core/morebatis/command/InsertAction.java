package com.bcx.plat.core.morebatis.command;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InsertAction {

  private TableSource tableSource;
  private Class<? extends BaseEntity> entityClass;
  private Collection<String> columns;
  private List<Map<String, Object>> rows;
  private List<List<Object>> values;
  private SqlComponentTranslator translator;
  private MoreBatis app;

  public InsertAction(MoreBatis app,SqlComponentTranslator translator) {
    this.app=app;
    this.translator=translator;
  }

  public SqlComponentTranslator getTranslator() {
    return translator;
  }

  public List<List<Object>> getValues() {
    return values;
  }

  public Class<? extends BaseEntity> getEntityClass() {
    return entityClass;
  }

  public void setEntityClass(Class<? extends BaseEntity> entityClass) {
    this.entityClass = entityClass;
  }

  public String getValuesRefresh() {
    LinkedList<List<Object>> values = new LinkedList<>();
    for (Map<String, Object> row : rows) {
      List<Object> rowValue = new LinkedList<>();
      for (String column : columns) {
        rowValue.add(row.get(column));
      }
      values.add(rowValue);
    }
    this.values = values;
    return "";
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public void setTableSource(TableSource tableSource) {
    this.tableSource = tableSource;
  }

  public Collection<String> getColumns() {
    return columns;
  }

  public void setColumns(Collection<String> columns) {
    this.columns = columns;
  }

  public List<Map<String, Object>> getRows() {
    return rows;
  }

  public void setRows(List<Map<String, Object>> rows) {
    this.rows = rows;
  }

  public InsertAction into(TableSource table) {
    setTableSource(table);
    return this;
  }

  public InsertAction cols(Collection<String> columns) {
    setColumns(columns);
    return this;
  }

  public InsertAction values(List<Map<String, Object>> rows) {
    setRows(rows);
    return this;
  }

  public InsertAction values(Map<String, Object> ... row) {
    setRows(Arrays.asList(row));
    return this;
  }

  public int execute(){
    return app.execute(this);
  }
}
