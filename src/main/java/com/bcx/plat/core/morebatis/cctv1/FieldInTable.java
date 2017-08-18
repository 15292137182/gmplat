package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.LinkedList;

public class FieldInTable implements TableSource,Column {

  private Field field;
  private Table table;

  public FieldInTable(Field field, Table table) {
    this.field = field;
    this.table = table;
  }

  public Field getField() {
    return field;
  }

  public Table getTable() {
    return table;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
//    return column.getColumnSqlFragment(translator);
    return getFieldSource();
  }

  @Override
  public String getAlies() {
    return field.getAlies();
  }

  @Override
  public String getFieldSource() {
    return table.getTableName()+"."+field.getFieldSource();
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return table.getTableSource(translator);
  }
}
