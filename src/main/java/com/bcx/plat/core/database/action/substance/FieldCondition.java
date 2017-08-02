package com.bcx.plat.core.database.action.substance;

import com.bcx.plat.core.database.action.phantom.Condition;
import com.bcx.plat.core.database.action.substance.condition.Operator;

public class FieldCondition implements Condition {

  Field field;
  Operator operator;
  Object value;

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

  @Override
  public String getConditionSqlFragment() {
    return null;
  }
}
