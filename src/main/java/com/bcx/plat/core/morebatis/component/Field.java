package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.utils.UtilsTool;

/**
 * 普通字段
 */
public class Field implements AliasedColumn {

  /**
   * 所在表
   */
  private Table table;
  /**
   * 字段名
   */
  private String fieldName;

  /**
   * 别名
   */
  private String alias;

  public Field() {
  }

  public Field(String alias) {
    this.fieldName = UtilsTool.camelToUnderline(alias);
    this.alias = alias;
  }

  public Field(String fieldName, String alias) {
    this.fieldName = fieldName;
    this.alias = alias;
  }

  public Field(Table table, String fieldName, String alias) {
    this.table = table;
    this.fieldName = fieldName;
    this.alias = alias;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getFieldSource() {
    return getFieldName();
  }

  public Table getTable() {
    return table;
  }

  public void setTable(Table table) {
    this.table = table;
  }
}