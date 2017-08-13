package com.bcx.plat.core.morebatis.phantom;

import java.util.LinkedList;

public interface ConditionTranslator {
  public LinkedList<Object> translate(Condition condition);
}
