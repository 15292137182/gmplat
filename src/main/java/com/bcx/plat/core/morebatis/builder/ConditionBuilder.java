package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.phantom.Condition;

public class ConditionBuilder implements ConditionContainer {

  private Condition condition;
  private ConditionBuilderContext conditionBuilderContext;

  public ConditionBuilder(Class<? extends BaseEntity> entityClass) {
    conditionBuilderContext = new ConditionBuilderContext(entityClass);
  }

  public static void main(String[] args) {
    new ConditionBuilder(BusinessObject.class).buildByAnd().equal("it works", "what").endAnd()
        .buildDone();
    Condition testCondition = new ConditionBuilder(BusinessObject.class).buildByAnd()
        .equal("changeOperat", 10).notNull("changeOperat")
        .or().isNull("deleteFlag").equal("deleteFlag", BaseConstants.NOT_DELETE_FLAG).endOr()
        .endAnd().buildDone();
    testCondition.hashCode();
  }

  @Override
  public void fill(Condition condition) {
    this.condition = condition;
  }

  public AndConditionBuilder<ConditionBuilder> buildByAnd() {
    return new AndConditionBuilder<>(false, this, conditionBuilderContext);
  }

  public AndConditionBuilder<ConditionBuilder> buildByAndNot() {
    return new AndConditionBuilder<>(true, this, conditionBuilderContext);
  }

  public OrConditionBuilder<ConditionBuilder> buildByOr() {
    return new OrConditionBuilder<>(false, this, conditionBuilderContext);
  }

  public OrConditionBuilder<ConditionBuilder> buildByOrNot() {
    return new OrConditionBuilder<>(true, this, conditionBuilderContext);
  }

  public Condition buildDone() {
    return condition;
  }
}
