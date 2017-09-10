package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.FieldInTable;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;

import java.util.LinkedList;

public class ImmuteFieldInTable implements FieldInTable {

  private AliasedColumn aliasedColumn;
  private TableSource tableSource;

  public ImmuteFieldInTable(AliasedColumn aliasedColumn, TableSource tableSource) {
    this.aliasedColumn = aliasedColumn;
    this.tableSource = tableSource;
  }

  public AliasedColumn getAliasedColumn() {
    return aliasedColumn;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return getFieldSource();
  }

  @Override
  public String getAlias() {
    return aliasedColumn.getAlias();
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
