package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.List;

public class OrConditionBuilder<PARENT_NODE extends ConditionContainer> extends ChainConditionBuilder<Or, PARENT_NODE,OrConditionBuilder<PARENT_NODE>>{
  private Or or=new Or();

  public OrConditionBuilder(boolean isNot,PARENT_NODE parentNode,ConditionBuilderContext conditionBuilderContext) {
    super.parent=parentNode;
    super.conditionBuilderContext=conditionBuilderContext;
    or.setNot(isNot);
  }

  public PARENT_NODE endOr(){
    parent.fill(or);
    return parent;
  }

  @Override
  protected List<Condition> getConditionList() {
    return or.getConditions();
  }
}