package com.bcx.plat.core.morebatis.translator;

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

public class Translator implements SqlComponentTranslator {

  private static final SqlSegment SELECT = new SqlSegment("SELECT");
  private static final SqlSegment DELETE = new SqlSegment("DELETE");
  private static final SqlSegment INSERT_INTO = new SqlSegment("INSERT INTO");
  private static final SqlSegment UPDATE = new SqlSegment("UPDATE");
  private static final SqlSegment SET = new SqlSegment("SET");
  private static final SqlSegment AS = new SqlSegment("AS");
  private static final SqlSegment FROM = new SqlSegment("FROM");
  private static final SqlSegment ON = new SqlSegment("ON");
  private static final SqlSegment JOIN = new SqlSegment("JOIN");
  private static final SqlSegment INNER = new SqlSegment("INNER");
  private static final SqlSegment LEFT = new SqlSegment("LEFT");
  private static final SqlSegment RIGHT = new SqlSegment("RIGHT");
  private static final SqlSegment NATURAL = new SqlSegment("NATURAL");
  private static final SqlSegment FULL = new SqlSegment("FULL");
  private static final SqlSegment WHERE = new SqlSegment("WHERE");
  private static final SqlSegment GROUP_BY = new SqlSegment("GROUP BY");
  private static final SqlSegment AND = new SqlSegment("AND");
  private static final SqlSegment OR = new SqlSegment("OR");
  private static final SqlSegment FALSE = new SqlSegment("FALSE");
  private static final SqlSegment TRUE = new SqlSegment("TRUE");
  private static final SqlSegment DOT = new SqlSegment(".");
  private static final SqlSegment COMMA = new SqlSegment(",");
  private static final SqlSegment VALUES = new SqlSegment("VALUES");
  private static final SqlSegment BRACKET_START = new SqlSegment("(");
  private static final SqlSegment BRACKET_END = new SqlSegment(")");
  private static final SqlSegment ASC = new SqlSegment("ASC");
  private static final SqlSegment DESC = new SqlSegment("DESC");
  private static final SqlSegment ORDER_BY = new SqlSegment("ORDER BY");
  private static final SqlSegment EQUAL = new SqlSegment("=");
  private static final SqlSegment NOT_EQUAL = new SqlSegment("!=");
  private static final SqlSegment IN = new SqlSegment("IN");
  private static final SqlSegment NOT_IN = new SqlSegment("NOT IN");
  private static final SqlSegment LIKE = new SqlSegment("LIKE");
  private static final SqlSegment NOT_LIKE = new SqlSegment("NOT LIKE");
  private static final SqlSegment BETWEEN = new SqlSegment("BETWEEN");
  private static final SqlSegment NOT_BETWEEN = new SqlSegment("NOT BETWEEN");
  private static final SqlSegment IS_NULL = new SqlSegment("IS NULL");
  private static final SqlSegment NOT_NULL = new SqlSegment("IS NOT NULL");
  private static final SqlSegment LIKE_RIGHT = new SqlSegment(",'%')");
  private static final SqlSegment LIKE_LEFT = new SqlSegment("concat('%',");
  private static final SqlSegment CONCAT = new SqlSegment("concat(");
  private static final SqlSegment NOT = new SqlSegment("NOT");
  private static final SqlSegment CAST_JSON = new SqlSegment("::jsonb");
  private static final SqlSegment JSON_ATTR = new SqlSegment("::jsonb->>");

  private MoreBatis moreBatis;

  private MoreBatis getMoreBatis() {
    if (moreBatis == null) {
      moreBatis = SpringContextHolder.getBean("moreBatis");
    }
    return moreBatis;
  }

  public LinkedList translateQueryAction(QueryAction queryAction, LinkedList linkedList) {
    appendSql(SELECT, linkedList);
    explainColumns(queryAction, linkedList);
    linkedList.add(FROM);
    translateTableSource(queryAction.getTableSource(), linkedList);
    Condition where = queryAction.getWhere();
    if (where != null) {
      if (!((where instanceof ChainCondition)
          && ((ChainCondition) where).getConditions().size() == 0)) {
        linkedList.add(WHERE);
      }
      translateCondition(where, linkedList);
    }

    List<FieldSource> group = queryAction.getGroup();
    if (group != null && group.size() > 0) {
      appendSql(GROUP_BY, linkedList);
      Iterator<FieldSource> groups = group.iterator();
      while (groups.hasNext()) {
        FieldSource fieldSource = groups.next();
        translateFieldSource(fieldSource, linkedList);
        appendSql(COMMA, linkedList);
      }
      if (linkedList.getLast() == COMMA) {
        linkedList.removeLast();
      }
    }

    final List<Order> orders = queryAction.getOrder();
    if (orders != null && !orders.isEmpty()) {
      linkedList.add(ORDER_BY);
      Iterator<Order> iterator = orders.iterator();
      while (iterator.hasNext()) {
        final Order order = iterator.next();
        translateOrder(order, linkedList);
        appendSql(COMMA, linkedList);
      }
      if (linkedList.getLast() == COMMA) {
        linkedList.removeLast();
      }
    }
    return linkedList;
  }

  private LinkedList explainColumns(QueryAction queryAction, LinkedList linkedList) {
    Iterator<AliasedColumn> iterator = queryAction.getAliasedColumns().iterator();
    while (iterator.hasNext()) {
      AliasedColumn aliasedColumn = iterator.next();
      translateAliasedColumn(aliasedColumn, linkedList);
      if (iterator.hasNext()) {
        linkedList.add(COMMA);
      }
    }
    return linkedList;
  }

  public LinkedList translateDeleteAction(DeleteAction deleteAction, LinkedList linkedList) {
    appendSql(DELETE, linkedList);
    appendSql(FROM, linkedList);
    translateTable((Table) deleteAction.getTableSource(), linkedList);
    appendSql(WHERE, linkedList);
    translateCondition(deleteAction.getWhere(), linkedList);
    return linkedList;
  }

  public LinkedList translateUpdateAction(UpdateAction updateAction, LinkedList linkedList) {
    appendSql(UPDATE, linkedList);
    translateTable((Table) updateAction.getTableSource(), linkedList);
    appendSql(SET, linkedList);
    Class<? extends BeanInterface> entityClass = updateAction.getEntityClass();
    Iterator<Map.Entry<String, Object>> entryIterator = updateAction.getValues().entrySet()
        .iterator();
    while (entryIterator.hasNext()) {
      final Map.Entry<String, Object> entry = entryIterator.next();
      Field field;
      try {
        field = getMoreBatis().getColumnByAlias(entityClass, entry.getKey());
      } catch (NullPointerException e) {
        continue;
      }
      translateFieldOnlyName(field, linkedList);
      appendSql(EQUAL, linkedList);
      if (entry.getValue() instanceof Map) {
        appendSql(mergeMap(field.getFieldSource()), linkedList);
      }
      appendArgs(entry.getValue(), linkedList);
      appendSql(COMMA, linkedList);
    }
    if (linkedList.getLast() == COMMA) {
      linkedList.removeLast();
    }
    appendSql(WHERE, linkedList);
    translateCondition(updateAction.getWhere(), linkedList);
    return linkedList;
  }

  public LinkedList translateTableSource(TableSource t) {
    return translateTableSource(t, new LinkedList());
  }

  public LinkedList translateInsertAction(InsertAction insertAction, LinkedList linkedList) {
    appendSql(INSERT_INTO, linkedList);
    translateTable((Table) insertAction.getTableSource(), linkedList);
    Collection<Field> columns = getMoreBatis().getColumns(insertAction.getEntityClass());
    Iterator<Field> iterator = columns.iterator();
    appendSql(BRACKET_START, linkedList);
    while (iterator.hasNext()) {
      final Field field = iterator.next();
      translateFieldOnlyName(field, linkedList);
      appendSql(COMMA, linkedList);
    }
    if (linkedList.getLast() == COMMA) {
      linkedList.removeLast();
    }
    appendSql(BRACKET_END, linkedList);
    appendSql(VALUES, linkedList);
    Iterator<Map<String, Object>> rowIterator = insertAction.getRows().iterator();
    while (rowIterator.hasNext()) {
      Map<String, Object> row = rowIterator.next();
      appendSql(BRACKET_START, linkedList);
      iterator = columns.iterator();
      while (iterator.hasNext()) {
        final Field field = iterator.next();
        appendArgs(row.get(field.getAlias()), linkedList);
        appendSql(COMMA, linkedList);
      }
      if (linkedList.getLast() == COMMA) {
        linkedList.removeLast();
      }
      appendSql(BRACKET_END, linkedList);
      appendSql(COMMA, linkedList);
    }
    if (linkedList.getLast() == COMMA) {
      linkedList.removeLast();
    }
    return linkedList;
  }

  public LinkedList translateOrder(Order order, LinkedList linkedList) {
    final Object source = order.getSource();
    if (source instanceof FieldSource) {
      translateFieldSource((FieldSource) source, linkedList);
    } else if (source instanceof String) {
      appendSql((SqlSegment) source, linkedList);
    }
    if (order.getOrder() == Order.DESC) {
      appendSql(DESC, linkedList);
    } else {
      appendSql(ASC, linkedList);
    }
    return linkedList;
  }

  //字段系列+++

  public LinkedList translateAliasedColumn(AliasedColumn aliasedColumn, LinkedList linkedList) {
    translateFieldSource(aliasedColumn, linkedList);
    final String alias = aliasedColumn.getAlias();
    if (alias != null) {
      appendSql(AS, linkedList);
      appendSql(quoteStr(alias), linkedList);
    }
    return linkedList;
  }

  public LinkedList translateFieldSource(FieldSource fieldSource, LinkedList linkedList) {
    if (fieldSource instanceof Field) {
      translateFieldSource((Field) fieldSource, linkedList);
    } else if (fieldSource instanceof SubAttribute) {
      translateFieldSource(((SubAttribute) fieldSource).getField(), linkedList);
      appendSql(JSON_ATTR, linkedList);
      appendArgs(((SubAttribute) fieldSource).getKey(), linkedList);
    }
    return linkedList;
  }

  public LinkedList translateFieldOnlyName(Field field, LinkedList linkedList) {
    linkedList.add(new SqlSegment(quoteStr(field.getFieldName())));
    return linkedList;
  }

  public LinkedList translateFieldSource(Field field, LinkedList linkedList) {
    final Table table = field.getTable();
    if (table != null) {
      linkedList.add(new SqlSegment(getTableStr(table) + "." + quoteStr(field.getFieldName())));
    } else {
      //TODO 所有对fieldCondition的访问移除以后 这个地方要删除
      linkedList.add(new SqlSegment(quoteStr(field.getFieldName())));
    }
    return linkedList;
  }

  public LinkedList translateTableSource(TableSource tableSource, LinkedList linkedList) {
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
      appendSql(INNER, linkedList);
    } else if (joinType == JoinType.LEFT_JOIN) {
      appendSql(LEFT, linkedList);
    } else if (joinType == JoinType.RIGHT_JOIN) {
      appendSql(RIGHT, linkedList);
    } else if (joinType == JoinType.FULL_JOIN) {
      appendSql(FULL, linkedList);
    } else if (joinType == JoinType.NATURAL_JOIN) {
      appendSql(NATURAL, linkedList);
    } else {
      throw new UnsupportedOperationException("没有这种操作");
    }
    appendSql(JOIN, linkedList);
    translateTableSource(joinTable.getTableSecond(), linkedList);
    appendSql(ON, linkedList);
    translateCondition(joinTable.getCondition(), linkedList);
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
      Object value = ((FieldCondition) condition).getValue();
      translateFieldSource(((FieldCondition) condition).getField(), list);
      switch (operator) {
        case IN:
          appendSql(condition.isNot() ? NOT_IN : IN, list);
          if (value instanceof Collection) {
            if (((Collection) value).size() == 0) {
              //空列表优化
              if (!condition.isNot()) {
                appendSql(FALSE, list);
              }
            } else {
              appendArgs(value, list);
            }
          } else if (value instanceof QueryAction) {
            appendSql(BRACKET_START, list);
            translateQueryAction((QueryAction) value, list);
            appendSql(BRACKET_END, list);
          }
          break;
        case EQUAL:
          appendSql(condition.isNot() ? NOT_EQUAL : EQUAL, list);
          appendArgs(value, list);
          break;
        case LIKE_FULL:
          appendSql(condition.isNot() ? NOT_LIKE : LIKE, list);
          appendSql(LIKE_LEFT, list);
          appendArgs(value, list);
          appendSql(LIKE_RIGHT, list);
          break;
        case LIKE_LEFT:
          appendSql(condition.isNot() ? NOT_LIKE : LIKE, list);
          appendSql(LIKE_LEFT, list);
          appendArgs(value, list);
          appendSql(BRACKET_END, list);
          break;
        case LIKE_RIGHT:
          appendSql(condition.isNot() ? NOT_LIKE : LIKE, list);
          appendSql(CONCAT, list);
          appendArgs(value, list);
          appendSql(LIKE_RIGHT, list);
          break;
        case BETWEEN:
          appendSql(condition.isNot() ? NOT_BETWEEN : BETWEEN, list);
          final Object[] values = (Object[]) value;
          appendArgs(values[0], list);
          appendSql(AND, list);
          appendArgs(values[1], list);
          break;
        case IS_NULL:
          appendSql(condition.isNot() ? NOT_NULL : IS_NULL, list);
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
      appendSql(NOT, list);
    }
    appendSql(BRACKET_START, list);
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
    appendSql(BRACKET_END, list);
    return list;
  }

  //条件系列---

  /**
   * 工具方法
   */
  private String quoteStr(String str) {
    return '"' + str + '"';
  }

  private SqlSegment mergeMap(String fieldName) {
    return new SqlSegment("(CASE when jsonb_typeof(" + fieldName + ")='object' THEN " + fieldName
        + " ELSE '{}'::jsonb END)||");
  }

  private String getTableStr(Table table) {
    String schema = table.getSchema();
    String strSource = schema == null || schema.isEmpty() ?
        quoteStr(table.getTableName())
        : quoteStr(table.getSchema()) + "." + quoteStr(table.getTableName());
    return strSource;
  }

  private LinkedList appendSql(String sqlSegment, LinkedList list) {
    return appendSql(new SqlSegment(sqlSegment), list);
  }

  private LinkedList appendSql(SqlSegment sqlSegment, LinkedList list) {
    list.add(sqlSegment);
    return list;
  }

  private LinkedList appendArgs(Object args, LinkedList list) {
    if (args instanceof FieldSource) {
      translateFieldSource((FieldSource) args, list);
    } else if (args instanceof Map) {
      list.add(UtilsTool.objToJson(args));
      appendSql(CAST_JSON, list);
    } else {
      list.add(args);
    }
    return list;
  }
  /**
   * 工具方法
   */
}
