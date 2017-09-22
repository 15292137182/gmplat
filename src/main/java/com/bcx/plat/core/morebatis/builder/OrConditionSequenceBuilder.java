package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.List;

public class OrConditionSequenceBuilder<PARENT_NODE extends ConditionSequence> extends
    ConditionSequenceBuilder<Or, PARENT_NODE, OrConditionSequenceBuilder<PARENT_NODE>> {

  private Or or = new Or();

  public OrConditionSequenceBuilder(boolean isNot, PARENT_NODE parentNode,
      ConditionBuilderContext conditionBuilderContext) {
    super.parent = parentNode;
    super.conditionBuilderContext = conditionBuilderContext;
    or.setNot(isNot);
  }

  public PARENT_NODE endOr() {
    parent.append(or);
    return parent;
  }

  @Override
  protected List<Condition> getConditionList() {
    return or.getConditions();
  }
}