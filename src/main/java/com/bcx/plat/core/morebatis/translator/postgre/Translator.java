package com.bcx.plat.core.morebatis.translator.postgre;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.SubAttribute;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.component.function.SqlFunction;
import com.bcx.plat.core.morebatis.phantom.Aliased;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Translator implements SqlComponentTranslator {

  private MoreBatis moreBatis;

  private static final SqlSegment CAST_MAP_SEGMENT_1 = new SqlSegment("(CASE when jsonb_typeof(");
  private static final SqlSegment CAST_MAP_SEGMENT_2 = new SqlSegment(")='object' THEN");
  private static final SqlSegment CAST_MAP_SEGMENT_3 = new SqlSegment(" ELSE '{}'::jsonb END)||");

  private final PostgreFunctionResolution functionResolution = new PostgreFunctionResolution(this);

  private MoreBatis getMoreBatis() {
    if (moreBatis == null) {
      moreBatis = SpringContextHolder.getBean("moreBatis");
    }
    return moreBatis;
  }

  public LinkedList translateQueryAction(QueryAction queryAction, LinkedList linkedList) {
    appendSql(SqlTokens.SELECT, linkedList);
    if (queryAction.getDistinct()) {
      appendSql(SqlTokens.DISTINCT, linkedList);
    }
    translateColumns(queryAction, linkedList);
    final TableSource tableSource = queryAction.getTableSource();
    if (tableSource==null) {
      return linkedList;
    }
    linkedList.add(SqlTokens.FROM);
    translateTableSource(tableSource, linkedList);
    Condition where = queryAction.getWhere();
    if (where != null) {
      if (!((where instanceof ChainCondition)
          && ((ChainCondition) where).getConditions().size() == 0)) {
        linkedList.add(SqlTokens.WHERE);
      }
      translateCondition(where, linkedList);
    }

    List<FieldSource> group = queryAction.getGroup();
    if (group != null && group.size() > 0) {
      appendSql(SqlTokens.GROUP_BY, linkedList);
      Iterator<FieldSource> groups = group.iterator();
      while (groups.hasNext()) {
        FieldSource fieldSource = groups.next();
        translateFieldSource(fieldSource, linkedList);
        appendSql(SqlTokens.COMMA, linkedList);
      }
      if (linkedList.getLast() == SqlTokens.COMMA) {
        linkedList.removeLast();
      }
    }

    final List<Order> orders = queryAction.getOrder();
    if (orders != null && !orders.isEmpty()) {
      linkedList.add(SqlTokens.ORDER_BY);
      Iterator<Order> iterator = orders.iterator();
      while (iterator.hasNext()) {
        final Order order = iterator.next();
        translateOrder(order, linkedList);
        appendSql(SqlTokens.COMMA, linkedList);
      }
      if (linkedList.getLast() == SqlTokens.COMMA) {
        linkedList.removeLast();
      }
    }
    return linkedList;
  }

  private LinkedList translateColumns(QueryAction queryAction, LinkedList linkedList) {
    Iterator<AliasedColumn> iterator = queryAction.getAliasedColumns().iterator();
    while (iterator.hasNext()) {
      AliasedColumn aliasedColumn = iterator.next();
      translateAliasedColumn(aliasedColumn, linkedList);
      if (iterator.hasNext()) {
        linkedList.add(SqlTokens.COMMA);
      }
    }
    return linkedList;
  }

  public LinkedList translateDeleteAction(DeleteAction deleteAction, LinkedList linkedList) {
    appendSql(SqlTokens.DELETE, linkedList);
    appendSql(SqlTokens.FROM, linkedList);
    translateTable((Table) deleteAction.getTableSource(), linkedList);
    appendSql(SqlTokens.WHERE, linkedList);
    translateCondition(deleteAction.getWhere(), linkedList);
    return linkedList;
  }

  public LinkedList translateUpdateAction(UpdateAction updateAction, LinkedList linkedList) {
    appendSql(SqlTokens.UPDATE, linkedList);
    translateTable((Table) updateAction.getTableSource(), linkedList);
    appendSql(SqlTokens.SET, linkedList);
    Class<? extends BeanInterface> entityClass = updateAction.getEntityClass();
    Iterator entryIterator = updateAction.getValues().entrySet()
        .iterator();
    final MoreBatis moreBatis = getMoreBatis();
    while (entryIterator.hasNext()) {
      final Map.Entry entry = (Entry) entryIterator.next();
      FieldSource field;
      try {
        field=moreBatis.getColumnByAlias(entityClass, (String) entry.getKey());
        translateFieldSourceWithoutTable(field, linkedList);
      } catch (NullPointerException e) {
        continue;
      }
      appendSql(SqlTokens.EQUAL, linkedList);
      if (entry.getValue() instanceof Map) {
        mergeMap(field, linkedList);
      }
      appendArgs(entry.getValue(), linkedList);
      appendSql(SqlTokens.COMMA, linkedList);
    }
    if (linkedList.getLast() == SqlTokens.COMMA) {
      linkedList.removeLast();
    }
    final Condition where = updateAction.getWhere();
    if (where==null) return linkedList;
    appendSql(SqlTokens.WHERE, linkedList);
    translateCondition(where, linkedList);
    return linkedList;
  }

  private LinkedList translateTableSource(TableSource t) {
    return translateTableSource(t, new LinkedList());
  }

  public LinkedList translateInsertAction(InsertAction insertAction, LinkedList linkedList) {
    appendSql(SqlTokens.INSERT_INTO, linkedList);
    translateTable((Table) insertAction.getTableSource(), linkedList);
    Collection<Field> columns = getMoreBatis().getColumns(insertAction.getEntityClass());
    Iterator<Field> iterator = columns.iterator();
    appendSql(SqlTokens.BRACKET_START, linkedList);
    while (iterator.hasNext()) {
      final Field field = iterator.next();
      translateFieldSourceWithoutTable(field, linkedList);
      appendSql(SqlTokens.COMMA, linkedList);
    }
    if (linkedList.getLast() == SqlTokens.COMMA) {
      linkedList.removeLast();
    }
    appendSql(SqlTokens.BRACKET_END, linkedList);
    appendSql(SqlTokens.VALUES, linkedList);
    Iterator<Map<String, Object>> rowIterator = insertAction.getRows().iterator();
    while (rowIterator.hasNext()) {
      Map<String, Object> row = rowIterator.next();
      appendSql(SqlTokens.BRACKET_START, linkedList);
      iterator = columns.iterator();
      while (iterator.hasNext()) {
        final Field field = iterator.next();
        appendArgs(row.get(field.getAlias()), linkedList);
        appendSql(SqlTokens.COMMA, linkedList);
      }
      if (linkedList.getLast() == SqlTokens.COMMA) {
        linkedList.removeLast();
      }
      appendSql(SqlTokens.BRACKET_END, linkedList);
      appendSql(SqlTokens.COMMA, linkedList);
    }
    if (linkedList.getLast() == SqlTokens.COMMA) {
      linkedList.removeLast();
    }
    return linkedList;
  }

  private LinkedList translateOrder(Order order, LinkedList linkedList) {
    final Object source = order.getSource();
    if (source instanceof FieldSource) {
      translateFieldSource((FieldSource) source, linkedList);
    } else if (source instanceof String) {
      appendSql((SqlSegment) source, linkedList);
    }
    if (order.getOrder() == Order.DESC) {
      appendSql(SqlTokens.DESC, linkedList);
    } else {
      appendSql(SqlTokens.ASC, linkedList);
    }
    return linkedList;
  }

  //字段系列+++

  private LinkedList translateAliasedColumn(AliasedColumn aliasedColumn, LinkedList linkedList) {
    translateFieldSource(aliasedColumn, linkedList);
    final String alias = aliasedColumn.getAlias();
    if (alias != null) {
      appendSql(SqlTokens.AS, linkedList);
      appendSql(quoteStr(alias), linkedList);
    }
    return linkedList;
  }

  public LinkedList translateFieldSource(FieldSource fieldSource, LinkedList linkedList) {
    if (fieldSource instanceof Field) {
      translateField((Field) fieldSource, linkedList);
    } else if (fieldSource instanceof SubAttribute) {
      translateFieldSource(((SubAttribute) fieldSource).getField(), linkedList);
      appendSql(SqlTokens.JSON_ATTR, linkedList);
      appendArgs(((SubAttribute) fieldSource).getKey(), linkedList);
    } else if (fieldSource instanceof Aliased) {
      translateFieldSource(((Aliased) fieldSource).getFieldSource(), linkedList);
    } else if (fieldSource instanceof SqlFunction) {
      translateFunction((SqlFunction) fieldSource, linkedList);
    }
    return linkedList;
  }

  private LinkedList translateFunction(SqlFunction sqlFunction, LinkedList linkedList) {
    functionResolution.resolve(sqlFunction, linkedList);
    return linkedList;
  }


  private LinkedList translateFieldSourceWithoutTable(FieldSource field, LinkedList linkedList) {
    if (field instanceof Field) {
      appendSql(quoteStr(((Field) field).getFieldName()), linkedList);
    } else if (field instanceof SubAttribute) {
      translateFieldSourceWithoutTable(((SubAttribute) field).getField(), linkedList);
      appendSql(SqlTokens.JSON_ATTR, linkedList);
      appendSql(((SubAttribute) field).getKey(), linkedList);
    }
    return linkedList;
  }

  private LinkedList translateField(Field field, LinkedList linkedList) {
    final Table table = field.getTable();
    if (table != null) {
      linkedList.add(new SqlSegment(getTableStr(table) + "." + quoteStr(field.getFieldName())));
    } else {
      linkedList.add(new SqlSegment(quoteStr(field.getFieldName())));
    }
    return linkedList;
  }

  private LinkedList translateTableSource(TableSource tableSource, LinkedList linkedList) {
    if (tableSource instanceof Table) {
      return translateTable((Table) tableSource, linkedList);
    } else if (tableSource instanceof JoinTable) {
      translateJoinTable((JoinTable) tableSource, linkedList);
    }
    return linkedList;
  }

  private LinkedList translateTable(Table table, LinkedList linkedList) {
    linkedList.add(new SqlSegment(getTableStr(table)));
    return linkedList;
  }

  private LinkedList translateJoinTable(JoinTable joinTable, LinkedList linkedList) {
    translateTableSource(joinTable.getTableFirst(), linkedList);
    final JoinType joinType = joinTable.getJoinType();
    if (joinType == JoinType.INNER_JOIN) {
      appendSql(SqlTokens.INNER, linkedList);
    } else if (joinType == JoinType.LEFT_JOIN) {
      appendSql(SqlTokens.LEFT, linkedList);
    } else if (joinType == JoinType.RIGHT_JOIN) {
      appendSql(SqlTokens.RIGHT, linkedList);
    } else if (joinType == JoinType.FULL_JOIN) {
      appendSql(SqlTokens.FULL, linkedList);
    } else if (joinType == JoinType.NATURAL_JOIN) {
      appendSql(SqlTokens.NATURAL, linkedList);
    } else {
      throw new UnsupportedOperationException("没有这种操作");
    }
    appendSql(SqlTokens.JOIN, linkedList);
    translateTableSource(joinTable.getTableSecond(), linkedList);
    appendSql(SqlTokens.ON, linkedList);
    translateCondition(joinTable.getCondition(), linkedList);
    return linkedList;
  }

  //字段系列---

  //条件系列+++

  private LinkedList<Object> translateCondition(Condition condition, LinkedList<Object> list) {
    if (condition instanceof ChainCondition) {
      if (condition instanceof And) {
        translateAnd((And) condition, list);
      } else if (condition instanceof Or) {
        translateOr((Or) condition, list);
      }
    } else if (condition instanceof FieldCondition) {
      final Operator operator = ((FieldCondition) condition).getOperator();
      Object value = ((FieldCondition) condition).getValue();
      translateFieldSource(((FieldCondition) condition).getField(), list);
      switch (operator) {
        case IN:
          appendSql(condition.isNot() ? SqlTokens.NOT_IN : SqlTokens.IN, list);
          if (value instanceof Collection) {
            if (((Collection) value).size() == 0) {
              //空列表优化
              if (!condition.isNot()) {
                appendSql(SqlTokens.FALSE, list);
              }
            } else {
              appendArgs(value, list);
            }
          } else if (value instanceof QueryAction) {
            appendSql(SqlTokens.BRACKET_START, list);
            translateQueryAction((QueryAction) value, list);
            appendSql(SqlTokens.BRACKET_END, list);
          }
          break;
        case EQUAL:
          appendSql(condition.isNot() ? SqlTokens.NOT_EQUAL : SqlTokens.EQUAL, list);
          appendArgs(value, list);
          break;
        case LIKE_FULL:
          appendSql(condition.isNot() ? SqlTokens.NOT_LIKE : SqlTokens.LIKE, list);
          appendSql(SqlTokens.LIKE_LEFT, list);
          appendArgs(value, list);
          appendSql(SqlTokens.LIKE_RIGHT, list);
          break;
        case LIKE_LEFT:
          appendSql(condition.isNot() ? SqlTokens.NOT_LIKE : SqlTokens.LIKE, list);
          appendSql(SqlTokens.LIKE_LEFT, list);
          appendArgs(value, list);
          appendSql(SqlTokens.BRACKET_END, list);
          break;
        case LIKE_RIGHT:
          appendSql(condition.isNot() ? SqlTokens.NOT_LIKE : SqlTokens.LIKE, list);
          appendSql(SqlTokens.CONCAT, list);
          appendArgs(value, list);
          appendSql(SqlTokens.LIKE_RIGHT, list);
          break;
        case BETWEEN:
          appendSql(condition.isNot() ? SqlTokens.NOT_BETWEEN : SqlTokens.BETWEEN, list);
          final Object[] values = (Object[]) value;
          appendArgs(values[0], list);
          appendSql(SqlTokens.AND, list);
          appendArgs(values[1], list);
          break;
        case IS_NULL:
          appendSql(condition.isNot() ? SqlTokens.NOT_NULL : SqlTokens.IS_NULL, list);
          break;
      }
    }
    return list;
  }

  private LinkedList<Object> translateAnd(And andCondition, LinkedList<Object> list) {
    return translateChainCondition(andCondition, list, SqlTokens.AND);
  }
  /*条件系列*/

  /*Table系列*/

  private LinkedList<Object> translateOr(Or orCondition, LinkedList<Object> list) {
    return translateChainCondition(orCondition, list, SqlTokens.OR);
  }

  private LinkedList<Object> translateChainCondition(ChainCondition chainCondition,
      LinkedList<Object> list, SqlSegment seperator) {
    if (chainCondition.getConditions().size() == 0) {
      return list;
    }
    if (chainCondition.isNot()) {
      appendSql(SqlTokens.NOT, list);
    }
    appendSql(SqlTokens.BRACKET_START, list);
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
    appendSql(SqlTokens.BRACKET_END, list);
    return list;
  }

  //条件系列---

  /**
   * 工具方法
   */
  private String quoteStr(String str) {
    return '"' + str + '"';
  }

  private LinkedList mergeMap(FieldSource fieldSource, LinkedList linkedList) {
//    linkedList.add()
    LinkedList fieldSourceSegment = translateFieldSource(fieldSource, new LinkedList());
    linkedList.add(CAST_MAP_SEGMENT_1);
    linkedList.addAll(fieldSourceSegment);
    linkedList.add(CAST_MAP_SEGMENT_2);
    linkedList.addAll(fieldSourceSegment);
    linkedList.add(CAST_MAP_SEGMENT_3);
    return linkedList;
  }

  private String getTableStr(Table table) {
    String schema = table.getSchema();
    String strSource = schema == null || schema.isEmpty() ?
        quoteStr(table.getTableName())
        : quoteStr(table.getSchema()) + "." + quoteStr(table.getTableName());
    return strSource;
  }

  protected LinkedList appendSql(String sqlSegment, LinkedList list) {
    return appendSql(new SqlSegment(sqlSegment), list);
  }

  protected LinkedList appendSql(SqlSegment sqlSegment, LinkedList list) {
    list.add(sqlSegment);
    return list;
  }

  protected LinkedList appendArgs(Object args, LinkedList list) {
    if (args instanceof FieldSource) {
      translateFieldSource((FieldSource) args, list);
    } else if (args instanceof Map) {
      list.add(UtilsTool.objToJson(args));
      appendSql(SqlTokens.CAST_JSON, list);
    } else {
      list.add(args);
    }
    return list;
  }
  /**
   * 工具方法
   */
}
