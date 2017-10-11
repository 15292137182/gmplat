package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 系统设置 class
 * Create By HCL at 2017/10/11
 */
public class SystemSetting extends BaseEntity<SystemSetting> {

  private String key;
  private String name;
  private String value;
  private String remark;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
