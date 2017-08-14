package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.LinkedList;

public class ImmuteFieldInTable implements TableSource,Column{
  private Column column;
  private TableSource tableSource;

  public ImmuteFieldInTable(Column column,TableSource tableSource) {
    this.column = column;
    this.tableSource = tableSource;
  }

  public Column getColumn() {
    return column;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return null;
  }

  @Override
  public String getAlies() {
    return null;
  }

  @Override
  public String getFieldSource() {
    return null;
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return null;
  }
}
