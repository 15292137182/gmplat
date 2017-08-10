package com.bcx.plat.core.morebatis.phantom;

import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Or;
import java.util.LinkedList;

public interface ConditionTranslator {
  public LinkedList<Object> translate(Condition condition);
}
