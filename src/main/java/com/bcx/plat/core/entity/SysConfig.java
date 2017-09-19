package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 系统资源配置实体类
 * Created by Wen Tiehu on 2017/8/7.
 */
public class SysConfig extends BaseEntity<SysConfig> {

  private String confKey;//键
  private String confValue;//值
  private String desp;//说明

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getConfKey() {
    return confKey;
  }

  public void setConfKey(String confKey) {
    this.confKey = confKey;
  }

  public String getConfValue() {
    return confValue;
  }

  public void setConfValue(String confValue) {
    this.confValue = confValue;
  }

  public String getDesp() {
    return desp;
  }

  public void setDesp(String desp) {
    this.desp = desp;
  }
}
