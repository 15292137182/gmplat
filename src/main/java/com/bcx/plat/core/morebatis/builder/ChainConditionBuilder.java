package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.Collection;
import java.util.List;

public abstract class ChainConditionBuilder<CONDITION extends ChainCondition<CONDITION>, PARENT_NODE extends ConditionContainer, CURRENT_NODE extends ConditionContainer> implements
    ConditionContainer {

  protected ConditionBuilderContext conditionBuilderContext;

  protected PARENT_NODE parent;

  private static final MoreBatis moreBatis= SpringContextHolder.getBean("moreBatis");

  //许多条件放这里 记得返回当前节点的类型

  public CURRENT_NODE equal(Class<? extends BeanInterface> entityClass,String alias, Object value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.EQUAL, value));
  }

  public CURRENT_NODE equal(String alias, Object value){
    return equal(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE in(Class<? extends BeanInterface> entityClass,String alias, Collection value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.IN, value));
  }

  public CURRENT_NODE in(String alias, Collection value) {
    return in(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE between(Class<? extends BeanInterface> entityClass,String alias, Object rangeStart, Object rangeEnd) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  public CURRENT_NODE between(String alias, Object rangeStart, Object rangeEnd){
    return between(conditionBuilderContext.getClz(),alias,rangeStart,rangeEnd);
  }

  public CURRENT_NODE like(Class<? extends BeanInterface> entityClass,String alias, String value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.LIKE_FULL, value));
  }

  public CURRENT_NODE like(String alias, String value) {
    return like(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE endWith(Class<? extends BeanInterface> entityClass,String alias, String value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.LIKE_LEFT, value));
  }

  public CURRENT_NODE endWith(String alias, String value) {
    return endWith(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE startWith(Class<? extends BeanInterface> entityClass,String alias, String value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.LIKE_RIGHT, value));
  }

  public CURRENT_NODE startWith(String alias, String value) {
    return startWith(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE isNull(Class<? extends BeanInterface> entityClass,String alias) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.IS_NULL, null));
  }

  public CURRENT_NODE isNull(String alias) {
    return isNull(conditionBuilderContext.getClz(),alias);
  }

  public CURRENT_NODE notEqual(Class<? extends BeanInterface> entityClass,String alias, Object value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.EQUAL, value));
  }

  public CURRENT_NODE notEqual(String alias, Object value) {
    return notEqual(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE notIn(Class<? extends BeanInterface> entityClass,String alias, Collection value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.IN, value));
  }

  public CURRENT_NODE notIn(String alias, Collection value) {
    return notIn(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE notBetween(Class<? extends BeanInterface> entityClass,String alias, Object rangeStart, Object rangeEnd) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  public CURRENT_NODE notBetween(String alias, Object rangeStart, Object rangeEnd) {
    return notBetween(conditionBuilderContext.getClz(),alias,rangeStart,rangeEnd);
  }

  public CURRENT_NODE notLike(Class<? extends BeanInterface> entityClass,String alias, String value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.LIKE_FULL, value));
  }

  public CURRENT_NODE notLike(String alias, String value) {
    return notLike(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE notEndWith(Class<? extends BeanInterface> entityClass,String alias, String value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.LIKE_LEFT, value));
  }

  public CURRENT_NODE notEndWith(String alias, String value) {
    return notEndWith(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE notStartWith(Class<? extends BeanInterface> entityClass,String alias, String value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.LIKE_RIGHT, value));
  }

  public CURRENT_NODE notStartWith(String alias, String value) {
    return notStartWith(conditionBuilderContext.getClz(),alias,value);
  }

  public CURRENT_NODE notNull(Class<? extends BeanInterface> entityClass,String alias) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alias), Operator.IS_NULL, null));
  }

  public CURRENT_NODE notNull(String alias) {
    return notNull(conditionBuilderContext.getClz(),alias);
  }

  public AndConditionBuilder<CURRENT_NODE> and() {
    return new AndConditionBuilder<>(false, (CURRENT_NODE) this,
            conditionBuilderContext);
  }

  public AndConditionBuilder<CURRENT_NODE> andNot() {
    return new AndConditionBuilder<>(true, (CURRENT_NODE) this,
            conditionBuilderContext);
  }

  public OrConditionBuilder<CURRENT_NODE> or() {
    return new OrConditionBuilder<>(false, (CURRENT_NODE) this,
            conditionBuilderContext);
  }

  public OrConditionBuilder<CURRENT_NODE> orNot() {
    return new OrConditionBuilder<>(true, (CURRENT_NODE) this, conditionBuilderContext);
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