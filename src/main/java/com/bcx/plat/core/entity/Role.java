package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 权限实体类
 * <p>
 * Created By YoungerOu at 2017/10/12
 */
public class Role extends BaseEntity<Role> {
  private String roleId;//角色编号
  private String roleName;//角色名称
  private String roleType;//角色类型（S/U）
  private String desc;//描述
  private String remarks;//备注

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
