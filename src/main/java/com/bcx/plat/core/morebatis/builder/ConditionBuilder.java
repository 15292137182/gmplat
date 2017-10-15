package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.SpringContextHolder;

public class ConditionBuilder implements ConditionSequence {

  private Condition condition;

  private ConditionBuilderContext conditionBuilderContext;

//  private static final MoreBatis moreBatis = SpringContextHolder.getBean("moreBatis");

  public ConditionBuilder(Class<? extends BeanInterface> entityClass,
      String defaultMapColumnAlias) {
    conditionBuilderContext = new ConditionBuilderContext(entityClass);
  }

  public ConditionBuilder(Class<? extends BeanInterface> entityClass) {
    conditionBuilderContext = new ConditionBuilderContext(entityClass);
  }

  public static void main(String[] args) throws IllegalAccessException {
    Condition testCondition = new ConditionBuilder(BusinessObject.class).and()
        .equal("changeOperat", 10).notNull("changeOperat").or()
        .or().isNull("deleteFlag").endOr().equal("deleteFlag", BaseConstants.NOT_DELETE_FLAG)
        .endOr()
        .endAnd().buildDone();
    testCondition.hashCode();
  }

  @Override
  public void append(Condition condition) {
    this.condition = condition;
  }

  public AndConditionSequenceBuilder<ConditionBuilder> and() {
    return new AndConditionSequenceBuilder<>(false, this, conditionBuilderContext);
  }

  public AndConditionSequenceBuilder<ConditionBuilder> andNot() {
    return new AndConditionSequenceBuilder<>(true, this, conditionBuilderContext);
  }

  public OrConditionSequenceBuilder<ConditionBuilder> or() {
    return new OrConditionSequenceBuilder<>(false, this, conditionBuilderContext);
  }

  public OrConditionSequenceBuilder<ConditionBuilder> orNot() {
    return new OrConditionSequenceBuilder<>(true, this, conditionBuilderContext);
  }

  public Condition buildDone() {
    return condition;
  }
}