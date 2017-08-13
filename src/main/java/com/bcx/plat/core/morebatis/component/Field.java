package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.Column;

/**
 * 普通字段
 */
public class Field implements Column {

  /**
   * 字段名
   */
  protected String fieldName;
  /**
   * 别名
   */
  protected String alies;

  public Field(String fieldName) {
    this.fieldName = fieldName;
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

  public String getColumnSqlFragment() {
    String alies = getAlies();
    if (alies == null || alies.isEmpty()) {
      return getFieldSource();
    } else {
      return getFieldSource() + " as \"" + getAlies()+"\"";
    }
  }

  public String getFieldSource() {
    return getFieldName();
  }
}