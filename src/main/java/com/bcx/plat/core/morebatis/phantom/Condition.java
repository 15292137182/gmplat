package com.bcx.plat.core.morebatis.phantom;

public interface Condition<T extends Condition<T>> {

  boolean isNot();

  void setNot(boolean not);

  default T not() {
    setNot(!isNot());
    return (T) this;
  }

  String getConditionSqlFragment();
}
