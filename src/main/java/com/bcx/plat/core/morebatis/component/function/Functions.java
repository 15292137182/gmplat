package com.bcx.plat.core.morebatis.component.function;

import com.bcx.plat.core.morebatis.phantom.FieldSource;

public class Functions {

  private static abstract class SingleArgsFunction<T> implements FieldSource<T>{
    Object args;
    public Object getArgs() {
      return args;
    }
    public void setArgs(Object args) {
      this.args = args;
    }
    public SingleArgsFunction(Object args){
      this.args=args;
    }
  }

  public static class Sum extends SingleArgsFunction<Sum>{

    public Sum(Object args) {
      super(args);
    }
  }

  public static class Count extends SingleArgsFunction<Count>{

    public Count(Object args) {
      super(args);
    }
  }

  public static class First extends SingleArgsFunction<First>{

    public First(Object args) {
      super(args);
    }
  }

  public static class Last extends SingleArgsFunction<Last>{
    public Last(Object args) {
      super(args);
    }
  }

  public static class Max extends SingleArgsFunction<Max>{
    public Max(Object args) {
      super(args);
    }
  }

  public static class Min extends SingleArgsFunction<Min>{
    public Min(Object args) {
      super(args);
    }
  }

  public static class Total extends SingleArgsFunction<Total>{
    public Total(Object args) {
      super(args);
    }
  }
}
