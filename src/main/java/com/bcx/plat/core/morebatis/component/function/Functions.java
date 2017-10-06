package com.bcx.plat.core.morebatis.component.function;

import com.bcx.plat.core.morebatis.phantom.FieldSource;
import java.util.Arrays;
import java.util.List;

public class Functions {

  public static abstract class SqlFunction<T> implements FieldSource<T>{
    List<Object> args;

    public List<Object> getArgs() {
      return args;
    }

    public void setArgs(List<Object> args) {
      this.args = args;
    }

    public SqlFunction(Object ... args){
      this.args= Arrays.asList(args);
    }
  }

  public static class Sum extends SqlFunction<Sum> {

    public Sum(Object args) {
      super(args);
    }
  }

  public static class Count extends SqlFunction<Count> {

    public Count(Object args) {
      super(args);
    }
  }

  public static class First extends SqlFunction<First> {

    public First(Object args) {
      super(args);
    }
  }

  public static class Last extends SqlFunction<Last> {
    public Last(Object args) {
      super(args);
    }
  }

  public static class Max extends SqlFunction<Max> {
    public Max(Object args) {
      super(args);
    }
  }

  public static class Min extends SqlFunction<Min> {
    public Min(Object args) {
      super(args);
    }
  }

  public static class Total extends SqlFunction<Total> {
    public Total(Object args) {
      super(args);
    }
  }
}
