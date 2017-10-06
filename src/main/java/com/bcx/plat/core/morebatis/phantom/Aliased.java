package com.bcx.plat.core.morebatis.phantom;

public class Aliased implements AliasedColumn{
  private FieldSource fieldSource;
  private String alias;

  public FieldSource getFieldSource() {
    return fieldSource;
  }

  public void setFieldSource(FieldSource fieldSource) {
    this.fieldSource = fieldSource;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Aliased(FieldSource fieldSource, String alias) {
    this.fieldSource = fieldSource;
    this.alias = alias;
  }
}
