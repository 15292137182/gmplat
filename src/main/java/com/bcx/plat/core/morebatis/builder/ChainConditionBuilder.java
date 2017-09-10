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

  public CURRENT_NODE equal(Class<? extends BeanInterface> entityClass,String alies, Object value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.EQUAL, value));
  }

  public CURRENT_NODE equal(String alies, Object value){
    return equal(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE in(Class<? extends BeanInterface> entityClass,String alies, Collection value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.IN, value));
  }

  public CURRENT_NODE in(String alies, Collection value) {
    return in(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE between(Class<? extends BeanInterface> entityClass,String alies, Object rangeStart, Object rangeEnd) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  public CURRENT_NODE between(String alies, Object rangeStart, Object rangeEnd){
    return between(conditionBuilderContext.getClz(),alies,rangeStart,rangeEnd);
  }

  public CURRENT_NODE like(Class<? extends BeanInterface> entityClass,String alies, String value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.LIKE_FULL, value));
  }

  public CURRENT_NODE like(String alies, String value) {
    return like(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE endWith(Class<? extends BeanInterface> entityClass,String alies, String value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.LIKE_LEFT, value));
  }

  public CURRENT_NODE endWith(String alies, String value) {
    return endWith(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE startWith(Class<? extends BeanInterface> entityClass,String alies, String value) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.LIKE_RIGHT, value));
  }

  public CURRENT_NODE startWith(String alies, String value) {
    return startWith(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE isNull(Class<? extends BeanInterface> entityClass,String alies) {
    return addCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.IS_NULL, null));
  }

  public CURRENT_NODE isNull(String alies) {
    return isNull(conditionBuilderContext.getClz(),alies);
  }

  public CURRENT_NODE notEqual(Class<? extends BeanInterface> entityClass,String alies, Object value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.EQUAL, value));
  }

  public CURRENT_NODE notEqual(String alies, Object value) {
    return notEqual(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE notIn(Class<? extends BeanInterface> entityClass,String alies, Collection value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.IN, value));
  }

  public CURRENT_NODE notIn(String alies, Collection value) {
    return notIn(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE notBetween(Class<? extends BeanInterface> entityClass,String alies, Object rangeStart, Object rangeEnd) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  public CURRENT_NODE notBetween(String alies, Object rangeStart, Object rangeEnd) {
    return notBetween(conditionBuilderContext.getClz(),alies,rangeStart,rangeEnd);
  }

  public CURRENT_NODE notLike(Class<? extends BeanInterface> entityClass,String alies, String value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.LIKE_FULL, value));
  }

  public CURRENT_NODE notLike(String alies, String value) {
    return notLike(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE notEndWith(Class<? extends BeanInterface> entityClass,String alies, String value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.LIKE_LEFT, value));
  }

  public CURRENT_NODE notEndWith(String alies, String value) {
    return notEndWith(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE notStartWith(Class<? extends BeanInterface> entityClass,String alies, String value) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.LIKE_RIGHT, value));
  }

  public CURRENT_NODE notStartWith(String alies, String value) {
    return notStartWith(conditionBuilderContext.getClz(),alies,value);
  }

  public CURRENT_NODE notNull(Class<? extends BeanInterface> entityClass,String alies) {
    return addNotCondition(
        new FieldCondition(moreBatis.getColumnByAlias(entityClass,alies), Operator.IS_NULL, null));
  }

  public CURRENT_NODE notNull(String alies) {
    return notNull(conditionBuilderContext.getClz(),alies);
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