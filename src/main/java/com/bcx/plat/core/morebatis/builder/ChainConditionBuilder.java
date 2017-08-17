package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.Collection;
import java.util.List;

public abstract class ChainConditionBuilder<CONDITION extends ChainCondition<CONDITION>, PARENT_NODE extends ConditionContainer, CURRENT_NODE extends ConditionContainer> implements
    ConditionContainer {

  protected ConditionBuilderContext conditionBuilderContext;
  protected PARENT_NODE parent;

  //许多条件放这里 记得返回当前节点的类型
  public CURRENT_NODE equal(String alies, Object value) {
    return addCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.EQUAL, value));
  }

  public CURRENT_NODE in(String alies, Collection value) {
    return addCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.IN, value));
  }

  public CURRENT_NODE between(String alies, Object rangeStart, Object rangeEnd) {
    return addCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  public CURRENT_NODE like(String alies, String value) {
    return addCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.LIKE_FULL, value));
  }

  public CURRENT_NODE leftLike(String alies, String value) {
    return addCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.LIKE_LEFT, value));
  }

  public CURRENT_NODE rightLike(String alies, String value) {
    return addCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.LIKE_RIGHT, value));
  }

  public CURRENT_NODE isNull(String alies) {
    return addCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.IS_NULL, null));
  }

  //许多条件放这里 记得返回当前节点的类型
  public CURRENT_NODE notEqual(String alies, Object value) {
    return addNotCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.EQUAL, value));
  }

  public CURRENT_NODE notIn(String alies, Collection value) {
    return addNotCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.IN, value));
  }

  public CURRENT_NODE notBetween(String alies, Object rangeStart, Object rangeEnd) {
    return addNotCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  public CURRENT_NODE notLike(String alies, String value) {
    return addNotCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.LIKE_FULL, value));
  }

  public CURRENT_NODE notLeftLike(String alies, String value) {
    return addNotCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.LIKE_LEFT, value));
  }

  public CURRENT_NODE notRightLike(String alies, String value) {
    return addNotCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.LIKE_RIGHT, value));
  }

  public CURRENT_NODE notNull(String alies) {
    return addNotCondition(
        new FieldCondition(conditionBuilderContext.getColumns(alies), Operator.IS_NULL, null));
  }

  public AndConditionBuilder<CURRENT_NODE> and() {
    return new AndConditionBuilder<CURRENT_NODE>(false, (CURRENT_NODE) this,
        conditionBuilderContext);
  }

  public AndConditionBuilder<CURRENT_NODE> andNot() {
    return new AndConditionBuilder<CURRENT_NODE>(true, (CURRENT_NODE) this,
        conditionBuilderContext);
  }

  public OrConditionBuilder<CURRENT_NODE> or() {
    return new OrConditionBuilder<CURRENT_NODE>(false, (CURRENT_NODE) this,
        conditionBuilderContext);
  }

  public OrConditionBuilder<CURRENT_NODE> orNot() {
    return new OrConditionBuilder<CURRENT_NODE>(true, (CURRENT_NODE) this, conditionBuilderContext);
  }

  public CURRENT_NODE addCondition(Condition condition) {
    getConditionList().add(condition);
    return (CURRENT_NODE) this;
  }

  private CURRENT_NODE addNotCondition(Condition condition) {
    return addCondition(condition.not());
  }

  @Override
  public void fill(Condition condition) {
    getConditionList().add(condition);
  }

  protected abstract List<Condition> getConditionList();
}
