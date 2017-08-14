package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.LinkedList;

public class FieldInTable implements TableSource,Column {

  private Column column;
  private TableSource tableSource;

  public FieldInTable(Column column, TableSource tableSource) {
    this.column = column;
    this.tableSource = tableSource;
  }

  public Column getColumn() {
    return column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public void setTableSource(TableSource tableSource) {
    this.tableSource = tableSource;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return column.getColumnSqlFragment(translator);
  }

  @Override
  public String getAlies() {
    return column.getAlies();
  }

  @Override
  public String getFieldSource() {
    return column.getFieldSource();
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return tableSource.getTableSource(translator);
  }
}
