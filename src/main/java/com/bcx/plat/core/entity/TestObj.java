package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

public class TestObj extends BaseEntity<TestObj> {
  private String onlyFieldX;

  public String getOnlyFieldX() {
    return onlyFieldX;
  }

  public void setOnlyFieldX(String onlyFieldX) {
    this.onlyFieldX = onlyFieldX;
  }
}
