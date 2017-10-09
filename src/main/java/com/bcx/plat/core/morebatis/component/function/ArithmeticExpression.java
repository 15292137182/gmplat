package com.bcx.plat.core.morebatis.component.function;

import java.util.List;

public class ArithmeticExpression extends SqlFunction<ArithmeticException> {

  private String expression;

  public ArithmeticExpression(String expression,Object ... objects){
    super(objects);
    this.expression=expression;
  }

  public ArithmeticExpression(String expression,List<Object> objects){
    super(objects);
    this.expression=expression;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }
}
