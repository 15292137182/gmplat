package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.List;

public class AndConditionBuilder<PARENT_NODE extends ConditionContainer> extends ChainConditionBuilder<And, PARENT_NODE,AndConditionBuilder<PARENT_NODE>>{
  private And and=new And();

  public AndConditionBuilder(boolean isNot,PARENT_NODE parentNode,ConditionBuilderContext conditionBuilderContext) {
    super.parent=parentNode;
    super.conditionBuilderContext=conditionBuilderContext;
    and.setNot(isNot);
  }

  public PARENT_NODE endAnd(){
    parent.fill(and);
    return parent;
  }

  @Override
  protected List<Condition> getConditionList() {
    return and.getConditions();
  }
}
