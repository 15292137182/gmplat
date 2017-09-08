package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
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
  private String alies;

  private String castType;

  public Field(){}

  public Field(String fieldName) {
    this.fieldName = UtilsTool.camelToUnderline(fieldName);
    this.alies= fieldName;
  }

  public Field(String fieldName, String alies) {
    this.fieldName = fieldName;
    this.alies = alies;
  }

  public Field(String fieldName, String alies,String castType) {
    this.fieldName = fieldName;
    this.alies = alies;
    this.castType=castType;
  }


  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getCastType() {
    return castType;
  }

  public void setCastType(String castType) {
    this.castType = castType;
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

  public Table getTable() {
    return table;
  }

  public void setTable(Table table) {
    this.table = table;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    // TODO 另一个translator完成以后应该移除
    String alies = getAlies();
    final String fieldSource = getFieldSource();
    if (alies == null || alies.isEmpty()) {
      return fieldSource;
    } else {
      return fieldSource + " as \"" + getAlies()+"\"";
    }
  }
}