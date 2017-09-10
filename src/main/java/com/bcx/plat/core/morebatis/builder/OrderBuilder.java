package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.utils.SpringContextHolder;

import java.util.LinkedList;
import java.util.Map;

public class OrderBuilder {
    private Class<? extends BeanInterface> entityClass;

    private static final MoreBatis moreBatis= SpringContextHolder.getBean("moreBatis");

    private LinkedList<Order> orders=new LinkedList<>();

    public OrderBuilder(Class<? extends BeanInterface> entityClass) {
        this.entityClass=entityClass;
    }

    public OrderBuilder asc(String alias){
        return asc(entityClass,alias);
    }

    public OrderBuilder asc(Class<? extends BeanInterface> entityClass,String alias){
        orders.add(new Order(moreBatis.getColumnByAlias(entityClass,alias),Order.ASC));
        return this;
    }

    public OrderBuilder desc(String alias){
        return desc(entityClass,alias);
    }

    public OrderBuilder desc(Class<? extends BeanInterface> entityClass,String alias){
        orders.add(new Order(moreBatis.getColumnByAlias(entityClass,alias),Order.DESC));
        return this;
    }

    public LinkedList<Order> done(){
        return orders;
    }


}
