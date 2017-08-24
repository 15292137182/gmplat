package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.FieldInTable;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import java.util.LinkedList;

public class ImmuteFieldInTable implements FieldInTable {

  private Column column;
  private TableInfo tableSource;

  public ImmuteFieldInTable(Column column, TableInfo tableSource) {
    this.column = column;
    this.tableSource = tableSource;
  }

  public Column getColumn() {
    return column;
  }

  public TableInfo getTableInfo() {
    return tableSource;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return getFieldSource();
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
