package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.UtilsTool;
import java.util.Arrays;

/**
 * 普通字段
 */
public class Field implements Column {

  /**
   * 字段名
   */
  private String fieldName;
  /**
   * 别名
   */
  private String alies;

  public Field(){}

  public Field(String fieldName) {
    this.fieldName = UtilsTool.camelToUnderline(fieldName);
    this.alies= fieldName;
  }

  public Field(String fieldName, String alies) {
    this.fieldName = fieldName;
    this.alies = alies;
  }


  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getAlies() {
    return alies;
  }

  public void setAlies(String alies) {
    this.alies = alies;
  }

  public String getFieldSource() {
    return getFieldName();
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    String alies = getAlies();
    final String fieldSource = getFieldSource();
    if (alies == null || alies.isEmpty()) {
      return fieldSource;
    } else {
      return fieldSource + " as \"" + getAlies()+"\"";
    }
  }
}