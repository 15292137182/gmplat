package com.bcx.plat.core.morebatis.component.condition;

import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class And implements ChainCondition<And> {

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
    LinkedList<Condition> list=new LinkedList<>();
    list.addAll(Arrays.asList(conditions));
    setConditions(list);
  }

  public And(List<Condition> conditions) {
    setConditions(conditions);
  }

  @Override
  public String getConditionSqlFragment() {
    return null;
  }
}
