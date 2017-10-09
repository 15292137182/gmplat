package com.bcx.plat.core.morebatis.translator.postgre;

import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import com.bcx.plat.core.morebatis.component.function.ArithmeticExpression;
import com.bcx.plat.core.morebatis.component.function.*;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.FunctionResolution;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PostgreFunctionResolution implements FunctionResolution {
  protected static final SqlSegment SUM  = new SqlSegment("SUM(");
  protected static final SqlSegment COUNT  = new SqlSegment("COUNT(");
  protected static final SqlSegment FIRST = new SqlSegment("FIRST(");
  protected static final SqlSegment LAST = new SqlSegment("LAST(");
  protected static final SqlSegment MAX = new SqlSegment("MAX(");
  protected static final SqlSegment MIN = new SqlSegment("MIN(");
  protected static final SqlSegment TOTAL = new SqlSegment("TOTAL(");
  protected static final SqlSegment CONCAT = new SqlSegment("CONCAT(");

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
    } else if (function instanceof Concat){
      translator.appendSql(CONCAT, linkedList);
      for (Object args : function.getArgs()) {
        translator.appendArgs(args, linkedList);
        translator.appendSql(SqlTokens.COMMA, linkedList);
      }
      linkedList.removeLast();
      translator.appendSql(SqlTokens.BRACKET_END,linkedList );
    } else if (function instanceof ArithmeticExpression){
      String[] parts = ("("+((ArithmeticExpression) function).getExpression()+")").split("\\?");
      Iterator<String> partIterator = Arrays.asList(parts).iterator();
      Iterator argsIterator = function.getArgs().iterator();
      try {
        while (partIterator.hasNext()) {
          final String part = partIterator.next();
          translator.appendSql(part,linkedList);
          if (partIterator.hasNext()) {
            translator.appendArgs(argsIterator.next(), linkedList);
          }
        }
      } catch (NoSuchElementException e) {
        throw new NoSuchElementException("实际参数数量低于问号数量");
      }
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
