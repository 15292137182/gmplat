package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * Create By HCL at 2017/10/24
 */
public class InterfaceRelatePermission extends BaseEntity<InterfaceRelatePermission> {

  private String permissionRowId;
  private String interfaceRowId;

  public String getPermissionRowId() {
    return permissionRowId;
  }

  public void setPermissionRowId(String permissionRowId) {
    this.permissionRowId = permissionRowId;
  }

  public String getInterfaceRowId() {
    return interfaceRowId;
  }

  public void setInterfaceRowId(String interfaceRowId) {
    this.interfaceRowId = interfaceRowId;
  }
}