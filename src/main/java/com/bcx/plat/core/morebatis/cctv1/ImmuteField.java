package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.substance.Field;


/**
 * 只读字段包装器
 */
public class ImmuteField implements Column {

  Column field;

  public ImmuteField(Field field) {
    this.field = field;
  }

  @Override
  public String getColumnSqlFragment() {
    return field.getColumnSqlFragment();
  }

  @Override
  public String getAlies() {
    return field.getAlies();
  }

  @Override
  public String getFieldSource() {
    return field.getFieldSource();
  }
}
