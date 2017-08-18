package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;


/**
 * 只读字段包装器
 */
public class ImmuteField implements Column {

  Column field;

  public ImmuteField(Column field) {
    this.field = field;
  }

  @Override
  public String getAlies() {
    return field.getAlies();
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
