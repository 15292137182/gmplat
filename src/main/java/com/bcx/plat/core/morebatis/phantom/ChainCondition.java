package com.bcx.plat.core.morebatis.phantom;

import java.util.List;

public interface ChainCondition<T extends Condition<T>> extends Condition<T> {
  List<Condition> getConditions();

  void setConditions(List<Condition> conditions);
}
