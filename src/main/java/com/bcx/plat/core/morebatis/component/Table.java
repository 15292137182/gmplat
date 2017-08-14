package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.LinkedList;

public class Table implements TableSource {

  private String tableName;
  private SqlSegment sqlSegment;
  public Table(String tableName) {
    this.tableName = tableName;
    sqlSegment=new SqlSegment(getTableName());
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public SqlSegment getSqlSegment() {
    return sqlSegment;
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return translator.translate(this);
  }
}