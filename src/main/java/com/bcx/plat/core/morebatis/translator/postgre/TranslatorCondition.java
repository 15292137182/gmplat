package com.bcx.plat.core.morebatis.translator.postgre;

import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Or;
import java.util.List;

public class TranslatorCondition {
  public List<Object> translate(Condition condition,List<Object> list){
    if (condition instanceof And) {

    }else if (condition instanceof Or){

    }else if (condition instanceof FieldCondition){

    }
    return list;
  }

  public List<Object> translate(And andCondition,List<Object> list){

    list.add("(");
    for (Condition condition : andCondition.getConditions()) {

    }
    list.add(")");
    return list;
  }


}
