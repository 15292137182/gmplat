package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.TableSource;

public class Table implements TableSource {

  protected String tableName;

  public Table(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public String getTableSourceSqlFragment() {
    return getTableName();
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}