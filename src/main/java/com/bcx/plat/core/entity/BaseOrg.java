package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * Create By HCL at 2017/10/11
 */
public class BaseOrg extends BaseEntity<BaseOrg> {

  private String orgPid;  // 组织机构父编号
  private String orgId; // 组织机构编号
  private String orgName; // 组织机构名称
  private Integer orgSort;  // 组织机构序号
  private String orgLevel;  // 组织机构等级
  private String fixedPhone;  // 固定电话
  private String address; // 地址
  private String desp;  // 描述

  public String getOrgPid() {
    return orgPid;
  }

  public void setOrgPid(String orgPid) {
    this.orgPid = orgPid;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public Integer getOrgSort() {
    return orgSort;
  }

  public void setOrgSort(Integer orgSort) {
    this.orgSort = orgSort;
  }

  public String getOrgLevel() {
    return orgLevel;
  }

  public void setOrgLevel(String orgLevel) {
    this.orgLevel = orgLevel;
  }

  public String getFixedPhone() {
    return fixedPhone;
  }

  public void setFixedPhone(String fixedPhone) {
    this.fixedPhone = fixedPhone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDesp() {
    return desp;
  }

  public void setDesp(String desp) {
    this.desp = desp;
  }
}
