package com.bcx.plat.core.database.info;

import com.bcx.plat.core.morebatis.cctv1.ImmuteField;
import com.bcx.plat.core.morebatis.cctv1.ImmuteTable;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;

public class FieldMeta implements AliasedColumn {
  private final ImmuteTable tableSource;
  private final ImmuteField field;

  public FieldMeta(ImmuteTable tableSource, ImmuteField field) {
    this.tableSource = tableSource;
    this.field = field;
  }


  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return field.getColumnSqlFragment(translator);
  }

  @Override
  public String getAlias() {
    return field.getAlias();
  }

  @Override
  public String getFieldSource() {
    return field.getFieldSource();
  }
}
