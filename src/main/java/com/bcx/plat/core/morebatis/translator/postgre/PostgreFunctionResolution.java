package com.bcx.plat.core.morebatis.translator.postgre;

import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.component.function.Functions.*;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.FunctionResolution;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import java.util.LinkedList;

public class PostgreFunctionResolution implements FunctionResolution {
  protected static final SqlSegment SUM  = new SqlSegment("SUM(");
  protected static final SqlSegment COUNT  = new SqlSegment("COUNT(");
  protected static final SqlSegment FIRST = new SqlSegment("FIRST(");
  protected static final SqlSegment LAST = new SqlSegment("LAST(");
  protected static final SqlSegment MAX = new SqlSegment("MAX(");
  protected static final SqlSegment MIN = new SqlSegment("MIN(");
  protected static final SqlSegment TOTAL = new SqlSegment("TOTAL(");

  private Translator translator;

  public PostgreFunctionResolution(Translator translator) {
    this.translator = translator;
  }

  @Override
  public LinkedList resolve(SqlFunction function, LinkedList linkedList) {
    if (function instanceof Sum) {
      singleArgsFunction(function,SUM,linkedList);
    } else if (function instanceof Count) {
      singleArgsFunction(function,COUNT,linkedList);
    } else if (function instanceof First) {
      singleArgsFunction(function,FIRST,linkedList);
    } else if (function instanceof Last) {
      singleArgsFunction(function,LAST,linkedList);
    } else if (function instanceof Max) {
      singleArgsFunction(function,MAX,linkedList);
    } else if (function instanceof Min) {
      singleArgsFunction(function,MIN,linkedList);
    } else if (function instanceof Total) {
      singleArgsFunction(function,TOTAL,linkedList);
    }
    return linkedList;
  }

  private LinkedList singleArgsFunction(SqlFunction function,SqlSegment functionName,LinkedList linkedList){
    translator.appendArgs(functionName, linkedList);
    Object args = function.getArgs().get(0);
    if (args instanceof FieldSource) {
      translator.translateFieldSource((FieldSource) args, linkedList);
    }else{
      translator.appendArgs(args, linkedList);
    }
    translator.appendArgs(SqlTokens.BRACKET_END, linkedList);
    return linkedList;
  }
}
