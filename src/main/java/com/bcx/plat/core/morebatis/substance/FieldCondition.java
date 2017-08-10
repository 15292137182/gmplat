package com.bcx.plat.core.morebatis.substance;

import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.substance.condition.Operator;

public class FieldCondition implements Condition<FieldCondition> {
  Field field;
  boolean not=false;
  Operator operator;
  Object value;

  public FieldCondition(Field field, Operator operator, Object value,boolean not) {
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

  public FieldCondition(Field field, Operator operator, Object value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  public FieldCondition(String fieldName, Operator operator, Object value) {
    this.field = new Field(fieldName);
    this.operator = operator;
    this.value = value;
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
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
