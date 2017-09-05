package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.phantom.Condition;

public class ConditionBuilder implements ConditionContainer {

  private Condition condition;
  private ConditionBuilderContext conditionBuilderContext;

  public ConditionBuilder(Class<? extends BeanInterface> entityClass) {
    conditionBuilderContext = new ConditionBuilderContext(entityClass);
  }

  public static void main(String[] args) throws IllegalAccessException {
    Condition testCondition = new ConditionBuilder(BusinessObject.class).and()
        .equal("changeOperat", 10).notNull("changeOperat").or()
        .or().isNull("deleteFlag").endOr().equal("deleteFlag", BaseConstants.NOT_DELETE_FLAG).endOr()
        .endAnd().buildDone();
    testCondition.hashCode();
  }

  @Override
  public void fill(Condition condition) {
    this.condition = condition;
  }

  public AndConditionBuilder<ConditionBuilder> and() {
    return new AndConditionBuilder<>(false, this, conditionBuilderContext);
  }

  public AndConditionBuilder<ConditionBuilder> andNot() {
    return new AndConditionBuilder<>(true, this, conditionBuilderContext);
  }

  public OrConditionBuilder<ConditionBuilder> or() {
    return new OrConditionBuilder<>(false, this, conditionBuilderContext);
  }

  public OrConditionBuilder<ConditionBuilder> orNot() {
    return new OrConditionBuilder<>(true, this, conditionBuilderContext);
  }

  public Condition buildDone() {
    return condition;
  }
}
