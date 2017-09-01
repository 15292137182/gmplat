package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;

/**
 * 用户
 *
 * Create By HCL at 2017/8/17
 */
@Table(TableInfo.T_SYS_USER)
public class User extends BaseEntity<User> {

  @TablePK
  private String rowId;

  private String id;
  private String name;
  private String portraitPath;
  private String password;
  private String sex;
  private String deptno;
  private String mail;
  private String phone;
  private String type;
  private String limit;
  private String lastLoginDate;
  private String lastModifyPassword;
  private String disabled;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPortraitPath() {
    return portraitPath;
  }

  public void setPortraitPath(String portraitPath) {
    this.portraitPath = portraitPath;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getDeptno() {
    return deptno;
  }

  public void setDeptno(String deptno) {
    this.deptno = deptno;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }

  public String getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(String lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  public String getLastModifyPassword() {
    return lastModifyPassword;
  }

  public void setLastModifyPassword(String lastModifyPassword) {
    this.lastModifyPassword = lastModifyPassword;
  }

  public String getDisabled() {
    return disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }
}
