package com.bcx.plat.core.morebatis.substance.condition;

import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.List;

public class Not implements Condition {

  List<Condition> conditions;

  @Override
  public String getConditionSqlFragment() {
    return null;
  }
}
