package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.morebatis.phantom.Condition;

public interface ConditionSequence {
  void append(Condition condition);
}
