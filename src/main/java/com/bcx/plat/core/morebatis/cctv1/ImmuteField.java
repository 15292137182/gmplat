package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;


/**
 * 只读字段包装器
 */
public class ImmuteField implements AliasedColumn {

  AliasedColumn field;

  public ImmuteField(AliasedColumn field) {
    this.field = field;
  }

  @Override
  public String getAlias() {
    return field.getAlias();
  }

  @Override
  public String getFieldSource() {
    return field.getFieldSource();
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return field.getColumnSqlFragment(translator);
  }
}
