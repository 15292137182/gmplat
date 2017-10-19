package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/17  Wen TieHu Create </pre>
 */
public class UserGroupRelateRole extends BaseEntity<UserGroupRelateRole>{

  private String userGroupRowId;//用户组唯一标识

  private String roleRowId;//角色唯一标识


  public String getUserGroupRowId() {
    return userGroupRowId;
  }

  public void setUserGroupRowId(String userGroupRowId) {
    this.userGroupRowId = userGroupRowId;
  }

  public String getRoleRowId() {
    return roleRowId;
  }

  public void setRoleRowId(String roleRowId) {
    this.roleRowId = roleRowId;
  }
}
