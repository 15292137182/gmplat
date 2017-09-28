package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.phantom.TableSource;

public class Table implements TableSource<Table> {

  private String tableName;
  private String schema;
  private SqlSegment sqlSegment;

  public Table() {
  }

  public Table(String tableName) {
    this.tableName = tableName;
    sqlSegment = new SqlSegment(getTableName());
  }

  public Table(String schema, String tableName) {
    this.tableName = tableName;
    this.schema = schema;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
    sqlSegment = new SqlSegment(getTableName());
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public SqlSegment getSqlSegment() {
    return sqlSegment;
  }
}