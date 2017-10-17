package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 角色权限关联表
 * <p>
 * Create By HCL at 2017/10/17
 */
public class RoleRelatePermission extends BaseEntity<RoleRelatePermission> {

  private String permissionRowId;
  private String roleRowId;

  public String getPermissionRowId() {
    return permissionRowId;
  }

  public void setPermissionRowId(String permissionRowId) {
    this.permissionRowId = permissionRowId;
  }

  public String getRoleRowId() {
    return roleRowId;
  }

  public void setRoleRowId(String roleRowId) {
    this.roleRowId = roleRowId;
  }
}
