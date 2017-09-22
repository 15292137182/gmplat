package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.SubAttribute;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.Collection;
import java.util.List;

public abstract class ConditionSequenceBuilder<CONDITION extends ChainCondition<CONDITION>, PARENT_NODE extends ConditionSequence, CURRENT_NODE extends ConditionSequence> implements
    ConditionSequence {

  protected ConditionBuilderContext conditionBuilderContext;

  protected PARENT_NODE parent;

  private static final MoreBatis moreBatis = SpringContextHolder.getBean("moreBatis");

  public CURRENT_NODE equal(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, Object value) {
    return equal(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE equal(Class<? extends BeanInterface> entityClass, String alias,
      Object value) {
    return equal(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE equal(String alias, Object value) {
    return equal(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }

  public CURRENT_NODE in(Class<? extends BeanInterface> entityClass, String alias, String attribute,
      Collection value) {
    return in(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE in(Class<? extends BeanInterface> entityClass, String alias,
      Collection value) {
    return in(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE in(String alias, Collection value) {
    return in(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }

  public CURRENT_NODE in(Class<? extends BeanInterface> entityClass, String alias, String attribute,
      QueryAction value) {
    return in(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE in(Class<? extends BeanInterface> entityClass, String alias,
      QueryAction value) {
    return in(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE in(String alias, QueryAction value) {
    return in(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }


  public CURRENT_NODE between(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, Object rangeStart, Object rangeEnd) {
    return between(getJsonAttributeColumnByAlias(entityClass, alias, attribute), rangeStart,
        rangeEnd);
  }

  public CURRENT_NODE between(Class<? extends BeanInterface> entityClass, String alias,
      Object rangeStart, Object rangeEnd) {
    return between(getNormalColumnByAlias(entityClass, alias), rangeStart, rangeEnd);
  }

  public CURRENT_NODE between(String alias, Object rangeStart, Object rangeEnd) {
    return between(getColumnByAlias(conditionBuilderContext.getClz(), alias), rangeStart, rangeEnd);
  }


  public CURRENT_NODE like(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, String value) {
    return like(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE like(Class<? extends BeanInterface> entityClass, String alias, String value) {
    return like(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE like(String alias, String value) {
    return like(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }


  public CURRENT_NODE endWith(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, String value) {
    return endWith(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE endWith(Class<? extends BeanInterface> entityClass, String alias,
      String value) {
    return endWith(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE endWith(String alias, String value) {
    return endWith(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }


  public CURRENT_NODE startWith(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, String value) {
    return startWith(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE startWith(Class<? extends BeanInterface> entityClass, String alias,
      String value) {
    return startWith(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE startWith(String alias, String value) {
    return startWith(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }


  public CURRENT_NODE isNull(Class<? extends BeanInterface> entityClass, String alias,
      String attribute) {
    return isNull(getJsonAttributeColumnByAlias(entityClass, alias, attribute));
  }

  public CURRENT_NODE isNull(Class<? extends BeanInterface> entityClass, String alias) {
    return isNull(getNormalColumnByAlias(entityClass, alias));
  }

  public CURRENT_NODE isNull(String alias) {
    return isNull(getColumnByAlias(conditionBuilderContext.getClz(), alias));
  }


  public CURRENT_NODE notEqual(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, Object value) {
    return notEqual(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE notEqual(Class<? extends BeanInterface> entityClass, String alias,
      Object value) {
    return notEqual(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE notEqual(String alias, Object value) {
    return notEqual(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }


  public CURRENT_NODE notIn(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, Collection value) {
    return notIn(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE notIn(Class<? extends BeanInterface> entityClass, String alias,
      Collection value) {
    return notIn(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE notIn(String alias, Collection value) {
    return notIn(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }

  public CURRENT_NODE notIn(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, QueryAction value) {
    return notIn(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE notIn(Class<? extends BeanInterface> entityClass, String alias,
      QueryAction value) {
    return notIn(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE notIn(String alias, QueryAction value) {
    return notIn(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }


  private CURRENT_NODE equal(FieldSource FieldSource, Object value) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.EQUAL, value));
  }

  private CURRENT_NODE in(FieldSource FieldSource, Collection value) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.IN, value));
  }

  private CURRENT_NODE in(FieldSource FieldSource, QueryAction value) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.IN, value));
  }

  private CURRENT_NODE between(FieldSource FieldSource, Object rangeStart, Object rangeEnd) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  private CURRENT_NODE like(FieldSource FieldSource, String value) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.LIKE_FULL, value));
  }

  private CURRENT_NODE endWith(FieldSource FieldSource, String value) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.LIKE_LEFT, value));
  }


  private CURRENT_NODE startWith(FieldSource FieldSource, String value) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.LIKE_RIGHT, value));
  }

  private CURRENT_NODE isNull(FieldSource FieldSource) {
    return addCondition(
        new FieldCondition(FieldSource, Operator.IS_NULL, null));
  }

  private CURRENT_NODE notEqual(FieldSource FieldSource, Object value) {
    return addNotCondition(
        new FieldCondition(FieldSource, Operator.EQUAL, value));
  }

  private CURRENT_NODE notIn(FieldSource FieldSource, Collection value) {
    return notIn(FieldSource, value);
  }

  private CURRENT_NODE notIn(FieldSource FieldSource, QueryAction value) {
    return notIn(FieldSource, value);
  }


  private CURRENT_NODE notBetween(FieldSource FieldSource, Object rangeStart, Object rangeEnd) {
    return addNotCondition(
        new FieldCondition(FieldSource, Operator.BETWEEN,
            new Object[]{rangeStart, rangeEnd}));
  }

  public CURRENT_NODE notBetween(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, Object rangeStart, Object rangeEnd) {
    return notBetween(getJsonAttributeColumnByAlias(entityClass, alias, attribute), rangeStart,
        rangeEnd);
  }

  public CURRENT_NODE notBetween(Class<? extends BeanInterface> entityClass, String alias,
      Object rangeStart, Object rangeEnd) {
    return notBetween(getNormalColumnByAlias(entityClass, alias), rangeStart, rangeEnd);
  }

  public CURRENT_NODE notBetween(String alias, Object rangeStart, Object rangeEnd) {
    return notBetween(getColumnByAlias(conditionBuilderContext.getClz(), alias), rangeStart,
        rangeEnd);
  }

  private CURRENT_NODE notLike(FieldSource FieldSource, String value) {
    return addNotCondition(
        new FieldCondition(FieldSource, Operator.LIKE_FULL, value));
  }

  public CURRENT_NODE notLike(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, String value) {
    return notLike(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE notLike(Class<? extends BeanInterface> entityClass, String alias,
      String value) {
    return notLike(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE notLike(String alias, String value) {
    return notLike(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }

  private CURRENT_NODE notEndWith(FieldSource FieldSource, String value) {
    return notEndWith(FieldSource, value);
  }

  public CURRENT_NODE notEndWith(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, String value) {
    return notEndWith(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE notEndWith(Class<? extends BeanInterface> entityClass, String alias,
      String value) {
    return notEndWith(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE notEndWith(String alias, String value) {
    return notEndWith(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }

  private CURRENT_NODE notStartWith(FieldSource FieldSource, String value) {
    return notStartWith(FieldSource, value);
  }

  public CURRENT_NODE notStartWith(Class<? extends BeanInterface> entityClass, String alias,
      String attribute, String value) {
    return notStartWith(getJsonAttributeColumnByAlias(entityClass, alias, attribute), value);
  }

  public CURRENT_NODE notStartWith(Class<? extends BeanInterface> entityClass, String alias,
      String value) {
    return notStartWith(getNormalColumnByAlias(entityClass, alias), value);
  }

  public CURRENT_NODE notStartWith(String alias, String value) {
    return notStartWith(getColumnByAlias(conditionBuilderContext.getClz(), alias), value);
  }

  private CURRENT_NODE notNull(FieldSource FieldSource) {
    return addNotCondition(
        new FieldCondition(FieldSource, Operator.IS_NULL, null));
  }

  public CURRENT_NODE notNull(Class<? extends BeanInterface> entityClass, String alias) {
    return notNull(getNormalColumnByAlias(entityClass, alias));
  }

  public CURRENT_NODE notNull(String alias) {
    return notNull(getColumnByAlias(conditionBuilderContext.getClz(), alias));
  }

  public AndConditionSequenceBuilder<CURRENT_NODE> and() {
    return new AndConditionSequenceBuilder<>(false, (CURRENT_NODE) this,
        conditionBuilderContext);
  }

  public AndConditionSequenceBuilder<CURRENT_NODE> andNot() {
    return new AndConditionSequenceBuilder<>(true, (CURRENT_NODE) this,
        conditionBuilderContext);
  }

  public OrConditionSequenceBuilder<CURRENT_NODE> or() {
    return new OrConditionSequenceBuilder<>(false, (CURRENT_NODE) this,
        conditionBuilderContext);
  }

  public OrConditionSequenceBuilder<CURRENT_NODE> orNot() {
    return new OrConditionSequenceBuilder<>(true, (CURRENT_NODE) this, conditionBuilderContext);
  }

  public CURRENT_NODE addCondition(Condition condition) {
    getConditionList().add(condition);
    return (CURRENT_NODE) this;
  }

  private CURRENT_NODE addNotCondition(Condition condition) {
    return addCondition(condition.not());
  }


  private FieldSource getNormalColumnByAlias(Class<? extends BeanInterface> entityClass,
      String alias) {
    return moreBatis.getColumnByAlias(entityClass, alias);
  }

  private FieldSource getJsonAttributeColumnByAlias(Class<? extends BeanInterface> entityClass,
      String alias, String attribute) {
    return new SubAttribute(getNormalColumnByAlias(entityClass, alias), attribute);
  }

  private FieldSource getColumnByAlias(Class<? extends BeanInterface> entityClass, String alias) {
    return moreBatis.getColumnOrEtcByAlias(entityClass, alias);
  }

  @Override
  public void append(Condition condition) {
    getConditionList().add(condition);
  }

  protected abstract List<Condition> getConditionList();
}