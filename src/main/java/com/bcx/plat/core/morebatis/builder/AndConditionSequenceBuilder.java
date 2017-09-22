package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.List;

public class AndConditionSequenceBuilder<PARENT_NODE extends ConditionSequence> extends
    ConditionSequenceBuilder<And, PARENT_NODE, AndConditionSequenceBuilder<PARENT_NODE>> {

  private And and = new And();

  public AndConditionSequenceBuilder(boolean isNot, PARENT_NODE parentNode,
      ConditionBuilderContext conditionBuilderContext) {
    super.parent = parentNode;
    super.conditionBuilderContext = conditionBuilderContext;
    and.setNot(isNot);
  }

  public PARENT_NODE endAnd() {
    parent.append(and);
    return parent;
  }

  @Override
  protected List<Condition> getConditionList() {
    return and.getConditions();
  }
}
