package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * Create By HCL at 2017/10/11
 */
public class BaseOrg extends BaseEntity {

  private String orgPid;
  private String orgId;
  private String orgName;
  private int orgSort;
  private String orgLevel;
  private String fixedPhone;
  private String address;
  private String desp;

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

  public int getOrgSort() {
    return orgSort;
  }

  public void setOrgSort(int orgSort) {
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
