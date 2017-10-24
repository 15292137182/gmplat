package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * Create By HCL at 2017/10/24
 */
public class MenuRelatePermission extends BaseEntity<MenuRelatePermission> {

  private String permissionRowId;
  private String menuRowId;

  public String getPermissionRowId() {
    return permissionRowId;
  }

  public void setPermissionRowId(String permissionRowId) {
    this.permissionRowId = permissionRowId;
  }

  public String getMenuRowId() {
    return menuRowId;
  }

  public void setMenuRowId(String menuRowId) {
    this.menuRowId = menuRowId;
  }
}