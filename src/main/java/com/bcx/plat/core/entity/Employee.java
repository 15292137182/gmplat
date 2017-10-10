package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

public class Employee extends BaseEntity<Employee> {
  private String employeeNo;//用户工号
  private String employeeName;//用户姓名
  private String employeeNickName;//用户昵称
  private String password;//密码
  private String status;//状态
  private String belongOrganization;//所属部门
  private String idCard;//身份证
  private String mobilePhone;//移动电话
  private String officePhone;//办公电话
  private String email;//邮箱
  private String gender;//性别
  private String job;//职务
  private String hiredate;//入职日期
  private String passwordUpdateTime;//密码更新日期
  private String lastLoginTime;//上次登录时间
  private String accountLockedTime;//账号锁定日期
  private String description;//说明
  private String remarks;//备注

  @Override
  public Employee buildCreateInfo() {
    return super.buildCreateInfo();
  }

  public String getEmployeeNo() {
    return employeeNo;
  }

  public void setEmployeeNo(String employeeNo) {
    this.employeeNo = employeeNo;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getEmployeeNickName() {
    return employeeNickName;
  }

  public void setEmployeeNickName(String employeeNickName) {
    this.employeeNickName = employeeNickName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBelongOrganization() {
    return belongOrganization;
  }

  public void setBelongOrganization(String belongOrganization) {
    this.belongOrganization = belongOrganization;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getOfficePhone() {
    return officePhone;
  }

  public void setOfficePhone(String officePhone) {
    this.officePhone = officePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public String getHiredate() {
    return hiredate;
  }

  public void setHiredate(String hiredate) {
    this.hiredate = hiredate;
  }

  public String getPasswordUpdateTime() {
    return passwordUpdateTime;
  }

  public void setPasswordUpdateTime(String passwordUpdateTime) {
    this.passwordUpdateTime = passwordUpdateTime;
  }

  public String getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(String lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getAccountLockedTime() {
    return accountLockedTime;
  }

  public void setAccountLockedTime(String accountLockedTime) {
    this.accountLockedTime = accountLockedTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
