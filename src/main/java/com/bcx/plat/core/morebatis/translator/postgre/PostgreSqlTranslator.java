package com.bcx.plat.core.morebatis.translator.postgre;

import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.phantom.ChainCondition;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.ConditionTranslator;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.component.condition.Or;

import java.util.*;

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
      addSqlSegment(fieldSource,list);
      switch (operator) {
        case IN:
          if (value instanceof Collection) {
            if (((Collection) value).size()==0) {
              if (!condition.isNot()) addSqlSegment("false",list);
            }else {
              addSqlSegment(condition.isNot()?"not in":"in",list);
              addSqlParameter(value,list);
            }
          }else{
            //预留给子表查询
          }
          break;
        case EQUAL:
          addSqlSegment(condition.isNot()?"!=":"=",list);
          addSqlParameter(value,list);
          break;
        case LIKE_FULL:
          addSqlSegment(condition.isNot()?"not like":"like",list);
          addSqlSegment("concat('%',",list);
          addSqlParameter(value,list);
          addSqlSegment(",'%')",list);
          break;
        case LIKE_LEFT:
          addSqlSegment(condition.isNot()?"not like":"like",list);
          addSqlSegment("concat('%',",list);
          addSqlParameter(value,list);
          addSqlSegment(")",list);
          break;
        case LIKE_RIGHT:
          addSqlSegment(condition.isNot()?"not like":"like",list);
          addSqlSegment("concat(",list);
          addSqlParameter(value,list);
          addSqlSegment(",'%')",list);
          break;
        case BETWEEN:
          addSqlSegment(condition.isNot()?"not between":"between",list);
          final Object[] values = (Object[]) value;
          addSqlParameter(values[0],list);
          addSqlSegment("and",list);
          addSqlParameter(values[1],list);
          break;
      }
    }
    return list;
  }

  public LinkedList<Object> translate(And andCondition,LinkedList<Object> list){
    return translateChainCondition(andCondition, list,"and");
  }

  public LinkedList<Object> translate(Or andCondition,LinkedList<Object> list){
    return translateChainCondition(andCondition, list,"or");
  }


  private LinkedList<Object> translateChainCondition(ChainCondition chainCondition, LinkedList<Object> list,String seperator){
    if (chainCondition.getConditions().size()==0) return list;
    if (chainCondition.isNot()) addSqlSegment("NOT",list);
    addSqlSegment("(",list);
    final List<Condition> conditions = chainCondition.getConditions();
    Iterator<Condition> conditionIterator = conditions.iterator();
    boolean notFirst=false;
    // TODO 实现为主 混乱将就
    for (Condition condition : conditions) {
        if (notFirst) {
            if (condition instanceof ChainCondition) {
                if (!((ChainCondition) condition).getConditions().isEmpty()) {
                  addSqlSegment(seperator,list);
                }
            }else{
                addSqlSegment(seperator,list);
            }
        }else{
            notFirst=true;
        }
      translate(condition,list);
    }
    addSqlSegment(")",list);
    return list;
  }

  private LinkedList<Object> addSqlSegment(String sql,LinkedList<Object> list){
    list.add(new SqlSegment(sql));
    return list;
  }

  private LinkedList<Object> addSqlParameter(Object parameter,LinkedList<Object> list){
    list.add(parameter);
    return list;
  }

  public static void main(String[] args) {
    PostgreSqlTranslator postgreSqlTranslator=new PostgreSqlTranslator();
    And and=new And(
        new FieldCondition("satrurn",Operator.EQUAL,10086).not()
        ,new FieldCondition("jupiter",Operator.IN, Arrays.asList(1,2,3,4,5)).not()
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
