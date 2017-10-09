package com.bcx.plat.core.morebatis.component.function;

import java.util.List;

public class Concat extends SqlFunction<Total> {

  public Concat(Object ... args) {
    super(args);
  }

  public Concat(List args){
    super(args);
  }
}
