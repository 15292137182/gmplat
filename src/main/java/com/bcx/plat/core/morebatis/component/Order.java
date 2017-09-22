package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.FieldSource;

public class Order {

  public static final int DESC = 0, ASC = 1;

  private Object source;

  private int order = DESC;

  public Order(FieldSource fieldSource, int order) {
    this.source = fieldSource;
    this.order = order;
  }

  public Order(String alias, int order) {
    this.source = alias;
    this.order = order;
  }

  public void setSource(FieldSource fieldSource) {
    this.source = fieldSource;
  }

  public void setSource(String alias) {
    this.source = alias;
  }

  public Object getSource() {
    return source;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }
}
