package com.bcx.plat.core.morebatis.translator;

import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Translator {
    public static final SqlSegment SELECT=new SqlSegment("SELECT");
    public static final SqlSegment AS=new SqlSegment("AS");
    public static final SqlSegment FROM=new SqlSegment("FROM");
    public static final SqlSegment WHERE=new SqlSegment("WHERE");
    public static final SqlSegment AND=new SqlSegment("AND");
    public static final SqlSegment OR=new SqlSegment("OR");
    public static final SqlSegment FALSE=new SqlSegment("FALSE");
    public static final SqlSegment TRUE=new SqlSegment("TRUE");
    public static final SqlSegment DOT=new SqlSegment(".");
    public static final SqlSegment COMMA=new SqlSegment(",");
    public static final SqlSegment ASC=new SqlSegment("ASC");
    public static final SqlSegment DESC=new SqlSegment("DESC");
    public static final SqlSegment ORDER_BY=new SqlSegment("ORDER BY");

    public LinkedList translateQueryAction(QueryAction queryAction,LinkedList linkedList){
        appendSql(SELECT, linkedList);
        explainColumns(queryAction,linkedList);
        linkedList.add(FROM);
        translateTableSource(queryAction.getTableSource(),linkedList);
        if (queryAction.getWhere()!=null) {
            linkedList.add(WHERE);
            translateCondition(queryAction.getWhere(),linkedList);
        }
        final List<Order> orders = queryAction.getOrder();
        if (orders!=null&&!orders.isEmpty()) {
            linkedList.add(ORDER_BY);
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()) {
                final Order order=iterator.next();
                translateOrder(order,linkedList);
                if (iterator.hasNext()) appendSql(COMMA,linkedList);
            }
        }
        return linkedList;
    }
    
    private LinkedList explainColumns(QueryAction queryAction, LinkedList linkedList){
        Iterator<AliasedColumn> iterator = queryAction.getAliasedColumns().iterator();
        while (iterator.hasNext()){
            AliasedColumn aliasedColumn = iterator.next();
            translateAliasedColumn(aliasedColumn,linkedList);
            if (iterator.hasNext()) linkedList.add(COMMA);
        }
        return linkedList;
    }

    public LinkedList translateOrder(Order order, LinkedList linkedList){
        final String alies = order.getAliasedColumn().getAlies();
        if (alies==null) {
            translateFieldSource(order.getAliasedColumn(),linkedList);
        }else {
            appendSql(order.getAlies(),linkedList);
        }
        if (order.getOrder()== Order.DESC) {
            appendSql(DESC,linkedList);
        }else {
            appendSql(ASC,linkedList);
        }
        return linkedList;
    }
    
    //字段系列+++
    
    public LinkedList translateAliasedColumn(AliasedColumn aliasedColumn,LinkedList linkedList){
        translateFieldSource(aliasedColumn,linkedList);
        final String alies = aliasedColumn.getAlies();
        if (alies !=null) {
            appendSql(AS, linkedList);
            appendSql(quoteStr(alies), linkedList);
        }
        return linkedList;
    }

    public LinkedList translateFieldSource(FieldSource fieldSource, LinkedList linkedList){
        if (fieldSource instanceof Field) {
            translateFieldSource((Field) fieldSource, linkedList);
        }
        return linkedList;
    }

    public LinkedList translateFieldSource(Field field, LinkedList linkedList){
        final Table table = field.getTable();
        linkedList.add(new SqlSegment(getTableStr(table)+"."+quoteStr(field.getFieldName())));
        return linkedList;
    }

    public LinkedList translateTableSource(TableSource tableSource,LinkedList linkedList){
        if (tableSource instanceof Table) {
            final Table table=(Table) tableSource;
            linkedList.add(new SqlSegment(getTableStr(table)));
        }
        return linkedList;
    }

    //字段系列---
    
    //条件系列+++

    public LinkedList<Object> translateCondition(Condition condition, LinkedList<Object> list) {
        if (condition instanceof ChainCondition) {
            if (condition instanceof And) {
                translateAnd((And) condition, list);
            } else if (condition instanceof Or) {
                translateOr((Or) condition, list);
            }
        } else if (condition instanceof FieldCondition) {
            final Operator operator = ((FieldCondition) condition).getOperator();
            final Object value = ((FieldCondition) condition).getValue();
            translateFieldSource(((FieldCondition) condition).getField(), list);
            switch (operator) {
                case IN:
                    if (value instanceof Collection) {
                        if (((Collection) value).size() == 0) {
                            //空列表优化
                            if (!condition.isNot()) {
                                appendSql(FALSE, list);
                            }
                        } else {
                            appendSql(condition.isNot() ? "not in" : "in", list);
                            appendArgs(value, list);
                        }
                    } else {
                        //预留给子表查询
                    }
                    break;
                case EQUAL:
                    appendSql(condition.isNot() ? "!=" : "=", list);
                    appendArgs(value, list);
                    break;
                case LIKE_FULL:
                    appendSql(condition.isNot() ? "not like" : "like", list);
                    appendSql("concat('%',", list);
                    appendArgs(value, list);
                    appendSql(",'%')", list);
                    break;
                case LIKE_LEFT:
                    appendSql(condition.isNot() ? "not like" : "like", list);
                    appendSql("concat('%',", list);
                    appendArgs(value, list);
                    appendSql(")", list);
                    break;
                case LIKE_RIGHT:
                    appendSql(condition.isNot() ? "not like" : "like", list);
                    appendSql("concat(", list);
                    appendArgs(value, list);
                    appendSql(",'%')", list);
                    break;
                case BETWEEN:
                    appendSql(condition.isNot() ? "not between" : "between", list);
                    final Object[] values = (Object[]) value;
                    appendArgs(values[0], list);
                    appendSql(AND, list);
                    appendArgs(values[1], list);
                    break;
                case IS_NULL:
                    appendSql(condition.isNot() ? "is not null" : "is null", list);
                    break;
            }
        }
        return list;
    }

    public LinkedList<Object> translateAnd(And andCondition, LinkedList<Object> list) {
        return translateChainCondition(andCondition, list, AND);
    }
  /*条件系列*/

  /*Table系列*/

    public LinkedList<Object> translateOr(Or orCondition, LinkedList<Object> list) {
        return translateChainCondition(orCondition, list, OR);
    }

    private LinkedList<Object> translateChainCondition(ChainCondition chainCondition,
                                                       LinkedList<Object> list, SqlSegment seperator) {
        if (chainCondition.getConditions().size() == 0) {
            return list;
        }
        if (chainCondition.isNot()) {
            appendSql("not", list);
        }
        appendSql("(", list);
        final List<Condition> conditions = chainCondition.getConditions();
        Iterator<Condition> conditionIterator = conditions.iterator();
        boolean notFirst = false;
        // TODO 实现为主 混乱将就
        for (Condition condition : conditions) {
            if (notFirst) {
                if (condition instanceof ChainCondition) {
                    if (!((ChainCondition) condition).getConditions().isEmpty()) {
                        appendSql(seperator, list);
                    }
                } else {
                    appendSql(seperator, list);
                }
            } else {
                notFirst = true;
            }
            translateCondition(condition, list);
        }
        appendSql(")", list);
        return list;
    }

    //条件系列---

    /**
     * 工具方法
     */

    private String quoteStr(String str){
        return '"'+str+'"';
    }

    private String getTableStr(Table table) {
        String schema = table.getSchema();
        String strSource = schema == null || schema.isEmpty() ?
                quoteStr(table.getTableName())
                :quoteStr(table.getSchema()) + "." + quoteStr(table.getTableName());
        return strSource;
    }


    private LinkedList appendSql(String sqlSegment, LinkedList list){
        return appendSql(new SqlSegment(sqlSegment), list);
    }

    private LinkedList appendSql(SqlSegment sqlSegment, LinkedList list){
        list.add(sqlSegment);
        return list;
    }

    private LinkedList appendArgs(Object args, LinkedList list){
        list.add(args);
        return list;
    }
    /**
     * 工具方法
     */
}
