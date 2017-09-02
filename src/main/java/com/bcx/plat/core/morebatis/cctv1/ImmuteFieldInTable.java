package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.FieldInTable;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import java.util.LinkedList;

public class ImmuteFieldInTable implements FieldInTable {

  private AliasedColumn aliasedColumn;
  private TableInfo tableSource;

  public ImmuteFieldInTable(AliasedColumn aliasedColumn, TableInfo tableSource) {
    this.aliasedColumn = aliasedColumn;
    this.tableSource = tableSource;
  }

  public AliasedColumn getAliasedColumn() {
    return aliasedColumn;
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
    return aliasedColumn.getAlies();
  }

  @Override
  public String getFieldSource() {
    return aliasedColumn.getFieldSource();
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return tableSource.getTableSource(translator);
  }
}
