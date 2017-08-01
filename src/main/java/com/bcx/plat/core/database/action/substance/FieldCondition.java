package com.bcx.plat.core.database.action.substance;

import com.bcx.plat.core.database.action.phantom.Condition;

public class FieldCondition implements Condition{
    Field field;
    int operator;
    Object value;

    public FieldCondition(Field field, int operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
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
