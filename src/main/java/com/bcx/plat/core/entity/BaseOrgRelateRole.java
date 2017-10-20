package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 组织机构与角色关联实体类
 * <p>
 * Created by YoungerOu on 2017/10/20.
 * <p>
 */
public class BaseOrgRelateRole extends BaseEntity<BaseOrgRelateRole> {
  private String baseOrgRowId;
  private String roleRowId;

  public String getBaseOrgRowId() {
    return baseOrgRowId;
  }

  public void setBaseOrgRowId(String baseOrgRowId) {
    this.baseOrgRowId = baseOrgRowId;
  }

  public String getRoleRowId() {
    return roleRowId;
  }

  public void setRoleRowId(String roleRowId) {
    this.roleRowId = roleRowId;
  }
}
