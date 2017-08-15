package com.bcx.plat.core.morebatis.translator.postgre;

import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.phantom.FieldInTable;
import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.util.MoreBatisUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PostgreSqlTranslator implements SqlComponentTranslator {

  public static void main(String[] args) {
    PostgreSqlTranslator postgreSqlTranslator = new PostgreSqlTranslator();
    And and = new And(
        new FieldCondition("satrurn", Operator.EQUAL, 10086).not()
        , new FieldCondition("jupiter", Operator.IN, Arrays.asList(1, 2, 3, 4, 5)).not()
        , new Or(new FieldCondition("earth", Operator.BETWEEN, new Object[]{"venus", "mars"})
        , new FieldCondition("sun", Operator.EQUAL, "star")
        , new And(new FieldCondition("a", Operator.EQUAL, "b").not(),
        new FieldCondition("b", Operator.EQUAL, "a").not())
    )
    );

    LinkedList<Object> result = postgreSqlTranslator.translate(and);
    result.size();
  }

  /*条件系列*/
  public LinkedList<Object> translate(Condition condition) {
    return translate(condition, new LinkedList<>());
  }

  /**
   *
   * @param condition
   * @param list
   * @return
   */
  public LinkedList<Object> translate(Condition condition, LinkedList<Object> list) {
    if (condition instanceof ChainCondition) {
      if (condition instanceof And) {
        translate((And) condition, list);
      } else if (condition instanceof Or) {
        translate((Or) condition, list);
      }
    } else if (condition instanceof FieldCondition) {
      final Operator operator = ((FieldCondition) condition).getOperator();
      final Object value = ((FieldCondition) condition).getValue();
      translate((FieldSource) ((FieldCondition) condition).getField(), list);
      switch (operator) {
        case IN:
          if (value instanceof Collection) {
            if (((Collection) value).size() == 0) {
              //空列表优化
              if (!condition.isNot()) {
                MoreBatisUtil.addSqlSegmentToList("false", list);
              }
            } else {
              MoreBatisUtil.addSqlSegmentToList(condition.isNot() ? "not in" : "in", list);
              MoreBatisUtil.addSqlParameterToList(value, list);
            }
          } else {
            //预留给子表查询
          }
          break;
        case EQUAL:
          MoreBatisUtil.addSqlSegmentToList(condition.isNot() ? "!=" : "=", list);
          MoreBatisUtil.addSqlParameterToList(value, list);
          break;
        case LIKE_FULL:
          MoreBatisUtil.addSqlSegmentToList(condition.isNot() ? "not like" : "like", list);
          MoreBatisUtil.addSqlSegmentToList("concat('%',", list);
          MoreBatisUtil.addSqlParameterToList(value, list);
          MoreBatisUtil.addSqlSegmentToList(",'%')", list);
          break;
        case LIKE_LEFT:
          MoreBatisUtil.addSqlSegmentToList(condition.isNot() ? "not like" : "like", list);
          MoreBatisUtil.addSqlSegmentToList("concat('%',", list);
          MoreBatisUtil.addSqlParameterToList(value, list);
          MoreBatisUtil.addSqlSegmentToList(")", list);
          break;
        case LIKE_RIGHT:
          MoreBatisUtil.addSqlSegmentToList(condition.isNot() ? "not like" : "like", list);
          MoreBatisUtil.addSqlSegmentToList("concat(", list);
          MoreBatisUtil.addSqlParameterToList(value, list);
          MoreBatisUtil.addSqlSegmentToList(",'%')", list);
          break;
        case BETWEEN:
          MoreBatisUtil.addSqlSegmentToList(condition.isNot() ? "not between" : "between", list);
          final Object[] values = (Object[]) value;
          MoreBatisUtil.addSqlParameterToList(values[0], list);
          MoreBatisUtil.addSqlSegmentToList("and", list);
          MoreBatisUtil.addSqlParameterToList(values[1], list);
          break;
        case IS_NULL:
          MoreBatisUtil.addSqlSegmentToList(condition.isNot() ? "is not null" : "is null", list);
          break;
      }
    }
    return list;
  }

  public LinkedList<Object> translate(And andCondition, LinkedList<Object> list) {
    return translateChainCondition(andCondition, list, "and");
  }
  /*条件系列*/

  /*Table系列*/

  public LinkedList<Object> translate(Or andCondition, LinkedList<Object> list) {
    return translateChainCondition(andCondition, list, "or");
  }

  /**
   * 这是真正的入口
   */
  @Override
  public LinkedList<Object> translate(TableSource tableSource, LinkedList<Object> list) {
    if (tableSource instanceof JoinTable) {
      return translate((JoinTable) tableSource, list);
    } else if (tableSource instanceof Table) {
      return translate((Table) tableSource, list);
    } else {
      list.addAll(tableSource.getTableSource(this));
      return list;
    }
  }

  @Override
  public LinkedList<Object> translate(TableSource tableSource) {
    return translate(tableSource, new LinkedList<>());
  }

  public LinkedList<Object> translate(JoinTable joinTable, LinkedList<Object> list) {
    translate(joinTable.getTableFirst(), list);
    MoreBatisUtil.addSqlSegmentToList(joinTable.getJoinType().getSql(), list);
    translate(joinTable.getTableSecond(), list);
    MoreBatisUtil.addSqlSegmentToList("on", list);
    translate(joinTable.getCondition(), list);
    return list;
  }

  /*Table系列*/

  public LinkedList<Object> translate(Table table, LinkedList<Object> list) {
    list.add(table.getSqlSegment());
    return list;
  }

  /*字段系列*/
  public LinkedList<Object> translate(FieldSource fieldSource, LinkedList<Object> list) {
    if (fieldSource instanceof FieldInTable) {
      return translate((FieldInTable) fieldSource,list);
    }else{
      list.add(new SqlSegment(fieldSource.getFieldSource()));
    }
    return list;
  }

  public LinkedList<Object> translate(FieldInTable fieldSource, LinkedList<Object> list) {
    list.add(new SqlSegment(fieldSource.getColumnSqlFragment(this)));
    return list;
  }
  /*字段系列*/

  private LinkedList<Object> translateChainCondition(ChainCondition chainCondition,
      LinkedList<Object> list, String seperator) {
    if (chainCondition.getConditions().size() == 0) {
      return list;
    }
    if (chainCondition.isNot()) {
      MoreBatisUtil.addSqlSegmentToList("not", list);
    }
    MoreBatisUtil.addSqlSegmentToList("(", list);
    final List<Condition> conditions = chainCondition.getConditions();
    Iterator<Condition> conditionIterator = conditions.iterator();
    boolean notFirst = false;
    // TODO 实现为主 混乱将就
    for (Condition condition : conditions) {
      if (notFirst) {
        if (condition instanceof ChainCondition) {
          if (!((ChainCondition) condition).getConditions().isEmpty()) {
            MoreBatisUtil.addSqlSegmentToList(seperator, list);
          }
        } else {
          MoreBatisUtil.addSqlSegmentToList(seperator, list);
        }
      } else {
        notFirst = true;
      }
      translate(condition, list);
    }
    MoreBatisUtil.addSqlSegmentToList(")", list);
    return list;
  }
}