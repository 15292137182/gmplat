package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/12  Wen TieHu Create </pre>
 */
public class Button extends BaseEntity<Button> {

  private String pageIdent;//页面标识

  private String buttonNumber;//按钮编号

  private String buttonName;//按钮名称

  private String avail;//是否可用

  private String grantAuth;//是否授权

  private String sort;//排序

  private String customStyle;//自定义样式

  private String remarks;//备注




  public String getPageIdent() {
    return pageIdent;
  }

  public void setPageIdent(String pageIdent) {
    this.pageIdent = pageIdent;
  }

  public String getButtonNumber() {
    return buttonNumber;
  }

  public void setButtonNumber(String buttonNumber) {
    this.buttonNumber = buttonNumber;
  }

  public String getButtonName() {
    return buttonName;
  }

  public void setButtonName(String buttonName) {
    this.buttonName = buttonName;
  }

  public String getAvail() {
    return avail;
  }

  public void setAvail(String avail) {
    this.avail = avail;
  }

  public String getGrantAuth() {
    return grantAuth;
  }

  public void setGrantAuth(String grantAuth) {
    this.grantAuth = grantAuth;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getCustomStyle() {
    return customStyle;
  }

  public void setCustomStyle(String customStyle) {
    this.customStyle = customStyle;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
