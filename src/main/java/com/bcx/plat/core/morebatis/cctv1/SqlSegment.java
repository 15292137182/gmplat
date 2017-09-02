package com.bcx.plat.core.morebatis.cctv1;

public class SqlSegment {
  private Object argument;

  public Object getSegment() {
    return argument;
  }

//  public void setSegment(Object argument) {
//    this.argument = argument;
//  }

  public SqlSegment(Object argument) {
    this.argument = argument;
  }
}
