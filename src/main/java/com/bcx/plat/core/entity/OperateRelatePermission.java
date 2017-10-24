package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * Create By HCL at 2017/10/24
 */
public class OperateRelatePermission extends BaseEntity<OperateRelatePermission> {

  private String permissionRowId;
  private String operateRowId;

  public String getPermissionRowId() {
    return permissionRowId;
  }

  public void setPermissionRowId(String permissionRowId) {
    this.permissionRowId = permissionRowId;
  }

  public String getOperateRowId() {
    return operateRowId;
  }

  public void setOperateRowId(String operateRowId) {
    this.operateRowId = operateRowId;
  }
}