package com.bcx.plat.core.morebatis.component.condition;

import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.Arrays;
import java.util.List;

public class Or implements ChainCondition<Or> {

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

  public Or(Condition ... conditions) {
    this.conditions = Arrays.asList(conditions);
  }

  public Or(List<Condition> conditions) {
    this.conditions = conditions;
  }

  @Override
  public String getConditionSqlFragment() {
    return null;
  }
}
