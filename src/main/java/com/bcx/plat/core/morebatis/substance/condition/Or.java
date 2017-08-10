package com.bcx.plat.core.morebatis.substance.condition;

import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.List;

public class Or implements Condition<Or> {

  List<Condition> conditions;

  boolean not=false;

  @Override
  public boolean isNot() {
    return not;
  }

  @Override
  public void setNot(boolean not) {
    this.not=not;
  }

  @Override
  public String getConditionSqlFragment() {
    return null;
  }
}
