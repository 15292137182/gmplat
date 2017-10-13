package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 人员信息与角色关联实体类
 * <p>
 * Created by YoungerOu on 2017/10/13.
 * <p>
 */
public class UserRelateRole extends BaseEntity<UserRelateRole> {
  private String userRowId;//关联用户标识
  private String roleRowId;//关联角色标识

  public String getUserRowId() {
    return userRowId;
  }

  public void setUserRowId(String userRowId) {
    this.userRowId = userRowId;
  }

  public String getRoleRowId() {
    return roleRowId;
  }

  public void setRoleRowId(String roleRowId) {
    this.roleRowId = roleRowId;
  }
}
