package com.bcx.plat.core.morebatis.substance.condition;

import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.Arrays;
import java.util.List;

public class And implements Condition<And> {

  List<Condition> conditions;

  boolean not=false;

  @Override
  public boolean isNot() {
    return not;
  }

  public void setNot(boolean not) {
    this.not = not;
  }

  public List<Condition> getConditions() {
    return conditions;
  }

  public void setConditions(List<Condition> conditions) {
    this.conditions = conditions;
  }

  public And(Condition ... conditions) {
    setConditions(Arrays.asList(conditions));
  }

  public And(List<Condition> conditions) {
    setConditions(conditions);
  }

  @Override
  public String getConditionSqlFragment() {
    return null;
  }
}
