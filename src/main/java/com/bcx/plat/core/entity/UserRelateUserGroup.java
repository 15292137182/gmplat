package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

import java.sql.RowId;

/**
 * <p>Title: UserRelateUserGroup</p>
 * <p>Description: 用户关联用户组表</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/16  Wen TieHu Create </pre>
 */
public class UserRelateUserGroup extends BaseEntity<UserRelateUserGroup> {

  private String userRowId;//用户唯一标

  private String userGroupRowId;//用户组唯一标示

  public String getUserRowId() {
    return userRowId;
  }

  public void setUserRowId(String userRowId) {
    this.userRowId = userRowId;
  }

  public String getUserGroupRowId() {
    return userGroupRowId;
  }

  public void setUserGroupRowId(String userGroupRowId) {
    this.userGroupRowId = userGroupRowId;
  }
}
