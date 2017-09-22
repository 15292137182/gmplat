package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.SubAttribute;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.LinkedList;

public class OrderBuilder {

  private Class entityClass;

  private static final MoreBatis moreBatis = SpringContextHolder.getBean("moreBatis");

  private LinkedList<Order> orders = new LinkedList<>();

  public OrderBuilder(Class entityClass) {
    this.entityClass = entityClass;
  }

  public OrderBuilder asc(String alias) {
    orders.add(new Order(moreBatis.getColumnOrEtcByAlias(entityClass, alias), Order.ASC));
    return this;
  }

  public OrderBuilder asc(FieldSource fieldSource) {
    orders.add(new Order(fieldSource, Order.ASC));
    return this;
  }

  public OrderBuilder asc(Class entityClass, String alias) {
    orders.add(new Order(moreBatis.getColumnOrEtcByAlias(entityClass, alias), Order.ASC));
    return this;
  }

  public OrderBuilder asc(Class entityClass, String alias, String attribute) {
    orders.add(new Order(subAttribute(entityClass, alias, attribute), Order.ASC));
    return this;
  }

  public OrderBuilder desc(String alias) {
    orders.add(new Order(moreBatis.getColumnOrEtcByAlias(entityClass, alias), Order.DESC));
    return this;
  }

  public OrderBuilder desc(FieldSource fieldSource) {
    orders.add(new Order(fieldSource, Order.DESC));
    return this;
  }

  public OrderBuilder desc(Class entityClass, String alias) {
    orders.add(new Order(moreBatis.getColumnOrEtcByAlias(entityClass, alias), Order.DESC));
    return this;
  }


  public OrderBuilder desc(Class entityClass, String alias, String attribute) {
    orders.add(new Order(subAttribute(entityClass, alias, attribute), Order.DESC));
    return this;
  }

  private Field getNormalColumnByAlias(Class entityClass, String alias) {
    return moreBatis.getColumnByAlias(entityClass, alias);
  }

  private SubAttribute subAttribute(Class entityClass, String alias, String attribite) {
    return new SubAttribute(getNormalColumnByAlias(entityClass, alias), attribite);
  }

  public LinkedList<Order> done() {
    return orders;
  }


}
