package com.bcx.plat.core.base.beans;

import static com.bcx.plat.core.utils.UtilsTool.getDateTimeNow;

/**
 * Create By HCL at 2017/8/29
 */
public class WorkFlowBean<T extends WorkFlowBean> extends SimpleBeanUtils<T> {

  private String approveStatus;
  private String approveUser;
  private String approveUserName;
  private String approveTime;

  /**
   * 构建审批信息
   *
   * @return 返回
   */
  @SuppressWarnings("unchecked")
  public T buildApproveInfo(String approveStatus) {
    this.approveStatus = approveStatus;
    this.approveUser = "admin";
    this.approveUserName = "系统管理员";
    this.approveTime = getDateTimeNow();
    return (T) this;
  }

  public String getApproveStatus() {
    return approveStatus;
  }

  public void setApproveStatus(String approveStatus) {
    this.approveStatus = approveStatus;
  }

  public String getApproveUser() {
    return approveUser;
  }

  public void setApproveUser(String approveUser) {
    this.approveUser = approveUser;
  }

  public String getApproveUserName() {
    return approveUserName;
  }

  public void setApproveUserName(String approveUserName) {
    this.approveUserName = approveUserName;
  }

  public String getApproveTime() {
    return approveTime;
  }

  public void setApproveTime(String approveTime) {
    this.approveTime = approveTime;
  }
}
