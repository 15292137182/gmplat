package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.component.constant.Operator;

public class FieldCondition implements Condition<FieldCondition> {
  AliasedColumn field;
  boolean not=false;
  Operator operator;
  Object value;

  public FieldCondition(AliasedColumn field, Operator operator, Object value, boolean not) {
    this.field = field;
    this.operator = operator;
    this.value = value;
    this.not=not;
  }

  public FieldCondition(String fieldName, Operator operator, Object value,boolean not) {
    this.field = new Field(fieldName);
    this.operator = operator;
    this.value = value;
    this.not=not;
  }

  public FieldCondition(AliasedColumn field, Operator operator, Object value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  public FieldCondition(String fieldName, Operator operator, Object value) {
    this.field = new Field(fieldName);
    this.operator = operator;
    this.value = value;
  }

  public AliasedColumn getField() {
    return field;
  }

  public void setField(AliasedColumn field) {
    this.field = field;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public boolean isNot() {
    return not;
  }

  public void setNot(boolean not) {
    this.not = not;
  }

  @Override
  public String getConditionSqlFragment() {
    return null;
  }
}
