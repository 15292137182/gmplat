package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.FieldSource;

public class SubAttribute implements FieldSource<SubAttribute> {

  FieldSource field;
  String key;

  public SubAttribute(FieldSource field, String key) {
    this.field = field;
    this.key = key;
  }

  public FieldSource getField() {
    return field;
  }

  public void setField(FieldSource field) {
    this.field = field;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
