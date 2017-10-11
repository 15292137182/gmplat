package com.bcx.plat.core.morebatis.component.function;

import com.bcx.plat.core.morebatis.phantom.FieldSource;
import java.util.Arrays;
import java.util.List;

public abstract class SqlFunction<T> implements FieldSource<T> {
  List<Object> args;

  public List<Object> getArgs() {
    return args;
  }

  public void setArgs(List<Object> args) {
    this.args = args;
  }

  protected SqlFunction(Object ... args){
    this.args= Arrays.asList(args);
  }

  protected SqlFunction(List args){
    this.args= args;
  }
}
