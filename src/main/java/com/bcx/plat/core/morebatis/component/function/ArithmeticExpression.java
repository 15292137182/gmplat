package com.bcx.plat.core.morebatis.component.function;

import com.bcx.plat.core.morebatis.phantom.FieldSource;
import java.util.Arrays;
import java.util.List;

public class ArithmeticExpression implements FieldSource<ArithmeticException> {

  private String expression;

  private List<Object> objects;

  ArithmeticExpression(String expression,Object ... objects){
    this.expression=expression;
    this.objects= Arrays.asList(objects);
  }

  ArithmeticExpression(String expression,List<Object> objects){
    this.expression=expression;
    this.objects= objects;
  }
}
