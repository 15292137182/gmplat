package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * Create By HCL at 2017/10/24
 */
public class ButtonRelatePermission extends BaseEntity<ButtonRelatePermission> {

  private String permissionRowId;
  private String buttonRowId;

  public String getPermissionRowId() {
    return permissionRowId;
  }

  public void setPermissionRowId(String permissionRowId) {
    this.permissionRowId = permissionRowId;
  }

  public String getButtonRowId() {
    return buttonRowId;
  }

  public void setButtonRowId(String buttonRowId) {
    this.buttonRowId = buttonRowId;
  }
}