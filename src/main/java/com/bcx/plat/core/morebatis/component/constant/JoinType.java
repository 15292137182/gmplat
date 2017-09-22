package com.bcx.plat.core.morebatis.component.constant;

public enum JoinType {
  INNER_JOIN, LEFT_JOIN, RIGHT_JOIN, FULL_JOIN, NATURAL_JOIN;
  private String sql;

  JoinType() {
    this.sql = this.toString().toLowerCase().replace('_', ' ');
  }

  public String getSql() {
    return this.sql;
  }
}