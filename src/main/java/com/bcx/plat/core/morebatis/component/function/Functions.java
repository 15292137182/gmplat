package com.bcx.plat.core.morebatis.component.function;

import java.util.List;

public class Functions {

  public static class Concat extends SqlFunction<Total> {

    public Concat(Object... args) {
      super(args);
    }

    public Concat(List args) {
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

  public static class Sum extends SqlFunction<Sum> {

    public Sum(Object args) {
      super(args);
    }
  }

  public static class Total extends SqlFunction<Total> {

    public Total(Object args) {
      super(args);
    }
  }
}
