package com.bcx.plat.core.morebatis.translator.postgre;

import com.bcx.plat.core.morebatis.cctv1.Argument;
import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.ConditionTranslator;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.morebatis.substance.condition.Or;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PostgreSqlTranslator implements ConditionTranslator {

  public LinkedList<Object> translate(Condition condition){
    return translate(condition,new LinkedList<>());
  }

  public LinkedList<Object> translate(Condition condition,LinkedList<Object> list){
    if (condition instanceof ChainCondition) {
      if (condition instanceof And){
        translate((And)condition,list);
      }else if (condition instanceof Or){
        translate((Or)condition,list);
      }
    }else if (condition instanceof FieldCondition){
      final String fieldSource = ((FieldCondition) condition).getField().getFieldSource();
      final Operator operator = ((FieldCondition) condition).getOperator();
      final Object value=((FieldCondition)condition).getValue();
      list.add(fieldSource);
      switch (operator) {
        case IN:
          list.add(condition.isNot()?"NOT IN":"IN");
          list.add(new Argument(value));
          break;
        case EQUAL:
          list.add(condition.isNot()?"!=":"=");
          list.add(new Argument(value));
          break;
        case LIKE_FULL:
          list.add(condition.isNot()?"NOT LIKE":"LIKE");
          list.add("concat('%',");
          list.add(value);
          list.add(",'%')");
          break;
        case LIKE_LEFT:
          list.add(condition.isNot()?"NOT LIKE":"LIKE");
          list.add("concat('%',");
          list.add(value);
          list.add(")");
          break;
        case LIKE_RIGHT:
          list.add(condition.isNot()?"NOT LIKE":"LIKE");
          list.add("concat(");
          list.add(value);
          list.add(",'%')");
          break;
        case BETWEEN:
          list.add(condition.isNot()?"NOT BETWEEN":"BETWEEN");
          final Object[] values = (Object[]) value;
          list.add(new Argument(values[0]));
          list.add("AND");
          list.add(new Argument(values[1]));
          break;
      }
    }
    return list;
  }

  public LinkedList<Object> translate(And andCondition,LinkedList<Object> list){
    return translateChainCondition(andCondition, list,"AND");
  }

  public LinkedList<Object> translate(Or andCondition,LinkedList<Object> list){
    return translateChainCondition(andCondition, list,"OR");
  }

  private LinkedList<Object> translateChainCondition(ChainCondition chainCondition, LinkedList<Object> list,String seperator){
    if (chainCondition.isNot()) list.add("NOT");
    list.add("(");
    final List<Condition> conditions = chainCondition.getConditions();
    for (Condition condition : conditions) {
      translate(condition,list);
      list.add(seperator);
    }
    if (conditions.size()>0) list.removeLast();
    list.add(")");
    return list;
  }

  public static void main(String[] args) {
    PostgreSqlTranslator postgreSqlTranslator=new PostgreSqlTranslator();
    And and=new And(
        new FieldCondition("satrurn",Operator.EQUAL,10086).not()
        ,new FieldCondition("jupiter",Operator.IN,Arrays.asList(1,2,3,4,5)).not()
        ,new Or(   new FieldCondition("earth",Operator.BETWEEN,new Object[]{"venus","mars"})
                  ,new FieldCondition("sun",Operator.EQUAL,"star")
                  ,new And(new FieldCondition("a",Operator.EQUAL,"b").not(),
                            new FieldCondition("b",Operator.EQUAL,"a").not())
        )
    );
    LinkedList<Object> result = postgreSqlTranslator.translate(and);
    result.size();
  }
}
