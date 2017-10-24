package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * Create By HCL at 2017/10/24
 */
public class PageRelatePermission extends BaseEntity<PageRelatePermission> {

  private String permissionRowId;
  private String pageRowId;

  public String getPermissionRowId() {
    return permissionRowId;
  }

  public void setPermissionRowId(String permissionRowId) {
    this.permissionRowId = permissionRowId;
  }

  public String getPageRowId() {
    return pageRowId;
  }

  public void setPageRowId(String pageRowId) {
    this.pageRowId = pageRowId;
  }
}